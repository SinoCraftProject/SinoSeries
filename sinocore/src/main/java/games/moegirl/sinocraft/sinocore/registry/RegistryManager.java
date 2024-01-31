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
public class RegistryManager {

    private static final Map<String, Map<ResourceKey<?>, IRegistry<?>>> DEF_MAP = new HashMap<>();
    private static final Map<String, ITabRegistry> TAB_MAP = new HashMap<>();
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
     * 创建一个新的专用于注册 CreativeModeTab 的注册器
     * <ul>
     *     <li>对于 Forge 和 NeoForge，将创建一个新 DeferredRegister</li>
     *     <li>对于 Fabric，只是创建一个新 IRegistry 实例</li>
     * </ul>
     *
     * <p>
     *
     * {@code create} 与 {@code obtain} 方法创建的 IRegistry 也可以用于注册 CreativeModeTab，但该方法创建的注册器可以向创建的
     * Tab 添加物品
     *
     * @param modId 对应 mod id
     * @return 新注册表引用
     */
    public synchronized static ITabRegistry createTab(String modId) {
        ITabRegistry registry = _createTab(modId);
        TAB_MAP.put(modId, registry);
        return registry;
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
}
