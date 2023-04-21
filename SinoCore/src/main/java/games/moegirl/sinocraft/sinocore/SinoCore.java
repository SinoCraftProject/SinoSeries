package games.moegirl.sinocraft.sinocore;

import games.moegirl.sinocraft.sinocore.old.utility.json.JsonUtils;
import games.moegirl.sinocraft.sinocore.old.utility.json.serializer.FluidStackSerializer;
import games.moegirl.sinocraft.sinocore.old.utility.json.serializer.IngredientSerializer;
import games.moegirl.sinocraft.sinocore.old.utility.json.serializer.ItemStackSerializer;
import games.moegirl.sinocraft.sinocore.old.utility.json.serializer.NonNullListSerializer;
import net.minecraft.core.NonNullList;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Properties;

@Mod(SinoCore.MODID)
public class SinoCore {
    public static final Logger LOGGER = LoggerFactory.getLogger("SinoCore");

    public static final boolean DEBUG = System.getProperty("sinoseries.debug", "false").equalsIgnoreCase("true");

    public static final String MODID = "sinocore";
    public static final String VERSION = "1.19.4-1.4.1";

    private static SinoCore INSTANCE = null;

    public SinoCore() {
        LOGGER.info("Loading SinoCore. Ver: " + VERSION);

        INSTANCE = this;

        IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();

        bus.addListener(this::onSetup);
        bus.addListener(this::onClientSetup);

        JsonUtils.INSTANCE
                .registerAdapter(Ingredient.class, new IngredientSerializer())
                .registerAdapter(ItemStack.class, new ItemStackSerializer())
                .registerAdapter(FluidStack.class, new FluidStackSerializer())
                .registerAdapter(NonNullList.class, new NonNullListSerializer());

        LOGGER.info("SinoCore loaded!");
    }

    public static SinoCore getInstance() {
        return INSTANCE;
    }

    private void onSetup(FMLCommonSetupEvent event) {
    }

    private void onClientSetup(FMLClientSetupEvent event) {
    }
}
