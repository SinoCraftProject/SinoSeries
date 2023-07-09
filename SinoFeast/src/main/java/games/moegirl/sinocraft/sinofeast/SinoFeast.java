package games.moegirl.sinocraft.sinofeast;

import games.moegirl.sinocraft.sinofeast.block.SFBlockItems;
import games.moegirl.sinocraft.sinofeast.block.SFBlocks;
import games.moegirl.sinocraft.sinofeast.item.SFItems;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Mod(SinoFeast.MODID)
public class SinoFeast {
    public static final String MODID = "sinofeast";

    private static SinoFeast INSTANCE;

    private final Logger logger = LoggerFactory.getLogger("SinoFeast");

    public SinoFeast() {
        INSTANCE = this;

        var bus = FMLJavaModLoadingContext.get().getModEventBus();

        SFBlocks.register(bus);
        SFBlockItems.register(bus);
        SFItems.register(bus);
    }

    public static SinoFeast getInstance() {
        return INSTANCE;
    }

    public Logger getLogger() {
        return logger;
    }
}
