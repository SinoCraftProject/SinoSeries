package games.moegirl.sinocraft.sinobrush.neoforge;

import games.moegirl.sinocraft.sinobrush.SinoBrush;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(SinoBrush.MODID)
public class SinoBrushNeoForge {

    private final SinoBrush mod = new SinoBrush();

    public SinoBrushNeoForge() {
        mod.init();
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::clientSetup);
    }

    private void clientSetup(FMLClientSetupEvent event) {
        mod.initClient();
    }
}
