package games.moegirl.sinocraft.sinocore.registry;

import games.moegirl.sinocraft.sinocore.util.IOptional;
import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;

import java.util.function.Supplier;

public interface IRef<T, O extends T> extends Supplier<O>, IOptional<O> {

    ResourceKey<T> getKey();

    ResourceLocation getId();

    Holder<T> getHolder();
}
