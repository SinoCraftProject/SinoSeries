package games.moegirl.sinocraft.sinocore.registry;

import games.moegirl.sinocraft.sinocore.utility.IOptional;
import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;

import java.util.function.Supplier;

/**
 * 已注册的游戏元素的引用
 *
 * @param <T> 元素类型
 * @param <O> 实际类型
 */
public interface IRegRef<T, O extends T> extends Supplier<O>, IOptional<O> {

    ResourceKey<T> getKey();

    ResourceLocation getId();

    Holder<T> getHolder();
}
