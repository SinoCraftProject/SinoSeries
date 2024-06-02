package games.moegirl.sinocraft.sinodeco.fabric;

import games.moegirl.sinocraft.sinodeco.SinoDeco;
import net.fabricmc.api.ModInitializer;

public class SinoDecoFabric implements ModInitializer {
    private final SinoDeco mod = new SinoDeco();

    @Override
    public void onInitialize() {
        mod.init();
    }
}
