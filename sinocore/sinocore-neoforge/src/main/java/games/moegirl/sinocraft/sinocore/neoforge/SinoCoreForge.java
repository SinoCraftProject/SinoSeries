package games.moegirl.sinocraft.sinocore.neoforge;

import games.moegirl.sinocraft.sinocore.SinoCore;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(SinoCore.MODID)
public class SinoCoreForge {
    private final SinoCore mod = new SinoCore();

    public SinoCoreForge() {
        mod.init();

        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setup);
    }

    private void setup(FMLCommonSetupEvent event) {
        event.enqueueWork(mod::setup);
    }
}
