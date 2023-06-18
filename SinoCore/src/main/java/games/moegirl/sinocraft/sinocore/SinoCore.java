package games.moegirl.sinocraft.sinocore;

import games.moegirl.sinocraft.sinocore.data.loottable.SCLootConditions;
import games.moegirl.sinocraft.sinocore.world.gen.SCBiomeModifiers;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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

        SCLootConditions.register(bus);
        SCBiomeModifiers.BIOME_MODIFIER_SERIALIZERS.register(bus);

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
