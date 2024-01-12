package games.moegirl.sinocraft.sinocore.registry.fabric;

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
    public ResourceKey<CreativeModeTab> register(String name) {
        TabItemGenerator generator = new TabItemGenerator();
        Supplier<CreativeModeTab> supplier = () -> FabricItemGroup.builder()
                .title(Component.translatable("tab." + modId + "." + name))
                .displayItems(generator)
                .icon(generator::displayItem)
                .build();
        ResourceLocation id = new ResourceLocation(modId, name);
        ResourceKey<CreativeModeTab> key = ResourceKey.create(Registries.CREATIVE_MODE_TAB, id);
        GENERATORS.put(key, generator);
        Registry.register(registry, key, supplier.get());
        return key;
    }

    @Override
    public ResourceKey<CreativeModeTab> register(String name, Supplier<CreativeModeTab> supplier) {
        ResourceLocation id = new ResourceLocation(modId, name);
        ResourceKey<CreativeModeTab> key = ResourceKey.create(Registries.CREATIVE_MODE_TAB, id);
        Registry.register(registry, key, supplier.get());
        return key;
    }

    @Override
    public TabItemGenerator tabItems(ResourceKey<CreativeModeTab> tab) {
        synchronized (ITabRegistry.class) {
            if (GENERATORS.containsKey(tab)) {
                return GENERATORS.get(tab);
            }
        }
        return VANILLA_GENERATORS.computeIfAbsent(tab, __ -> {
            TabItemGenerator generator = TabItemGenerator.vanilla(tab);
            ItemGroupEvents.modifyEntriesEvent(tab).register(entries -> generator.accept(entries.getContext(), entries));
            return generator;
        });
    }
}
