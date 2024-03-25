package games.moegirl.sinocraft.sinocore.fabric;

import games.moegirl.sinocraft.sinocore.SinoCore;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;

public class SinoCoreFabric implements ModInitializer {
    @Override
    public void onInitialize() {
        ServerLifecycleEvents.SERVER_STARTED.register(server -> SinoCorePlatformImpl.SERVER = server);
        ServerLifecycleEvents.SERVER_STOPPED.register(server -> SinoCorePlatformImpl.SERVER = null);

        new SinoCore().init();
    }
}
