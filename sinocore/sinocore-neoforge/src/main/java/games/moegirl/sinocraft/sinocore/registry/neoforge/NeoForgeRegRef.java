package games.moegirl.sinocraft.sinocore.registry.neoforge;

import games.moegirl.sinocraft.sinocore.registry.IRegRef;
import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.registries.DeferredHolder;
import org.jetbrains.annotations.NotNull;

public class NeoForgeRegRef<T, O extends T> implements IRegRef<T, O> {

    private final DeferredHolder<T, O> obj;

    public NeoForgeRegRef(DeferredHolder<T, O> obj) {
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
        return obj.getDelegate();
    }

    @Override
    public boolean isPresent() {
        return obj.isBound();
    }

    @Override
    public @NotNull O get() {
        return obj.get();
    }
}
