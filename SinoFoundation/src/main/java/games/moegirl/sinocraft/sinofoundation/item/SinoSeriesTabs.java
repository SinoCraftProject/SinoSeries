package games.moegirl.sinocraft.sinofoundation.item;

import games.moegirl.sinocraft.sinocore.tab.TabItemGenerator;
import games.moegirl.sinocraft.sinofoundation.SinoFoundation;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

public class SinoSeriesTabs {
    public static final DeferredRegister<CreativeModeTab> REGISTRY = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, SinoFoundation.MODID);

//    public static final ResourceLocation TOOLS = new ResourceLocation(SinoFoundation.MODID, "tools");
//    public static final ResourceLocation WEAPONS = new ResourceLocation(SinoFoundation.MODID, "weapons");
//    public static final ResourceLocation MATERIALS = new ResourceLocation(SinoFoundation.MODID, "materials");
//    public static final ResourceLocation MISC = new ResourceLocation(SinoFoundation.MODID, "misc");

    public static class Generators {
        public static final TabItemGenerator BUILDING = new TabItemGenerator();
        public static final TabItemGenerator AGRICULTURE = new TabItemGenerator();
        public static final TabItemGenerator FUNCTIONAL = new TabItemGenerator();
    }

    public static final RegistryObject<CreativeModeTab> BUILDING = tab("building_blocks", Generators.BUILDING);
    public static final RegistryObject<CreativeModeTab> AGRICULTURE = tab("agriculture", Generators.BUILDING);
    public static final RegistryObject<CreativeModeTab> FUNCTIONAL = tab("functional_blocks", Generators.BUILDING);

//    public static final TabsRegistry TOOLS_TAB = TabsRegistry.register(TOOLS, BUS);
//    public static final TabsRegistry WEAPONS_TAB = TabsRegistry.register(WEAPONS, BUS);
//    public static final TabsRegistry MATERIALS_TAB = TabsRegistry.register(MATERIALS, BUS);
//    public static final TabsRegistry MISC_TAB = TabsRegistry.register(MISC, BUS);

    private static RegistryObject<CreativeModeTab> tab(String name, TabItemGenerator generator) {
        return REGISTRY.register(name, () -> CreativeModeTab.builder()
                .title(Component.translatable("tab.sinofoundation." + name))
                .displayItems(generator)
                .icon(generator::displayItem)
                .build());
    }
}
