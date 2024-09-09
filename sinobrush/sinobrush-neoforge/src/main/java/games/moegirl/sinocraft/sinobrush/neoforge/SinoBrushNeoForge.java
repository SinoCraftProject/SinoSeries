package games.moegirl.sinocraft.sinobrush.neoforge;

import games.moegirl.sinocraft.sinobrush.SinoBrush;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;

@Mod(SinoBrush.MODID)
public class SinoBrushNeoForge {

    private final SinoBrush mod = new SinoBrush();

    public SinoBrushNeoForge(IEventBus bus, ModContainer modContainer) {
        bus.addListener(this::setup);
        bus.addListener(this::clientSetup);
    }

    private void setup(FMLCommonSetupEvent event) {
        mod.init();
    }

    private void clientSetup(FMLClientSetupEvent event) {
        mod.initClient();
    }
}
