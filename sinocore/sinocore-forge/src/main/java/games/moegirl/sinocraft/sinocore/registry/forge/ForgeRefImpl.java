package games.moegirl.sinocraft.sinocore.registry.forge;

import games.moegirl.sinocraft.sinocore.registry.IRef;
import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.registries.RegistryObject;

public class ForgeRefImpl<T, O extends T> implements IRef<T, O> {

    private final RegistryObject<O> obj;

    public ForgeRefImpl(RegistryObject<O> obj) {
        this.obj = obj;
    }

    @Override
    public ResourceKey<T> getKey() {
        return (ResourceKey<T>) obj.getKey();
    }

    @Override
    public ResourceLocation getId() {
        return obj.getId();
    }

    @Override
    public Holder<T> getHolder() {
        return (Holder<T>) obj.getHolder().orElseThrow();
    }

    @Override
    public boolean isPresent() {
        return obj.isPresent();
    }

    @Override
    public O get() {
        return obj.get();
    }
}
