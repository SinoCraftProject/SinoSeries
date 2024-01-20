package games.moegirl.sinocraft.sinocore.registry.neoforge;

import games.moegirl.sinocraft.sinocore.registry.IRegRef;
import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.registries.DeferredHolder;

public class NeoForgeRegRefImpl<T, O extends T> implements IRegRef<T, O> {

    private final DeferredHolder<T, O> obj;

    public NeoForgeRegRefImpl(DeferredHolder<T, O> obj) {
        this.obj = obj;
    }

    @Override
    public ResourceKey<T> getKey() {
        return obj.getKey();
    }

    @Override
    public ResourceLocation getId() {
        return obj.getId();
    }

    @Override
    public Holder<T> getHolder() {
        return obj;
    }

    @Override
    public boolean isPresent() {
        return obj.isBound();
    }

    @Override
    public O get() {
        return obj.get();
    }
}
