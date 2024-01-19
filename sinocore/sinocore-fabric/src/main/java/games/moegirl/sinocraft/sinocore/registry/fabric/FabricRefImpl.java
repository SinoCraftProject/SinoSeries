package games.moegirl.sinocraft.sinocore.registry.fabric;

import games.moegirl.sinocraft.sinocore.registry.IRef;
import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;

public class FabricRefImpl<T, O extends T> implements IRef<T, O> {

    private final Holder.Reference<O> obj;

    public FabricRefImpl(Holder.Reference<O> obj) {
        this.obj = obj;
    }

    @Override
    public ResourceKey<T> getKey() {
        return (ResourceKey<T>) obj.key();
    }

    @Override
    public ResourceLocation getId() {
        return obj.key().location();
    }

    @Override
    public Holder<T> getHolder() {
        return (Holder<T>) obj;
    }

    @Override
    public boolean isPresent() {
        return obj.isBound();
    }

    @Override
    public O get() {
        return obj.value();
    }
}
