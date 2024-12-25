package games.moegirl.sinocraft.sinocore.registry.fabric;

import games.moegirl.sinocraft.sinocore.registry.IRegRef;
import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

public class FabricRegRef<O> implements IRegRef<O> {

    private final Holder.Reference<O> obj;

    public FabricRegRef(Holder.Reference<O> obj) {
        this.obj = obj;
    }

    @Override
    public ResourceKey<O> getKey() {
        return obj.key();
    }

    @Override
    public ResourceLocation getId() {
        return obj.key().location();
    }

    @Override
    public Holder<O> getHolder() {
        return obj;
    }

    @Override
    public boolean isPresent() {
        return obj.isBound();
    }

    @Override
    public @NotNull O get() {
        return obj.value();
    }
}
