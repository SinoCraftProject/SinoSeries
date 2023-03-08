package games.moegirl.sinocraft.sinofoundation;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(SinoFoundation.MODID)
public class SinoFoundation {
    public static final String MODID = "sinofoundation";

    public SinoFoundation() {
        var bus = FMLJavaModLoadingContext.get().getModEventBus();
    }
}
