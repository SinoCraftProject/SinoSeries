package games.moegirl.sinocraft.sinofoundation;

import games.moegirl.sinocraft.sinofoundation.item.SFDItems;
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

        SFDItems.register(bus);

        LOGGER.info("Shake it, baby!");
    }

    static Logger getLogger() {
        return LOGGER;
    }
}
