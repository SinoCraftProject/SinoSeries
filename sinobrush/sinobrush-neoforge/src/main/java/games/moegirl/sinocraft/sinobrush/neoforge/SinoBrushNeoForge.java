package games.moegirl.sinocraft.sinobrush.neoforge;

import games.moegirl.sinocraft.sinobrush.SinoBrush;
import net.neoforged.fml.common.Mod;

@Mod(SinoBrush.MODID)
public class SinoBrushNeoForge {
    public SinoBrushNeoForge() {
        var mod = new SinoBrush();
        mod.init();
    }
}
