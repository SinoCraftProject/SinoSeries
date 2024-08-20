package games.moegirl.sinocraft.sinobrush.client;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import games.moegirl.sinocraft.sinobrush.SinoBrush;
import games.moegirl.sinocraft.sinobrush.drawing.Drawing;
import games.moegirl.sinocraft.sinobrush.utility.ColorHelper;
import games.moegirl.sinocraft.sinocore.utility.GLSwitcher;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.decoration.ItemFrame;
import net.minecraft.world.item.MapItem;
import net.minecraft.world.level.saveddata.maps.MapItemSavedData;

public class DrawingRenderer {
    public static void renderInGui(GuiGraphics guiGraphics, int x, int y, int width, int height,
                                   Drawing drawing, float partialTick) {
        guiGraphics.fill(x, y, x + width, y + height, ColorHelper.rgbToARGB(drawing.getPaperColor()));

        var pW = width / Math.max(1, drawing.getWidth());
        var pH = height / Math.max(1, drawing.getHeight());
        if (!drawing.isEmpty()) {
            try (var ignored = GLSwitcher.blend().enable()) {
                for (var i = 0; i < drawing.getWidth(); i++) {
                    for (var j = 0; j < drawing.getHeight(); j++) {
                        var pX = x + (i * pW);
                        var pY = y + (j * pH);
                        var color = drawing.getPixel(i, j);
                        fillGuiRect(guiGraphics, pX, pY, pX + pW, pY + pH, ColorHelper.pixelColorToARGB(color, drawing.getInkColor()));
                    }
                }
            }
        }
    }

    private static final float HANDHELD_PAPER_SIZE = 16;

    public static void renderInHand(PoseStack poseStack, MultiBufferSource bufferSource, int combinedLight,
                                    Drawing drawing) {
        poseStack.pushPose();
        poseStack.mulPose(Axis.YP.rotationDegrees(180.0F));
        poseStack.mulPose(Axis.ZP.rotationDegrees(180.0F));
        poseStack.scale(0.38F, 0.38F, 0.38F);
        poseStack.translate(-0.5D, -0.5D, 0);
        poseStack.scale(0.0625F, 0.0625F, 0.0078125F);
        // Todo: qyl27: handheld background texture.
//            blitHandheldBackground(poseStack, buffer, 0, 0, drawing.getWidth(), drawing.getHeight(), combinedLight);
        render(poseStack, bufferSource, combinedLight, HANDHELD_PAPER_SIZE, HANDHELD_PAPER_SIZE, 0.01F, 0, drawing);
        poseStack.popPose();
    }

    public static void renderInFrame(PoseStack poseStack, MultiBufferSource bufferSource, int combinedLight,
                                     ItemFrame frame, Drawing drawing) {
        var i = frame.getRotation() % 4 * 2;
        poseStack.pushPose();
        poseStack.mulPose(Axis.ZP.rotationDegrees(i * 360.0F / 8.0F));
        poseStack.mulPose(Axis.ZP.rotationDegrees(180.0F));
        poseStack.scale(0.0078125F, 0.0078125F, 0.0078125F);
        poseStack.translate(-64.0F, -64.0F, 0.0F);
        poseStack.translate(0.0F, 0.0F, -1.0F);
        var light = getLightVal(frame, 15728850, combinedLight);
        render(poseStack, bufferSource, light, 128, 128, -0.01F, -0.02F, drawing);
        poseStack.popPose();
    }

    private static int getLightVal(ItemFrame itemFrame, int glowLightVal, int regularLightVal) {
        return itemFrame.getType() == EntityType.GLOW_ITEM_FRAME ? glowLightVal : regularLightVal;
    }

    private static void render(PoseStack poseStack, MultiBufferSource bufferSource, int combinedLight,
                               float width, float height, float backZ, float foreZ, Drawing drawing) {
        var pW = width / Math.max(1, drawing.getWidth());
        var pH = height / Math.max(1, drawing.getHeight());

        var buffer = bufferSource.getBuffer(RenderType.textBackground());
        try (var ignored1 = GLSwitcher.blend().enable(); var ignored2 = GLSwitcher.depth().enable()) {
            fillRect(poseStack, buffer, 0, 0, width, height, backZ,
                    ColorHelper.rgbToARGB(drawing.getPaperColor()), combinedLight);
            if (!drawing.isEmpty()) {
                for (var i = 0; i < drawing.getWidth(); i++) {
                    for (var j = 0; j < drawing.getHeight(); j++) {
                        var pX = i * pW;
                        var pY = j * pH;
                        var color = ColorHelper.pixelColorToARGB(drawing.getPixel(i, j), drawing.getInkColor());
                        fillRect(poseStack, buffer, pX, pY, pX + pW, pY + pH, foreZ, color, combinedLight);
                    }
                }
            }
        }
    }

    private static void fillGuiRect(GuiGraphics guiGraphics, float minX, float minY, float maxX, float maxY, int color) {
        var matrix4f = guiGraphics.pose().last().pose();
        var buffer = guiGraphics.bufferSource().getBuffer(RenderType.guiOverlay());
        buffer.vertex(matrix4f, minX, minY, 0).color(color).endVertex();
        buffer.vertex(matrix4f, minX, maxY, 0).color(color).endVertex();
        buffer.vertex(matrix4f, maxX, maxY, 0).color(color).endVertex();
        buffer.vertex(matrix4f, maxX, minY, 0).color(color).endVertex();
        guiGraphics.flush();
    }

    private static void fillRect(PoseStack poseStack, VertexConsumer buffer,
                                 float minX, float minY, float maxX, float maxY,
                                 float zIndex,
                                 int color, int combinedLight) {
        buffer.vertex(poseStack.last().pose(), minX, minY, zIndex).color(color).uv2(combinedLight).endVertex();
        buffer.vertex(poseStack.last().pose(), minX, maxY, zIndex).color(color).uv2(combinedLight).endVertex();
        buffer.vertex(poseStack.last().pose(), maxX, maxY, zIndex).color(color).uv2(combinedLight).endVertex();
        buffer.vertex(poseStack.last().pose(), maxX, minY, zIndex).color(color).uv2(combinedLight).endVertex();
    }

    public static final ResourceLocation HANDHELD_BACKGROUND = new ResourceLocation(SinoBrush.MODID, "paper_backrgound");

    private static void blitHandheldBackground(PoseStack poseStack, MultiBufferSource bufferSource,
                                               float minX, float minY, float maxX, float maxY, int combinedLight) {
        var buffer = bufferSource.getBuffer(RenderType.text(HANDHELD_BACKGROUND));
        var color = 0xFF000000;
        buffer.vertex(poseStack.last().pose(), minX, minY, 0).color(color).uv(0, 0).uv2(combinedLight).endVertex();
        buffer.vertex(poseStack.last().pose(), minX, maxY, 0).color(color).uv(0, 1).uv2(combinedLight).endVertex();
        buffer.vertex(poseStack.last().pose(), maxX, maxY, 0).color(color).uv(1, 1).uv2(combinedLight).endVertex();
        buffer.vertex(poseStack.last().pose(), maxX, minY, 0).color(color).uv(1, 0).uv2(combinedLight).endVertex();
    }
}
