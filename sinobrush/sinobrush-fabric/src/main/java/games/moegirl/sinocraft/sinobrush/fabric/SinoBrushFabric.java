package games.moegirl.sinocraft.sinobrush.fabric;

import games.moegirl.sinocraft.sinobrush.SinoBrush;
import games.moegirl.sinocraft.sinobrush.client.SinoBrushClient;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.loader.api.FabricLoader;

public class SinoBrushFabric implements ModInitializer {
    @Override
    public void onInitialize() {
        SinoBrush.init();
        if (FabricLoader.getInstance().getEnvironmentType() == EnvType.CLIENT) {
            SinoBrushClient.initClient();
        }
    }
}
