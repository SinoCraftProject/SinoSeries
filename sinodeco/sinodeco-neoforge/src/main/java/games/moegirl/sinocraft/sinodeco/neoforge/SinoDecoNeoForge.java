package games.moegirl.sinocraft.sinodeco.neoforge;

import games.moegirl.sinocraft.sinodeco.SinoDeco;
import net.neoforged.fml.common.Mod;

@Mod(SinoDeco.MODID)
public class SinoDecoNeoForge {
    public SinoDecoNeoForge() {
        SinoDeco mod = new SinoDeco();
        mod.init();
    }
}
