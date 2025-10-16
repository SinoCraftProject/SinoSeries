package games.moegirl.sinocraft.sinodeco.neoforge.event;

import games.moegirl.sinocraft.sinodeco.SinoDeco;
import games.moegirl.sinocraft.sinodeco.client.entity.DummyChairEntityRenderer;
import games.moegirl.sinocraft.sinodeco.entity.SDEntities;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;

@EventBusSubscriber(modid = SinoDeco.MODID, bus = EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class EntityRendererRegistry {
    @SubscribeEvent
    public static void onRegister(EntityRenderersEvent.RegisterRenderers event) {
        event.registerEntityRenderer(SDEntities.DUMMY_CHAIR.get(), DummyChairEntityRenderer::new);
    }
}
