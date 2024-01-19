package games.moegirl.sinocraft.sinocore.fabric;

import games.moegirl.sinocraft.sinocore.SinoCore;
import net.fabricmc.api.ModInitializer;

public class SinoCoreFabric implements ModInitializer {
    @Override
    public void onInitialize() {
        new SinoCore().init();
    }
}
