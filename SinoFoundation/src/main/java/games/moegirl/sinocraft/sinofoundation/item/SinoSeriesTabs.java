package games.moegirl.sinocraft.sinofoundation.item;

import games.moegirl.sinocraft.sinocore.item.tab.TabsRegistry;
import games.moegirl.sinocraft.sinofoundation.SinoFoundation;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

public class SinoSeriesTabs {
    private static final IEventBus BUS = FMLJavaModLoadingContext.get().getModEventBus();

    public static final ResourceLocation BUILDING_BLOCKS = new ResourceLocation(SinoFoundation.MODID, "building_blocks");
    // public static final ResourceLocation FUNCTIONAL_BLOCKS = new ResourceLocation(SinoFoundation.MODID, "functional_blocks");
    public static final ResourceLocation AGRICULTURE = new ResourceLocation(SinoFoundation.MODID, "agriculture");
    public static final ResourceLocation TOOLS = new ResourceLocation(SinoFoundation.MODID, "tools");
    public static final ResourceLocation WEAPONS = new ResourceLocation(SinoFoundation.MODID, "weapons");
    public static final ResourceLocation MATERIALS = new ResourceLocation(SinoFoundation.MODID, "materials");
    public static final ResourceLocation MISC = new ResourceLocation(SinoFoundation.MODID, "misc");

    public static final TabsRegistry BUILDING_BLOCKS_TAB = TabsRegistry.register(BUILDING_BLOCKS, BUS);
    // TODO 问题：Tab 中没有物品，所以这个 tab 不给显示
    // public static final TabsRegistry FUNCTIONAL_BLOCKS_TAB = TabsRegistry.register(FUNCTIONAL_BLOCKS, BUS);
    public static final TabsRegistry AGRICULTURE_TAB = TabsRegistry.register(AGRICULTURE, BUS);
    public static final TabsRegistry TOOLS_TAB = TabsRegistry.register(TOOLS, BUS);
    public static final TabsRegistry WEAPONS_TAB = TabsRegistry.register(WEAPONS, BUS);
    public static final TabsRegistry MATERIALS_TAB = TabsRegistry.register(MATERIALS, BUS);
    public static final TabsRegistry MISC_TAB = TabsRegistry.register(MISC, BUS);
}
