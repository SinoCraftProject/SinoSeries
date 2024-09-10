package games.moegirl.sinocraft.sinocore.registry.neoforge;

import games.moegirl.sinocraft.sinocore.neoforge.SinoCoreNeoForge;
import games.moegirl.sinocraft.sinocore.registry.IRegRef;
import games.moegirl.sinocraft.sinocore.registry.IRegistry;
import games.moegirl.sinocraft.sinocore.utility.neoforge.ModBusHelper;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NewRegistryEvent;
import net.neoforged.neoforge.registries.RegistryBuilder;
import net.neoforged.neoforge.registries.RegistryManager;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Supplier;

public class NeoForgeRegistryImpl<T> implements IRegistry<T> {

    private final IEventBus bus;

    final ResourceKey<Registry<T>> key;
    final String modId;
    final List<IRegRef<T, ?>> elementReferences = new ArrayList<>();
    final List<IRegRef<T, ?>> elementView = Collections.unmodifiableList(elementReferences);

    protected DeferredRegister<T> dr;
    protected boolean registered;

    NeoForgeRegistryImpl(String modId, ResourceKey<Registry<T>> key) {
        this.bus = ModBusHelper.getModBus(modId);

        this.modId = modId;
        this.key = key;
        this.dr = DeferredRegister.create(key, modId);

        if (!BuiltInRegistries.REGISTRY.containsKey(key.location())) {
            bus.addListener((Consumer<NewRegistryEvent>) event -> event.register(new RegistryBuilder<>(key)
                    .sync(true)
                    .create()));
        }
        registered = false;
    }

    @Override
    public String modId() {
        return modId;
    }

    @Override
    public void register() {
        if (!registered) {
            dr.register(bus);
            registered = true;
        }
    }

    @Override
    public <R extends T> IRegRef<T, R> register(String name, Supplier<? extends R> supplier) {
        NeoForgeRegRef<T, R> ref = new NeoForgeRegRef<>(dr.register(name, supplier));
        elementReferences.add(ref);
        return ref;
    }

    @Override
    public TagKey<T> createTag(ResourceLocation name) {
        return dr.createTagKey(name);
    }

    @Override
    public Registry<T> getRegistry() {
        return dr.getRegistry().get();
    }

    @Override
    public Iterable<IRegRef<T, ?>> getEntries() {
        return elementView;
    }
}
