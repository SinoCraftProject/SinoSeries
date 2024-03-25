package games.moegirl.sinocraft.sinobrush.fabric;

import games.moegirl.sinocraft.sinobrush.SinoBrush;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.ModInitializer;

public class SinoBrushFabric implements ModInitializer, ClientModInitializer {

    private final SinoBrush mod = new SinoBrush();

    @Override
    public void onInitialize() {
        mod.init();
    }

    @Override
    public void onInitializeClient() {
        mod.initClient();
    }
}
