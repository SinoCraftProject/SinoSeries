package games.moegirl.sinocraft.sinocore.registry.forge;

import games.moegirl.sinocraft.sinocore.registry.ITabRegistry;
import games.moegirl.sinocraft.sinocore.registry.TabItemGenerator;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.Supplier;

public class ForgeTabRegistryImpl implements ITabRegistry {
    private static final List<ResourceLocation> ALL_TABS = new ArrayList<>();

    static {
        var inventory = ResourceKey.create(Registries.CREATIVE_MODE_TAB, new ResourceLocation("inventory"));
        var hotbar = ResourceKey.create(Registries.CREATIVE_MODE_TAB, new ResourceLocation("hotbar"));

        if (ALL_TABS.isEmpty()) {
            for (CreativeModeTab tab : CreativeModeTabs.tabs()) {
                if (tab != CreativeModeTabs.searchTab()) {
                    ResourceLocation key = BuiltInRegistries.CREATIVE_MODE_TAB.getKey(tab);
                    if (!Objects.equals(key, inventory.location()) && !Objects.equals(key, hotbar.location())) {
                        ALL_TABS.add(key);
                    }
                }
            }
        }
    }

    private final String modId;
    private final DeferredRegister<CreativeModeTab> dr;

    ForgeTabRegistryImpl(String modId) {
        this.modId = modId;
        this.dr = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, modId);
    }

    @Override
    public void register() {
        dr.register(FMLJavaModLoadingContext.get().getModEventBus());
    }

    @Override
    public ResourceKey<CreativeModeTab> register(String name) {
        TabItemGenerator generator = new TabItemGenerator();
        ResourceLocation[] tabs = ALL_TABS.toArray(ResourceLocation[]::new);
        ResourceKey<CreativeModeTab> tab = register(name, () -> CreativeModeTab.builder()
                .title(Component.translatable("tab." + modId + "." + name))
                .displayItems(generator)
                .icon(generator::displayItem)
                .withTabsBefore(tabs)
                .build());
        GENERATORS.put(tab, generator);
        return tab;
    }

    @Override
    public ResourceKey<CreativeModeTab> register(String name, Supplier<CreativeModeTab> supplier) {
        RegistryObject<CreativeModeTab> ro = dr.register(name, supplier);
        ALL_TABS.add(ro.getId());
        return ro.getKey();
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
            FMLJavaModLoadingContext.get().getModEventBus().addListener(new Event(generator, tab));
            return generator;
        });
    }

    record Event(TabItemGenerator generator,
                 ResourceKey<CreativeModeTab> tab) implements Consumer<BuildCreativeModeTabContentsEvent> {

        @Override
        public void accept(BuildCreativeModeTabContentsEvent event) {
            if (Objects.equals(tab, event.getTabKey())) {
                generator.accept(event.getParameters(), event);
            }
        }
    }
}
