package games.moegirl.sinocraft.sinocore.registry.neoforge;

import com.google.common.base.Suppliers;
import games.moegirl.sinocraft.sinocore.registry.IRegRef;
import games.moegirl.sinocraft.sinocore.registry.IRegistry;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.neoforged.fml.javafmlmod.FMLJavaModLoadingContext;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.Supplier;

public class NeoForgeRegistryImpl<T> implements IRegistry<T> {

    final ResourceKey<Registry<T>> key;
    final String modId;
    final List<IRegRef<T, ?>> elementReferences = new ArrayList<>();
    final List<IRegRef<T, ?>> elementView = Collections.unmodifiableList(elementReferences);

    DeferredRegister<T> dr;
    Supplier<Registry<T>> reg;
    boolean registered;

    NeoForgeRegistryImpl(String modId, ResourceKey<Registry<T>> key) {
        this.modId = modId;
        this.key = key;
        this.dr = DeferredRegister.create(key, modId);
        this.reg = Suppliers.memoize(() -> BuiltInRegistries.REGISTRY.get((ResourceKey) key));

        if (!BuiltInRegistries.REGISTRY.containsKey((ResourceKey) key)) {
            dr.makeRegistry(builder -> builder.sync(true));
        }
        registered = false;
    }

    @Override
    public String getModId() {
        return modId;
    }

    @Override
    public void register() {
        if (!registered) {
            dr.register(FMLJavaModLoadingContext.get().getModEventBus());
            registered = true;
        }
    }

    @Override
    public <R extends T> IRegRef<T, R> register(String name, Supplier<? extends R> supplier) {
        return new NeoForgeRegRefImpl<>(dr.register(name, supplier));
    }

    @Override
    public TagKey<T> createTag(ResourceLocation name) {
        return dr.createTagKey(name);
    }

    @Override
    public Registry<T> getRegistry() {
        return reg.get();
    }

    @Override
    public Iterable<IRegRef<T, ?>> getEntries() {
        return elementView;
    }
}
