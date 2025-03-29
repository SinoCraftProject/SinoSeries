package games.moegirl.sinocraft.sinobrush.handler;

import games.moegirl.sinocraft.sinobrush.client.DrawingRenderer;
import games.moegirl.sinocraft.sinobrush.client.FanRenderer;
import games.moegirl.sinocraft.sinobrush.item.SBRItems;
import games.moegirl.sinocraft.sinobrush.item.component.Drawing;
import games.moegirl.sinocraft.sinocore.client.model.TransformFilteredItemModel;
import games.moegirl.sinocraft.sinocore.event.client.ModelEvents;
import games.moegirl.sinocraft.sinocore.event.client.RenderEvents;
import net.minecraft.world.item.ItemDisplayContext;

import java.util.List;

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

        RenderEvents.BEFORE_RENDER_HUD.register(args -> FanRenderer.renderInHud(args.guiGraphics(), false));

        RenderEvents.CUSTOM_ITEM_RENDERER.register(args ->
                args.register().register(new FanRenderer(), SBRItems.FAN.get()));

        ModelEvents.AFTER_BAKE.register(args -> {
            if (FanRenderer.MODEL_FAN.equals(args.id())) {
                FanRenderer.DEFAULT_FAN_MODEL = args.model();
                args.setModel(TransformFilteredItemModel.create(args.model(), List.of(ItemDisplayContext.values())));
            }
        });
    }
}
