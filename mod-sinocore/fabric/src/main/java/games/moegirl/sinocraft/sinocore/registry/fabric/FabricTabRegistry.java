package games.moegirl.sinocraft.sinocore.registry.fabric;

import games.moegirl.sinocraft.sinocore.registry.IRegRef;
import games.moegirl.sinocraft.sinocore.registry.ITabRegistry;
import games.moegirl.sinocraft.sinocore.registry.TabItemGenerator;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTab;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Supplier;

public class FabricTabRegistry implements ITabRegistry {

    private final String modId;
    private final Registry<CreativeModeTab> registry;
    private final Map<ResourceLocation, IRegRef<CreativeModeTab>> refs = new HashMap<>();

    @SuppressWarnings("unchecked")
    FabricTabRegistry(String modId) {
        this.modId = modId;
        registry = (Registry<CreativeModeTab>) BuiltInRegistries.REGISTRY.get(Registries.CREATIVE_MODE_TAB.location());
    }

    @Override
    public String modId() {
        return modId;
    }

    @Override
    public void register() {
    }

    @Override
    public IRegRef<CreativeModeTab> registerForRef(String name) {
        TabItemGenerator generator = new TabItemGenerator();
        ResourceLocation id = ResourceLocation.fromNamespaceAndPath(modId, name);
        ResourceKey<CreativeModeTab> key = ResourceKey.create(Registries.CREATIVE_MODE_TAB, id);
        GENERATORS.put(key, generator);
        return registerForRef(name, () -> FabricItemGroup.builder()
                .title(Component.translatable(ITabRegistry.buildDefaultTranslationKey(modId, name)))
                .displayItems(generator)
                .icon(generator::displayItem)
                .build());
    }

    @Override
    public <T extends CreativeModeTab> IRegRef<CreativeModeTab> registerForRef(String name, Supplier<? extends T> supplier) {
        ResourceLocation id = ResourceLocation.fromNamespaceAndPath(modId, name);
        ResourceKey<CreativeModeTab> key = ResourceKey.create(Registries.CREATIVE_MODE_TAB, id);
        var ref = new FabricRegRef<>(Registry.registerForHolder(registry, key, supplier.get()));
        refs.put(ResourceLocation.fromNamespaceAndPath(modId, name), ref);
        return ref;
    }

    @Override
    public synchronized TabItemGenerator tabItems(ResourceKey<CreativeModeTab> tab) {
        if (GENERATORS.containsKey(tab)) {
            return GENERATORS.get(tab);
        }
        return VANILLA_GENERATORS.computeIfAbsent(tab, __ -> {
            TabItemGenerator generator = TabItemGenerator.vanilla(tab);
            ItemGroupEvents.modifyEntriesEvent(tab).register(entries -> generator.accept(entries.getContext(), entries));
            return generator;
        });
    }

    @Override
    public Registry<CreativeModeTab> getRegistry() {
        return registry;
    }

    @Override
    public Iterable<IRegRef<CreativeModeTab>> getEntries() {
        return refs.values();
    }

    @Override
    public Optional<IRegRef<CreativeModeTab>> get(ResourceLocation id) {
        return Optional.ofNullable(refs.get(id));
    }
}
