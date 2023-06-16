package games.moegirl.sinocraft.sinofoundation.item;

import games.moegirl.sinocraft.sinocore.tab.TabsRegistry;
import games.moegirl.sinocraft.sinocore.tab.TabItemGenerator;
import games.moegirl.sinocraft.sinofoundation.SinoFoundation;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

public class SinoSeriesTabs {
    public static final DeferredRegister<CreativeModeTab> TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, SinoFoundation.MODID);

    public static class Generators {
        public static final TabItemGenerator BUILDING_BLOCKS = new TabItemGenerator();
        public static final TabItemGenerator AGRICULTURE = new TabItemGenerator();
        public static final TabItemGenerator FUNCTIONAL_BLOCKS = new TabItemGenerator();
        public static final TabItemGenerator TOOLS = new TabItemGenerator();
        public static final TabItemGenerator WEAPONS = new TabItemGenerator();
        public static final TabItemGenerator MATERIALS = new TabItemGenerator();
        public static final TabItemGenerator MISC = new TabItemGenerator();
    }

    public static final RegistryObject<CreativeModeTab> BUILDING_BLOCKS = tab("building_blocks", Generators.BUILDING_BLOCKS);
    public static final RegistryObject<CreativeModeTab> AGRICULTURE = tab("agriculture", Generators.AGRICULTURE);
    public static final RegistryObject<CreativeModeTab> FUNCTIONAL_BLOCKS = tab("functional_blocks", Generators.FUNCTIONAL_BLOCKS);
    public static final RegistryObject<CreativeModeTab> TOOLS = tab("tools", Generators.TOOLS);
    public static final RegistryObject<CreativeModeTab> WEAPONS = tab("weapons", Generators.WEAPONS);
    public static final RegistryObject<CreativeModeTab> MATERIALS = tab("materials", Generators.MATERIALS);
    public static final RegistryObject<CreativeModeTab> MISC = tab("misc", Generators.MISC);

    private static RegistryObject<CreativeModeTab> tab(String name, TabItemGenerator generator) {
        return TabsRegistry.tab(SinoFoundation.MODID, TABS, name, generator);
    }

    public static void register(IEventBus bus) {
        TABS.register(bus);
    }
}
