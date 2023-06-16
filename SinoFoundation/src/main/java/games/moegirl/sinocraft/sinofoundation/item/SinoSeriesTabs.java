package games.moegirl.sinocraft.sinofoundation.item;

import games.moegirl.sinocraft.sinocore.tab.TabsRegistry;
import games.moegirl.sinocraft.sinofoundation.SinoFoundation;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

public class SinoSeriesTabs {
    public static final DeferredRegister<CreativeModeTab> TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, SinoFoundation.MODID);

    public static final RegistryObject<CreativeModeTab> BUILDING_BLOCKS = tab("building_blocks");
    public static final RegistryObject<CreativeModeTab> AGRICULTURE = tab("agriculture");
    public static final RegistryObject<CreativeModeTab> FUNCTIONAL_BLOCKS = tab("functional_blocks");
    public static final RegistryObject<CreativeModeTab> TOOLS = tab("tools");
    public static final RegistryObject<CreativeModeTab> WEAPONS = tab("weapons");
    public static final RegistryObject<CreativeModeTab> MATERIALS = tab("materials");
    public static final RegistryObject<CreativeModeTab> MISC = tab("misc");

    private static RegistryObject<CreativeModeTab> tab(String name) {
        return TabsRegistry.tab(SinoFoundation.MODID, TABS, name);
    }

    public static void register(IEventBus bus) {
        TABS.register(bus);
    }
}
