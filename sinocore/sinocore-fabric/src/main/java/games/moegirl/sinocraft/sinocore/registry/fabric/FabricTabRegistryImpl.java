package games.moegirl.sinocraft.sinocore.registry.fabric;

import games.moegirl.sinocraft.sinocore.registry.IRef;
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

import java.util.function.Supplier;

public class FabricTabRegistryImpl implements ITabRegistry {

    private final String modId;
    private final Registry<CreativeModeTab> registry;

    FabricTabRegistryImpl(String modId) {
        this.modId = modId;
        registry = (Registry<CreativeModeTab>) BuiltInRegistries.REGISTRY.get(Registries.CREATIVE_MODE_TAB.location());
    }

    @Override
    public void register() {
    }

    @Override
    public IRef<CreativeModeTab, CreativeModeTab> registerForRef(String name) {
        TabItemGenerator generator = new TabItemGenerator();
        ResourceLocation id = new ResourceLocation(modId, name);
        ResourceKey<CreativeModeTab> key = ResourceKey.create(Registries.CREATIVE_MODE_TAB, id);
        GENERATORS.put(key, generator);
        return registerForRef(name, () -> FabricItemGroup.builder()
                .title(Component.translatable("tab." + modId + "." + name))
                .displayItems(generator)
                .icon(generator::displayItem)
                .build());
    }

    @Override
    public <T extends CreativeModeTab> IRef<CreativeModeTab, T> registerForRef(String name, Supplier<? extends T> supplier) {
        ResourceLocation id = new ResourceLocation(modId, name);
        ResourceKey<CreativeModeTab> key = ResourceKey.create(Registries.CREATIVE_MODE_TAB, id);
        return (IRef<CreativeModeTab, T>) new FabricRefImpl<>(Registry.registerForHolder(registry, key, supplier.get()));
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
}
