package games.moegirl.sinocraft.sinodeco.fabric;

import games.moegirl.sinocraft.sinodeco.SinoDeco;
import games.moegirl.sinocraft.sinodeco.client.entity.DummyChairEntityRenderer;
import games.moegirl.sinocraft.sinodeco.entity.SDEntities;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;

public class SinoDecoFabricClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        SinoDeco.initClient();

        EntityRendererRegistry.register(SDEntities.DUMMY_CHAIR.get(), DummyChairEntityRenderer::new);
    }
}
