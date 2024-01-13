package games.moegirl.sinocraft.sinocore.registry.neoforge;

import games.moegirl.sinocraft.sinocore.registry.IRef;
import games.moegirl.sinocraft.sinocore.registry.IRegistry;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.neoforged.fml.javafmlmod.FMLJavaModLoadingContext;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class NeoForgeRegistryImpl<T> implements IRegistry<T> {

    final ResourceKey<Registry<T>> key;
    final String modId;

    DeferredRegister<T> dr;
    boolean registered;

    NeoForgeRegistryImpl(String modId, ResourceKey<Registry<T>> key) {
        this.modId = modId;
        this.key = key;

        // todo 自定义注册表
        dr = DeferredRegister.create(key, modId);
        registered = false;
    }

    @Override
    public void register() {
        if (!registered) {
            dr.register(FMLJavaModLoadingContext.get().getModEventBus());
            registered = true;
        }
    }

    @Override
    public <R extends T> IRef<T, R> register(String name, Supplier<? extends R> supplier) {
        return new NeoForgeRefImpl<>(dr.register(name, supplier));
    }
}
