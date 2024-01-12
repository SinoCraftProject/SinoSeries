package games.moegirl.sinocraft.sinocore.forge;

import games.moegirl.sinocraft.sinocore.SinoCore;
import net.minecraftforge.fml.common.Mod;

@Mod(SinoCore.MODID)
public class SinoCoreForge {

    public SinoCoreForge() {
        SinoCore.registerAll();
    }
}
