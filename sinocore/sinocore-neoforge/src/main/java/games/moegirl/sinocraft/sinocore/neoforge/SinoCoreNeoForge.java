package games.moegirl.sinocraft.sinocore.neoforge;

import games.moegirl.sinocraft.sinocore.SinoCore;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;

@Mod(SinoCore.MODID)
public class SinoCoreNeoForge {

    private static IEventBus MOD_BUS;

    private final SinoCore mod;

    public SinoCoreNeoForge(IEventBus bus, ModContainer container) {
        MOD_BUS = bus;
        mod = new SinoCore();

        bus.addListener(this::setup);
    }

    private void setup(FMLCommonSetupEvent event) {
        mod.init();
        event.enqueueWork(mod::setup);
    }

    public static IEventBus getModBus() {
        return MOD_BUS;
    }
}
