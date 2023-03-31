package games.moegirl.sinocraft.sinocalligraphy;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Mod(SinoCalligraphy.MODID)
public class SinoCalligraphy {
    public static final Logger LOGGER = LoggerFactory.getLogger("SinoCalligraphy");

    public static final String MODID = "sinocalligraphy";

    private static SinoCalligraphy INSTANCE = null;

    public SinoCalligraphy() {
        INSTANCE = this;

        LOGGER.info("Loading SinoCalligraphy.");

        var bus = FMLJavaModLoadingContext.get().getModEventBus();


        LOGGER.info("I'll give you a little color.");
    }

    public static SinoCalligraphy getInstance() {
        return INSTANCE;
    }
}
