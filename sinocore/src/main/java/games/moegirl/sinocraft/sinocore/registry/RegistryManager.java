package games.moegirl.sinocraft.sinocore.registry;

import com.google.common.collect.ImmutableList;
import com.mojang.logging.LogUtils;
import dev.architectury.injectables.annotations.ExpectPlatform;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import org.slf4j.Logger;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * SinoCore 注册表管理器
 */
@SuppressWarnings({"JavadocReference"})
public class RegistryManager {

    private static final Logger LOGGER = LogUtils.getLogger();

    // Map<String modId, Map<ResourceKey<?> registryId, List<IRegistrable<?>> registries>>
    private static final Map<String, Map<ResourceKey<?>, List<IRegistryBase<?>>>> REGISTRIES = new ConcurrentHashMap<>();

    private static final Map<String, List<IScreenRegistry>> SCREEN_MAP = new HashMap<>();
    private static final Map<String, List<ICommandRegistry>> COMMAND_MAP = new ConcurrentHashMap<>();

    private static <T, U extends IRegistryBase<T>> U appendRegistry(String modId, ResourceKey<Registry<T>> key, U registry) {
        REGISTRIES.computeIfAbsent(modId, __ -> new HashMap<>())
                .computeIfAbsent(key, __ -> new ArrayList<>())
                .add(registry);
        return registry;
    }

    private static <T, U extends IRegistrable<T>> U appendRegistrable(Map<String, List<U>> map, String modId, U registrable) {
        map.computeIfAbsent(modId, __ -> new ArrayList<>())
                .add(registrable);
        return registrable;
    }

    @SuppressWarnings("unchecked")
    public static <T, U extends IRegistryBase<T>> List<U> getRegistries(String modId, ResourceKey<Registry<T>> key) {
        if (!REGISTRIES.containsKey(modId)) {
            return ImmutableList.of();
        }

        var map = REGISTRIES.get(modId);
        if (!map.containsKey(key)) {
            return ImmutableList.of();
        }

        return new ImmutableList.Builder<U>()
                .addAll((List<U>) map.get(key))
                .build();
    }

    /**
     * 创建一个新的 SinoCore 注册表
     * <ul>
     *     <li>对于 Forge 和 NeoForge，将创建一个新 DeferredRegister</li>
     *     <li>对于 Fabric，只是创建一个新 IRegistry 实例</li>
     * </ul>
     *
     * @param modId modid
     * @param key   注册表类型
     * @param <T>   对象类型
     * @return SinoCore 注册表
     */
    public synchronized static <T> IRegistry<T> create(String modId, ResourceKey<Registry<T>> key) {
        return appendRegistry(modId, key, _create(modId, key));
    }

    /**
     * 创建用于 CreativeModeTab 的 SinoCore 注册表
     * <p>
     * 在 {@code create} 方法中创建的 IRegistry 也可以用于注册 CreativeModeTab ，<br/>
     * 但该方法创建的注册器可以向创建的 Tab 添加物品
     * </p>
     *
     * @param modId modid
     * @return Tab 注册表
     */
    public synchronized static ITabRegistry createTab(String modId) {
        return appendRegistry(modId, Registries.CREATIVE_MODE_TAB, _createTab(modId));
    }

    /**
     * 创建用于 Menu 的 SinoCore 注册表
     * <p>
     * 在 {@code create} 方法中创建的 IRegistry 也可以用于注册 {@link net.minecraft.world.inventory.MenuType}，<br/>
     * 但 {@link net.minecraft.world.inventory.MenuType.MenuSupplier} 是私有的。
     * </p>
     *
     * @param modId modid
     * @return Menu 注册表
     */
    public synchronized static IMenuRegistry createMenu(String modId) {
        return appendRegistry(modId, Registries.MENU, _createMenu(modId));
    }

    /**
     * 创建用于自定义统计信息的 SinoCore 注册表
     *
     * @param modId modid
     * @return 自定义统计信息注册表
     */
    public synchronized static ICustomStatRegistry createCustomStat(String modId) {
        return appendRegistry(modId, Registries.CUSTOM_STAT, _createCustomStat(modId));
    }

    /**
     * 创建用于 Screen 的 SinoCore 注册表
     *
     * @param modId modid
     * @return Screen 注册表
     */
    public synchronized static IScreenRegistry createScreen(String modId) {
        return appendRegistrable(SCREEN_MAP, modId, RegistryManager._createScreen(modId));
    }

    /**
     * 创建用于 Command 的 SinoCore 注册表
     *
     * @param modId modid
     * @return Command 注册表
     */
    public synchronized static ICommandRegistry createCommand(String modId) {
        return appendRegistrable(COMMAND_MAP, modId, RegistryManager._createCommand(modId));
    }

    @ExpectPlatform
    static <T> IRegistry<T> _create(String modId, ResourceKey<Registry<T>> key) {
        throw new AssertionError();
    }

    @ExpectPlatform
    static ITabRegistry _createTab(String modId) {
        throw new AssertionError();
    }

    @ExpectPlatform
    static IMenuRegistry _createMenu(String modId) {
        throw new AssertionError();
    }

    @ExpectPlatform
    static IScreenRegistry _createScreen(String modId) {
        throw new AssertionError();
    }

    @ExpectPlatform
    static ICommandRegistry _createCommand(String modId) {
        throw new AssertionError();
    }

    @ExpectPlatform
    static ICustomStatRegistry _createCustomStat(String modId) {
        throw new AssertionError();
    }
}
