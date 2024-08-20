package games.moegirl.sinocraft.sinocore.event.client.render.forge;

import games.moegirl.sinocraft.sinocore.SinoCore;
import games.moegirl.sinocraft.sinocore.event.client.RenderEvents;
import games.moegirl.sinocraft.sinocore.event.client.args.render.RenderItemInFrameArgs;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RenderItemInFrameEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = SinoCore.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE, value = Dist.CLIENT)
public class RenderEventImpl {
    @SubscribeEvent
    public static void onRenderInFrame(RenderItemInFrameEvent event) {
        var result = RenderEvents.RENDER_ITEM_IN_FRAME.invoke(new RenderItemInFrameArgs(event.getItemStack(),
                event.getItemFrameEntity(), event.getRenderer(),
                event.getPoseStack(), event.getMultiBufferSource(), event.getPackedLight()));
        if (result.isCancelled()) {
            event.setCanceled(true);
        }
    }
}
