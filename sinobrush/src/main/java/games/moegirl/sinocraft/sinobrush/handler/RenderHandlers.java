package games.moegirl.sinocraft.sinobrush.handler;

import games.moegirl.sinocraft.sinobrush.client.DrawingRenderer;
import games.moegirl.sinocraft.sinobrush.client.FanRenderer;
import games.moegirl.sinocraft.sinobrush.item.SBRItems;
import games.moegirl.sinocraft.sinobrush.item.component.Drawing;
import games.moegirl.sinocraft.sinocore.event.client.RenderEvents;

public class RenderHandlers {
    public static void register() {
        RenderEvents.RENDER_ITEM_IN_FRAME.register(args -> {
            if (args.isCancelled()) {
                return;
            }

            var item = args.itemStack();
            if (item.is(SBRItems.FILLED_XUAN_PAPER.get())) {
                var drawing = Drawing.get(item);
                DrawingRenderer.renderInFrame(args.poseStack(), args.multiBufferSource(), args.packedLight(), args.itemFrameEntity(), drawing);
                args.cancel();
            }
        });

        RenderEvents.BEFORE_RENDER_HUD.register(args -> FanRenderer.renderInHud(args.guiGraphics()));
    }
}
