package games.moegirl.sinocraft.sinobrush.fabric;

import games.moegirl.sinocraft.sinobrush.SinoBrush;
import net.fabricmc.api.ModInitializer;

public class SinoBrushFabric implements ModInitializer {
    @Override
    public void onInitialize() {
        SinoBrush.init();
    }
}
