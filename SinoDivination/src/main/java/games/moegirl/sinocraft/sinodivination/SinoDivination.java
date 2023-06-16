package games.moegirl.sinocraft.sinodivination;

import games.moegirl.sinocraft.sinodivination.block.SDBlocks;
import games.moegirl.sinocraft.sinodivination.blockentity.SDBlockEntities;
import games.moegirl.sinocraft.sinodivination.client.screen.SDScreens;
import games.moegirl.sinocraft.sinodivination.item.SDItems;
import games.moegirl.sinocraft.sinodivination.menu.SDMenus;
import games.moegirl.sinocraft.sinodivination.recipe.SDRecipes;
import games.moegirl.sinocraft.sinodivination.tree.SDTrees;
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

    private static final Logger LOGGER = LoggerFactory.getLogger(NAME);

    public SinoDivination() {
        LOGGER.info("Loading SinoDivination. Ver: " + VERSION);

        IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();

//        SDNetworks.register();
        SDBlocks.register(bus);
        SDItems.register(bus);
        SDBlockEntities.register(bus);
//        SDEntities.REGISTRY.register(bus);
        SDMenus.register(bus);
        SDRecipes.register(bus);
        SDScreens.register(bus);
        SDTrees.register(bus);

        LOGGER.info("Reverence for heaven and earth, respect ghosts and gods.");
    }
}
