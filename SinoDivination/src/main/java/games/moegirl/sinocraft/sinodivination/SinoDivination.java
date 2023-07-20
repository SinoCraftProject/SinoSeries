package games.moegirl.sinocraft.sinodivination;

import games.moegirl.sinocraft.sinodivination.block.SDBlocks;
import games.moegirl.sinocraft.sinodivination.blockentity.SDBlockEntities;
import games.moegirl.sinocraft.sinodivination.client.screen.SDScreens;
import games.moegirl.sinocraft.sinodivination.item.SDItems;
import games.moegirl.sinocraft.sinodivination.menu.SDMenus;
import games.moegirl.sinocraft.sinodivination.recipe.SDRecipes;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Mod(SinoDivination.MODID)
public class SinoDivination {
    public static final String MODID = "sinodivination";
    public static final String NAME = "SinoDivination";

    private static final Logger LOGGER = LoggerFactory.getLogger(NAME);

    public SinoDivination() {
        LOGGER.info("Loading SinoDivination.");

        IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();

        SDBlocks.register(bus);
        SDItems.register(bus);
        SDBlockEntities.register(bus);
        SDMenus.register(bus);
        SDRecipes.register(bus);
        SDScreens.register(bus);
        SDTrees.register(bus);

        LOGGER.info("Reverence for heaven and earth, respect ghosts and gods.");
    }
}
