package games.moegirl.sinocraft.sinotest.fabric;

import games.moegirl.sinocraft.sinotest.SinoTest;
import net.fabricmc.api.ModInitializer;

public class SinoTestFabric implements ModInitializer {

    @Override
    public void onInitialize() {
        SinoTest.registerAll();
    }
}
