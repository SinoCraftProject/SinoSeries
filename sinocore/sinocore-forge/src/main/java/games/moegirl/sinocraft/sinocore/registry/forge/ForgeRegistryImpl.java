package games.moegirl.sinocraft.sinocore.registry.forge;

import games.moegirl.sinocraft.sinocore.registry.IRegistry;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Supplier;

public class ForgeRegistryImpl<T> implements IRegistry<T> {

    final ResourceKey<Registry<T>> key;
    final String modId;

    DeferredRegister<T> dr;
    ResourceKey<T> lastKey;
    ResourceLocation lastId;
    boolean registered;

    ForgeRegistryImpl(String modId, ResourceKey<Registry<T>> key) {
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
    public <R extends T> Supplier<R> register(String name, Supplier<? extends R> supplier) {
//        R value = supplier.get();
        RegistryObject<R> ro = dr.register(name, supplier);
        lastId = ro.getId();
        lastKey = (ResourceKey<T>) ro.getKey();
        return ro;
    }
}
