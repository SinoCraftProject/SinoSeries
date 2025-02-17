package games.moegirl.sinocraft.sinocore.registry;

import java.util.function.Supplier;

/**
 * SinoCore 注册表
 *
 * @param <T> 元素类型
 */
public interface IRegistry<T> extends IRegistryBase<T> {

    /**
     * 向该注册表注册内容
     *
     * @param name 注册名，实际为 modid:name
     * @param <R>  实际元素类型（Extends &lt;T&gt;）
     * @return 元素引用
     */
    <R extends T> IRegRef<R> register(String name, Supplier<? extends R> supplier);
}
