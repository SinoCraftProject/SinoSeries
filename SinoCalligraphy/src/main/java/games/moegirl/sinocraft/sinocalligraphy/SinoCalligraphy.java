package games.moegirl.sinocraft.sinocalligraphy;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(SinoCalligraphy.MODID)
public class SinoCalligraphy {
    public static final String MODID = "sinocalligraphy";

    public SinoCalligraphy() {
        var bus = FMLJavaModLoadingContext.get().getModEventBus();

    }
}
