package games.moegirl.sinocraft.sinocore.registry;

import java.util.function.Supplier;

/**
 * 用于注册元素的注册表类型
 * // todo 注册 tag
 *
 * @param <T> 元素类型
 */
public interface IRegistry<T> {

    /**
     * 向 MC 注册该注册表修改
     */
    void register();

    /**
     * 向该注册表注册内容
     *
     * @param name 注册名，实际为 modid:name
     * @return 对象引用
     */
    <R extends T> IRef<T, R> register(String name, Supplier<? extends R> supplier);
}
