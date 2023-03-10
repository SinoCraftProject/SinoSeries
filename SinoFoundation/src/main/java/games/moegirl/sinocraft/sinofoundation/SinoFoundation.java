package games.moegirl.sinocraft.sinofoundation;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Mod(SinoFoundation.MODID)
public class SinoFoundation {
    private static final Logger LOGGER = LoggerFactory.getLogger("SinoFoundation");

    public static final String MODID = "sinofoundation";

    public SinoFoundation() {
        LOGGER.info("Loading SinoFoundation.");

        var bus = FMLJavaModLoadingContext.get().getModEventBus();

        LOGGER.info("Shake it, baby!");
    }

    static Logger getLogger() {
        return LOGGER;
    }
}
