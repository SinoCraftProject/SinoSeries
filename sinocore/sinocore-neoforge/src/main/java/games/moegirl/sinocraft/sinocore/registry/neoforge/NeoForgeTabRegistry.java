package games.moegirl.sinocraft.sinocore.registry.neoforge;

import games.moegirl.sinocraft.sinocore.registry.IRegRef;
import games.moegirl.sinocraft.sinocore.registry.ITabRegistry;
import games.moegirl.sinocraft.sinocore.registry.TabItemGenerator;
import games.moegirl.sinocraft.sinocore.utility.neoforge.ModBusHelper;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.CreativeModeTab;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.Supplier;

public class NeoForgeTabRegistry implements ITabRegistry {

    private final String modId;
    private final IEventBus bus;
    private final DeferredRegister<CreativeModeTab> dr;

    NeoForgeTabRegistry(String modId) {
        this.modId = modId;
        this.bus = ModBusHelper.getModBus(modId);
        this.dr = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, modId);
    }

    @Override
    public void register() {
        dr.register(bus);
    }

    @Override
    public IRegRef<CreativeModeTab, CreativeModeTab> registerForRef(String name) {
        TabItemGenerator generator = new TabItemGenerator();
        IRegRef<CreativeModeTab, CreativeModeTab> ref = registerForRef(name, () -> CreativeModeTab.builder()
                .title(Component.translatable(ITabRegistry.buildDefaultTranslationKey(modId, name)))
                .displayItems(generator)
                .icon(generator::displayItem)
                .build());
        GENERATORS.put(ref.getKey(), generator);
        return ref;
    }

    @Override
    public <T extends CreativeModeTab> IRegRef<CreativeModeTab, T> registerForRef(String name, Supplier<? extends T> supplier) {
        return new NeoForgeRegRef<>(dr.register(name, supplier));
    }

    @Override
    public synchronized TabItemGenerator tabItems(ResourceKey<CreativeModeTab> tab) {
        if (GENERATORS.containsKey(tab)) {
            return GENERATORS.get(tab);
        }
        return VANILLA_GENERATORS.computeIfAbsent(tab, __ -> {
            TabItemGenerator generator = TabItemGenerator.vanilla(tab);
            bus.addListener(new Event(generator, tab));
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
