package games.moegirl.sinocraft.sinocore.registry;

import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;

import java.util.function.Supplier;

/**
 * SinoCore 注册表
 *
 * @param <T> 元素类型
 */
public interface IRegistry<T> {

    /**
     * 当前 Mod Id
     *
     * @return Mod id
     */
    String modId();

    /**
     * 进行注册
     * <ul>
     *     <li>Forge / NeoForge：在 Mod 主类里调用</li>
     *     <li>Fabric：在 {@code ModInitializer#onInitialize} 中调用</li>
     * </ul>
     */
    void register();

    /**
     * 向该注册表注册内容
     *
     * @param name 注册名，实际为 modid:name
     * @param <R>  实际元素类型（Extends &lt;T&gt;）
     * @return 对象引用
     */
    <R extends T> IRegRef<R> register(String name, Supplier<? extends R> supplier);

    /**
     * 创建或获取某个 Tag
     *
     * @param name Tag 名
     * @return TagKey
     */
    TagKey<T> createTag(ResourceLocation name);

    /**
     * 获取注册器对应的注册表
     *
     * @return 注册表
     */
    Registry<T> getRegistry();

    /**
     * 获取注册的所有对象
     *
     * @return 所有对象
     */
    Iterable<IRegRef<T>> getEntries();

    /**
     * 创建或获取某个 Tag
     *
     * @param name Tag 名，实际为 modid:name
     * @return TagKey
     */
    default TagKey<T> createTag(String name) {
        return createTag(ResourceLocation.fromNamespaceAndPath(modId(), name));
    }
}
