package games.moegirl.sinocraft.sinodivination;

import games.moegirl.sinocraft.sinocore.item.tab.TabsRegistry;
import games.moegirl.sinocraft.sinodivination.old.block.SDBlocks;
import games.moegirl.sinocraft.sinodivination.old.blockentity.SDBlockEntities;
import games.moegirl.sinocraft.sinodivination.old.client.screen.SDScreens;
import games.moegirl.sinocraft.sinodivination.old.entity.SDEntities;
import games.moegirl.sinocraft.sinodivination.old.item.SDItems;
import games.moegirl.sinocraft.sinodivination.old.menu.SDMenus;
import games.moegirl.sinocraft.sinodivination.old.network.SDNetworks;
import games.moegirl.sinocraft.sinodivination.old.recipe.SDRecipes;
import games.moegirl.sinocraft.sinodivination.old.tree.SDTrees;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Mod(SinoDivination.MODID)
public class SinoDivination {
    public static final String MODID = "sinodivination";
    public static final String NAME = "SinoDivination";
    public static final String VERSION = "@version@";

    public static TabsRegistry TAB;

    private static final Logger LOGGER = LoggerFactory.getLogger(NAME);

    public SinoDivination() {
        LOGGER.info("Loading SinoDivination. Ver: " + VERSION);

        IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();
        TAB = TabsRegistry.register(new ResourceLocation(MODID, "tab"), bus);

        SDNetworks.register();
        SDBlocks.REGISTRY.register(bus);
        SDItems.REGISTRY.register(bus);
        SDBlockEntities.REGISTRY.register(bus);
//        SDPlacedFeatures.REGISTRY.register();
        SDEntities.REGISTRY.register(bus);
        SDMenus.REGISTRY.register(bus);
        SDRecipes.REGISTRY.register(bus);
        SDScreens.register(bus);
//        SDCommands.REGISTER.register();
        SDTrees.register(bus, SDBlocks.REGISTRY, SDItems.REGISTRY);

        TAB.add(SDItems.REGISTRY);

        LOGGER.info("Reverence for heaven and earth, respect ghosts and gods.");
    }
}
