package games.moegirl.sinocraft.sinotest.forge;

import games.moegirl.sinocraft.sinotest.SinoTest;
import net.minecraftforge.fml.common.Mod;

@Mod(SinoTest.MODID)
public class SinoTestForge {

    public SinoTestForge() {
        SinoTest.registerAll();
    }
}
