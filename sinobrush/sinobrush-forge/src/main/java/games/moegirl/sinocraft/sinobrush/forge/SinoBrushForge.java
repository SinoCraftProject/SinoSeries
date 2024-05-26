package games.moegirl.sinocraft.sinobrush.forge;

import games.moegirl.sinocraft.sinobrush.SinoBrush;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(SinoBrush.MODID)
public class SinoBrushForge {

    private final SinoBrush mod = new SinoBrush();

    public SinoBrushForge() {
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::clientSetup);

        mod.init();
    }

    private void clientSetup(FMLClientSetupEvent event) {
        mod.initClient();
    }
}
