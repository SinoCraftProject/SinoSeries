package games.moegirl.sinocraft.sinocore.registry;

import com.mojang.logging.LogUtils;
import dev.architectury.injectables.annotations.ExpectPlatform;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import org.slf4j.Logger;

import java.util.HashMap;
import java.util.Map;

/**
 * SinoCore 注册表管理器
 */
@SuppressWarnings({"unchecked", "JavadocReference"})
public class RegistryManager {

    private static final Logger LOGGER = LogUtils.getLogger();

    private static final Map<String, Map<ResourceKey<?>, IRegistry<?>>> REGISTRY_MAP = new HashMap<>();
    private static final Map<String, ITabRegistry> TAB_MAP = new HashMap<>();
    private static final Map<String, IMenuRegistry> MENU_MAP = new HashMap<>();
    private static final Map<String, IScreenRegistry> SCREEN_MAP = new HashMap<>();
    private static final Map<String, ICommandRegistry> COMMAND_MAP = new HashMap<>();
    private static final Map<String, IDataProviderRegistry> DATA_PROVIDER_MAP = new HashMap<>();
    private static final Map<String, ICustomStatRegistry> CUSTOM_STAT_MAP = new HashMap<>();

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
        IRegistry<T> registry = _create(modId, key);
        REGISTRY_MAP.computeIfAbsent(modId, __ -> new HashMap<>()).put(key, registry);
        return registry;
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
        return TAB_MAP.computeIfAbsent(modId, RegistryManager::_createTab);
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
        return MENU_MAP.computeIfAbsent(modId, RegistryManager::_createMenu);
    }

    /**
     * 创建用于 Screen 的 SinoCore 注册表
     *
     * @param modId modid
     * @return Screen 注册表
     */
    public synchronized static IScreenRegistry createScreen(String modId) {
        return SCREEN_MAP.computeIfAbsent(modId, RegistryManager::_createScreen);
    }

    /**
     * 创建用于 Command 的 SinoCore 注册表
     *
     * @param modId modid
     * @return Command 注册表
     */
    public synchronized static ICommandRegistry createCommand(String modId) {
        return COMMAND_MAP.computeIfAbsent(modId, RegistryManager::_createCommand);
    }

    /**
     * 创建用于 DataProvider 的 SinoCore 注册表
     *
     * @param modId modid
     * @return DataProvider 注册表
     */
    public synchronized static IDataProviderRegistry createDataProvider(String modId) {
        return DATA_PROVIDER_MAP.computeIfAbsent(modId, RegistryManager::_createDataProvider);
    }

    /**
     * 创建用于自定义统计信息的 SinoCore 注册表
     *
     * @param modId modid
     * @return 自定义统计信息注册表
     */
    public synchronized static ICustomStatRegistry createCustomStat(String modId) {
        return CUSTOM_STAT_MAP.computeIfAbsent(modId, RegistryManager::_createCustomStat);
    }

    /**
     * 获取该 mod 最后一个该类型注册器，如果不存在则创建一个新对象
     *
     * @param modId 对应 mod id
     * @param key   注册类型注册表的键
     * @param <T>   注册类型
     * @return 最后一个或新注册表引用
     * @deprecated Use {@link RegistryManager#create}
     */
    @Deprecated(forRemoval = true)
    public synchronized static <T> IRegistry<T> obtain(String modId, ResourceKey<Registry<T>> key) {
        if (Registries.CREATIVE_MODE_TAB.equals(key)) {
            LOGGER.warn("Use obtainTab to add creative mod tab easier.");
        }

        return (IRegistry<T>) REGISTRY_MAP
                .computeIfAbsent(modId, __ -> new HashMap<>())
                .computeIfAbsent(key, __ -> _create(modId, key));
    }

    /**
     * 获取该 mod 最后一个专用于注册 CreativeModeTab 的注册器，如果不存在则创建一个新对象
     *
     * <p>
     * <p>
     * {@code create} 与 {@code obtain} 方法创建的 IRegistry 也可以用于注册 CreativeModeTab，但该方法创建的注册器可以向创建的
     * Tab 添加物品
     *
     * @param modId 对应 mod id
     * @return 最后一个或新注册器
     * @deprecated Use {@link RegistryManager#createTab}
     */
    @Deprecated(forRemoval = true)
    public synchronized static ITabRegistry obtainTab(String modId) {
        return TAB_MAP.computeIfAbsent(modId, RegistryManager::_createTab);
    }

    /**
     * 获取该 mod 最后一个专用于注册 Menu 的注册器，如果不存在则创建一个新对象
     *
     * <p>
     * <p>
     * {@code create} 与 {@code obtain} 方法创建的 IRegistry 也可以用于注册 {@link net.minecraft.world.inventory.MenuType}，
     * 但 {@link net.minecraft.world.inventory.MenuType.MenuSupplier} 是私有的，无法创建
     *
     * @param modId 对应 mod id
     * @return 最后一个或新注册器
     * @deprecated Use {@link RegistryManager#createMenu}
     */
    @Deprecated(forRemoval = true)
    public synchronized static IMenuRegistry obtainMenu(String modId) {
        return MENU_MAP.computeIfAbsent(modId, RegistryManager::_createMenu);
    }

    /**
     * 注册 Screen 与 Menu 的关联关系
     *
     * @param modId 对应 mod id
     * @return 最后一个或新注册器
     * @deprecated Use {@link RegistryManager#createScreen}
     */
    @Deprecated(forRemoval = true)
    public synchronized static IScreenRegistry obtainScreen(String modId) {
        return SCREEN_MAP.computeIfAbsent(modId, RegistryManager::_createScreen);
    }

    /**
     * 注册指令
     *
     * @param modId 对应 mod id
     * @return 最后一个或新注册器
     * @deprecated Use {@link RegistryManager#createCommand}
     */
    @Deprecated(forRemoval = true)
    public synchronized static ICommandRegistry obtainCommand(String modId) {
        return COMMAND_MAP.computeIfAbsent(modId, RegistryManager::_createCommand);
    }

    /**
     * @param modId ModId
     * @return IDataProviderRegistry
     * @deprecated Use {@link RegistryManager#createDataProvider}
     */
    @Deprecated(forRemoval = true)
    public synchronized static IDataProviderRegistry obtainDataProvider(String modId) {
        return DATA_PROVIDER_MAP.computeIfAbsent(modId, RegistryManager::_createDataProvider);
    }

    /**
     * 注册自定义统计信息
     *
     * @deprecated Use {@link RegistryManager#createCustomStat}
     */
    @Deprecated(forRemoval = true)
    public synchronized static ICustomStatRegistry obtainCustomStat(String modId) {
        return CUSTOM_STAT_MAP.computeIfAbsent(modId, RegistryManager::_createCustomStat);
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
    static IDataProviderRegistry _createDataProvider(String modId) {
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
