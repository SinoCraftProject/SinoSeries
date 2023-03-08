package games.moegirl.sinocraft.sinofeast;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Mod(SinoFeast.MODID)
public class SinoFeast {
    private static final Logger LOGGER = LoggerFactory.getLogger("SinoFeast");

    public static final String MODID = "sinofeast";

    public SinoFeast() {
        var bus = FMLJavaModLoadingContext.get().getModEventBus();
    }
}
