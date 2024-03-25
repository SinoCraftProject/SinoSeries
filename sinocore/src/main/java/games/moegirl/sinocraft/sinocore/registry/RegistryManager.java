package games.moegirl.sinocraft.sinocore.registry;

import dev.architectury.injectables.annotations.ExpectPlatform;
import games.moegirl.sinocraft.sinocore.SinoCore;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;

import java.util.HashMap;
import java.util.Map;

/**
 * 注册管理器
 */
@SuppressWarnings("unchecked")
public class RegistryManager {

    private static final Map<String, Map<ResourceKey<?>, IRegistry<?>>> DEF_MAP = new HashMap<>();
    private static final Map<String, ITabRegistry> TAB_MAP = new HashMap<>();
    private static final Map<String, IMenuRegister> MENU_MAP = new HashMap<>();
    private static final Map<String, IScreenRegister> SCREEN_MAP = new HashMap<>();
    private static final Map<String, IDataProviderRegister> DATA_PROVIDER_TAB = new HashMap<>();

    /**
     * 创建一个新的注册器
     * <ul>
     *     <li>对于 Forge 和 NeoForge，将创建一个新 DeferredRegister</li>
     *     <li>对于 Fabric，只是创建一个新 IRegistry 实例</li>
     * </ul>
     *
     * @param modId 对应 mod id
     * @param key   注册类型注册表的键
     * @param <T>   注册类型
     * @return 新注册表引用
     */
    public synchronized static <T> IRegistry<T> create(String modId, ResourceKey<Registry<T>> key) {
        if (Registries.CREATIVE_MODE_TAB.equals(key)) {
            SinoCore.LOGGER.warn("Use obtainTab or createTab to add creative mod tab easier.");
        }

        IRegistry<T> registry = _create(modId, key);
        DEF_MAP.computeIfAbsent(modId, __ -> new HashMap<>()).put(key, registry);
        return registry;
    }

    /**
     * 获取该 mod 最后一个该类型注册器，如果不存在则创建一个新对象
     *
     * @param modId 对应 mod id
     * @param key   注册类型注册表的键
     * @param <T>   注册类型
     * @return 最后一个或新注册表引用
     */
    public synchronized static <T> IRegistry<T> obtain(String modId, ResourceKey<Registry<T>> key) {
        if (Registries.CREATIVE_MODE_TAB.equals(key)) {
            SinoCore.LOGGER.warn("Use obtainTab or createTab to add creative mod tab easier.");
        }

        return (IRegistry<T>) DEF_MAP
                .computeIfAbsent(modId, __ -> new HashMap<>())
                .computeIfAbsent(key, __ -> _create(modId, key));
    }

    /**
     * 获取该 mod 最后一个专用于注册 CreativeModeTab 的注册器，如果不存在则创建一个新对象
     *
     * <p>
     *
     * {@code create} 与 {@code obtain} 方法创建的 IRegistry 也可以用于注册 CreativeModeTab，但该方法创建的注册器可以向创建的
     * Tab 添加物品
     *
     * @param modId 对应 mod id
     * @return 最后一个或新注册器
     */
    public synchronized static ITabRegistry obtainTab(String modId) {
        return TAB_MAP.computeIfAbsent(modId, RegistryManager::_createTab);
    }

    /**
     * 获取该 mod 最后一个专用于注册 CreativeModeTab 的注册器，如果不存在则创建一个新对象
     *
     * <p>
     *
     * {@code create} 与 {@code obtain} 方法创建的 IRegistry 也可以用于注册 {@link net.minecraft.world.inventory.MenuType}，
     * 但 {@link net.minecraft.world.inventory.MenuType.MenuSupplier} 是私有的，无法创建
     *
     * @param modId 对应 mod id
     * @return 最后一个或新注册器
     */
    public synchronized static IMenuRegister obtainMenu(String modId) {
        return MENU_MAP.computeIfAbsent(modId, RegistryManager::_createMenu);
    }

    /**
     * 注册 Screen 与 Menu 的关联关系
     *
     * @param modId 对应 mod id
     * @return 最后一个或新注册器
     */
    public synchronized static IScreenRegister obtainScreen(String modId) {
        return SCREEN_MAP.computeIfAbsent(modId, RegistryManager::_createScreen);
    }

    public synchronized static IDataProviderRegister obtainDataProvider(String modId) {
        return DATA_PROVIDER_TAB.computeIfAbsent(modId, RegistryManager::_createDataProvider);
    }

    @ExpectPlatform
    public static <T> IRegistry<T> _create(String modId, ResourceKey<Registry<T>> key) {
        throw new AssertionError();
    }

    @ExpectPlatform
    public static ITabRegistry _createTab(String modId) {
        throw new AssertionError();
    }

    @ExpectPlatform
    public static IDataProviderRegister _createDataProvider(String modId) {
        throw new AssertionError();
    }

    @ExpectPlatform
    public static IMenuRegister _createMenu(String modId) {
        throw new AssertionError();
    }

    @ExpectPlatform
    public static IScreenRegister _createScreen(String modId) {
        throw new AssertionError();
    }
}
