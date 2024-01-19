package games.moegirl.sinocraft.sinotest.neoforge;

import games.moegirl.sinocraft.sinotest.SinoTest;
import net.neoforged.fml.common.Mod;

@Mod(SinoTest.MODID)
public class SinoTestNeoForge {

    public SinoTestNeoForge() {
        SinoTest.registerAll();
    }
}
