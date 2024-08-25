package games.moegirl.sinocraft.sinodeco.forge;

import games.moegirl.sinocraft.sinodeco.SinoDeco;
import net.minecraftforge.fml.common.Mod;

@Mod(SinoDeco.MODID)
public class SinoDecoForge {
    public SinoDecoForge() {
        SinoDeco mod = new SinoDeco();
        mod.init();
    }
}
