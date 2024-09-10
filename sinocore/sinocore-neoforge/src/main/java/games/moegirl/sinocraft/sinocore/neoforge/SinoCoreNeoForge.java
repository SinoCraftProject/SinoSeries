package games.moegirl.sinocraft.sinocore.neoforge;

import games.moegirl.sinocraft.sinocore.SinoCore;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;

@Mod(SinoCore.MODID)
public class SinoCoreNeoForge {

    private final SinoCore mod;

    public SinoCoreNeoForge(IEventBus bus, ModContainer container) {
        mod = new SinoCore();

        mod.init();
        bus.addListener(this::setup);
    }

    private void setup(FMLCommonSetupEvent event) {
        event.enqueueWork(mod::setup);
    }
}
