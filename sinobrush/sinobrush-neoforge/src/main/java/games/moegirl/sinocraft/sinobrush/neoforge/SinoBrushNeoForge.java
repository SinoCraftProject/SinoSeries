package games.moegirl.sinocraft.sinobrush.neoforge;

import games.moegirl.sinocraft.sinobrush.SinoBrush;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.loading.FMLEnvironment;

@Mod(SinoBrush.MODID)
public class SinoBrushNeoForge {

    private final SinoBrush mod = new SinoBrush();

    public SinoBrushNeoForge(IEventBus bus, ModContainer modContainer) {
        mod.init();
        mod.initClient();
    }
}
