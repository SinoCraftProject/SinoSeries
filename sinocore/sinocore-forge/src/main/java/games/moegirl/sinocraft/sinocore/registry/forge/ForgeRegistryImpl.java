package games.moegirl.sinocraft.sinocore.registry.forge;

import com.google.common.base.Suppliers;
import com.mojang.serialization.Lifecycle;
import games.moegirl.sinocraft.sinocore.registry.IRegRef;
import games.moegirl.sinocraft.sinocore.registry.IRegistry;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.*;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.function.Supplier;

public class ForgeRegistryImpl<T> implements IRegistry<T> {

    final ResourceKey<Registry<T>> key;
    final String modId;
    final List<IRegRef<T, ?>> elementReferences = new ArrayList<>();
    final List<IRegRef<T, ?>> elementView = Collections.unmodifiableList(elementReferences);

    DeferredRegister<T> dr;
    Supplier<Registry<T>> reg;
    boolean registered;

    ForgeRegistryImpl(String modId, ResourceKey<Registry<T>> key) {
        this.modId = modId;
        this.key = key;
        this.dr = DeferredRegister.create(key, modId);
        this.reg = Suppliers.memoize(() -> GameData.getWrapper(key, Lifecycle.stable()));

        if (RegistryManager.ACTIVE.getRegistry((ResourceKey) key) == null) {
            dr.makeRegistry(() -> new RegistryBuilder<T>().hasTags());
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
        ForgeRegRefImpl<T, R> ref = new ForgeRegRefImpl<>(dr.register(name, supplier));
        elementReferences.add(ref);
        return ref;
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
