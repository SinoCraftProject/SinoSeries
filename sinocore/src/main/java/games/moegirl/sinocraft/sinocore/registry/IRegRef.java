package games.moegirl.sinocraft.sinocore.registry;

import games.moegirl.sinocraft.sinocore.utility.IOptional;
import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;

import java.util.function.Supplier;

/**
 * 已注册的游戏元素的引用
 *
 * @param <O> 元素类型
 */
public interface IRegRef<O> extends Supplier<O>, IOptional<O> {

    ResourceKey<O> getKey();

    ResourceLocation getId();

    Holder<O> getHolder();
}
