package games.moegirl.sinocraft.sinocore.registry.fabric;

import com.google.common.base.Suppliers;
import games.moegirl.sinocraft.sinocore.registry.IRegRef;
import games.moegirl.sinocraft.sinocore.registry.IRegistry;
import net.fabricmc.fabric.api.event.registry.FabricRegistryBuilder;
import net.fabricmc.fabric.api.event.registry.RegistryAttribute;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.function.Supplier;

public class FabricRegistryImpl<T> implements IRegistry<T> {

    final String modId;
    final ResourceKey<Registry<T>> key;
    Registry<T> registry;
    Supplier<Registry<T>> sup;
    final List<IRegRef<T, ?>> elementReferences = new ArrayList<>();
    final List<IRegRef<T, ?>> elementView = Collections.unmodifiableList(elementReferences);

    FabricRegistryImpl(String modId, ResourceKey<Registry<T>> key) {
        this.modId = modId;
        this.key = key;

        registry = (Registry<T>) BuiltInRegistries.REGISTRY.get(key.location());
        if (registry == null) {
            // 不存在的注册表 -- 创建自定义注册表
            registry = FabricRegistryBuilder.createSimple(key)
                    .attribute(RegistryAttribute.SYNCED)
                    .buildAndRegister();
        }
        sup = Suppliers.memoize(() -> (Registry<T>) BuiltInRegistries.REGISTRY.get(key.location()));
    }

    @Override
    public String getModId() {
        return modId;
    }

    @Override
    public void register() {
    }

    @Override
    public <R extends T> IRegRef<T, R> register(String name, Supplier<? extends R> supplier) {
        ResourceLocation id = new ResourceLocation(modId, name);
        ResourceKey<T> eKey = ResourceKey.create(key, id);
        FabricRegRefImpl<T, T> ref = new FabricRegRefImpl<>(Registry.registerForHolder(registry, eKey, supplier.get()));
        elementReferences.add(ref);
        return (IRegRef<T, R>) ref;
    }

    @Override
    public TagKey<T> createTag(ResourceLocation name) {
        return TagKey.create(key, name);
    }

    @Override
    public Registry<T> getRegistry() {
        return sup.get();
    }

    @Override
    public Iterable<IRegRef<T, ?>> getEntries() {
        return elementView;
    }
}
