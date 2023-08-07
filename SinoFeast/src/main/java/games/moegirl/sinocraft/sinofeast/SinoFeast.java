package games.moegirl.sinocraft.sinofeast;

import games.moegirl.sinocraft.sinofeast.block.SFBlockItems;
import games.moegirl.sinocraft.sinofeast.block.SFBlocks;
import games.moegirl.sinocraft.sinofeast.item.SFItems;
import games.moegirl.sinocraft.sinofeast.networking.SFNetworking;
import games.moegirl.sinocraft.sinofeast.taste.TasteCodec;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DataPackRegistryEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Mod(SinoFeast.MODID)
public class SinoFeast {
    public static final String MODID = "sinofeast";

    private static SinoFeast INSTANCE;

    private final Logger logger = LoggerFactory.getLogger("SinoFeast");

    private final SFNetworking networking;

    public SinoFeast() {
        INSTANCE = this;

        networking = new SFNetworking();

        var bus = FMLJavaModLoadingContext.get().getModEventBus();

        SFBlocks.register(bus);
        SFBlockItems.register(bus);
        SFItems.register(bus);

        bus.addListener((DataPackRegistryEvent.NewRegistry event) ->
                event.dataPackRegistry(TasteCodec.TASTE_KEY, TasteCodec.TASTE_CODEC, TasteCodec.TASTE_CODEC));

    }

    public static SinoFeast getInstance() {
        return INSTANCE;
    }

    public Logger getLogger() {
        return logger;
    }

    public SFNetworking getNetworking() {
        return networking;
    }
}
