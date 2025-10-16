package games.moegirl.sinocraft.sinobrush.fabric;

import games.moegirl.sinocraft.sinobrush.client.SinoBrushClient;
import net.fabricmc.api.ClientModInitializer;

public class SinoBrushFabricClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        SinoBrushClient.initClient();
        SinoBrushClient.setupClient();
    }
}
