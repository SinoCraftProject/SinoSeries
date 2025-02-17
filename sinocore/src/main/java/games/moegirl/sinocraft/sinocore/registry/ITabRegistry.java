package games.moegirl.sinocraft.sinocore.registry;

import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.CreativeModeTab;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Supplier;

/**
 * CreativeModeTab 注册表
 */
public interface ITabRegistry extends IRegistryBase<CreativeModeTab> {

    /**
     * 所有非通过该接口创建的 Tab 物品添加器
     */
    Map<ResourceKey<CreativeModeTab>, TabItemGenerator> VANILLA_GENERATORS = new ConcurrentHashMap<>();

    /**
     * 所有通过该接口创建的 Tab 物品添加器
     */
    Map<ResourceKey<CreativeModeTab>, TabItemGenerator> GENERATORS = new ConcurrentHashMap<>();

    /**
     * 注册一个默认的 CreativeModeTab 并返回其引用
     *
     * @param name 注册名
     * @return CreativeModeTab 的引用
     */
    IRegRef<CreativeModeTab> registerForRef(String name);

    /**
     * 注册一个默认的 CreativeModeTab 并返回其 Key
     *
     * @param name 注册名
     * @return CreativeModeTab 的 Key
     */
    default ResourceKey<CreativeModeTab> register(String name) {
        return registerForRef(name).getKey();
    }

    /**
     * 注册一个自定义 CreativeModeTab 并返回其引用
     *
     * @param name 注册名
     * @return CreativeModeTab 的引用
     */
    <T extends CreativeModeTab> IRegRef<CreativeModeTab> registerForRef(String name, Supplier<? extends T> supplier);

    /**
     * 注册一个自定义 CreativeModeTab 并返回其 Key
     *
     * @param name 注册名
     * @return CreativeModeTab 的 Key
     */
    default <T extends CreativeModeTab> ResourceKey<CreativeModeTab> register(String name, Supplier<? extends T> supplier) {
        return registerForRef(name, supplier).getKey();
    }

    /**
     * 根据 Key 获取一个 TabItemGenerator，用于向对应 CreativeModeTab 添加物品
     *
     * @param tab CreativeModeTab Key
     */
    TabItemGenerator tabItems(ResourceKey<CreativeModeTab> tab);

    /**
     * 生成默认 CreativeModeTab 标签名的语言文件键
     *
     * @param modId modId
     * @param name  tab名
     * @return CreativeModeTab 的语言键
     */
    static String buildDefaultTranslationKey(String modId, String name) {
        return "itemGroup." + modId + "." + name;
    }

    static String buildDefaultTranslationKey(ResourceKey<CreativeModeTab> name) {
        return buildDefaultTranslationKey(name.location().getNamespace(), name.location().getPath());
    }
}
