package games.moegirl.sinocraft.sinodeco.fabric;

import games.moegirl.sinocraft.sinodeco.SinoDeco;
import net.fabricmc.api.ModInitializer;

public class SinoDecoFabric implements ModInitializer {
    @Override
    public void onInitialize() {
        SinoDeco.init();
    }
}
