package games.moegirl.sinocraft.sinocore.neoforge.eventhandler;

import games.moegirl.sinocraft.sinocore.event.client.RenderEvents;
import games.moegirl.sinocraft.sinocore.event.client.args.render.BeforeRenderHudArgs;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.RenderGuiEvent;

@EventBusSubscriber
public class ScreenEventHandler {

    @SubscribeEvent
    public static void onGuiRender(RenderGuiEvent.Pre event) {
        RenderEvents.BEFORE_RENDER_HUD.invoke(new BeforeRenderHudArgs(event.getGuiGraphics(), event.getPartialTick()));
    }
}
