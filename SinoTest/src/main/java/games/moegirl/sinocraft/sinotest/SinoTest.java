package games.moegirl.sinocraft.sinotest;

import mezz.jei.forge.JustEnoughItems;
import net.minecraftforge.fml.common.Mod;

@Mod(SinoTest.MODID)
public class SinoTest {
    public static final String MODID = "sinotest";

    public SinoTest() {
        // load jei
        new JustEnoughItems();
    }
}
