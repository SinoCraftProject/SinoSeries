package games.moegirl.sinocraft.sinotest;

import games.moegirl.sinocraft.sinotest.sinocore.TestSinoCore;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(SinoTest.MODID)
public class SinoTest {
    public static final String MODID = "sinotest";

    public SinoTest() {
        var bus = FMLJavaModLoadingContext.get().getModEventBus();
        new TestSinoCore().register(bus);
    }
}
