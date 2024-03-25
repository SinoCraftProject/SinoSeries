package games.moegirl.sinocraft.sinobrush.forge;

import games.moegirl.sinocraft.sinobrush.SinoBrush;
import net.minecraftforge.fml.common.Mod;

@Mod(SinoBrush.MODID)
public class SinoBrushForge {
    public SinoBrushForge() {
        var mod = new SinoBrush();
        mod.init();
    }
}
