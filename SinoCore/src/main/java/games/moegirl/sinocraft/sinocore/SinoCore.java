package games.moegirl.sinocraft.sinocore;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Mod(SinoCore.MODID)
public class SinoCore {
    private static final Logger LOGGER = LoggerFactory.getLogger("SinoCore");

    public static final String MODID = "sinocore";

    public SinoCore() {
        LOGGER.info("Loading SinoCore.");

        var bus = FMLJavaModLoadingContext.get().getModEventBus();
    }
}
