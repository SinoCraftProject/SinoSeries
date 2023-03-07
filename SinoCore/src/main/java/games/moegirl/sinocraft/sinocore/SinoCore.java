package games.moegirl.sinocraft.sinocore;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(SinoCore.MODID)
public class SinoCore {
    public static final String MODID = "sinocore";

    public SinoCore() {
        var bus = FMLJavaModLoadingContext.get().getModEventBus();

        System.out.println("Hello!");
    }
}
