package games.moegirl.sinocraft.sinocore.registry;

import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;

import java.util.Optional;

public interface IRegistryBase<T> extends IRegistrable<T> {
    /**
     * 获取对应的注册表
     *
     * @return 注册表
     */
    Registry<T> getRegistry();

    /**
     * 获取注册的所有元素引用
     *
     * @return 所有元素引用
     */
    Iterable<IRegRef<T>> getEntries();

    /**
     * 按 id 获取注册的元素引用
     * @param id ID
     * @return 元素引用，可空，代表未注册。
     */
    Optional<IRegRef<T>> get(ResourceLocation id);
}
