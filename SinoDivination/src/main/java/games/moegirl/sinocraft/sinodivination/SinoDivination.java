package games.moegirl.sinocraft.sinodivination;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Mod(SinoDivination.MODID)
public class SinoDivination {
    private static final Logger LOGGER = LoggerFactory.getLogger("SinoDivination");

    public static final String MODID = "sinodivination";

    public SinoDivination() {
        var bus = FMLJavaModLoadingContext.get().getModEventBus();
    }
}
