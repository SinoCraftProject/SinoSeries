package games.moegirl.sinocraft.sinobrush.utility;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import games.moegirl.sinocraft.sinobrush.SinoBrush;
import games.moegirl.sinocraft.sinobrush.drawing.Drawing;
import games.moegirl.sinocraft.sinocore.utility.GLSwitcher;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemDisplayContext;

public class DrawingRenderer {
    public static void renderInGui(GuiGraphics guiGraphics, int x, int y, int width, int height, Drawing drawing, float partialTick) {
        guiGraphics.fill(x, y, x + width, y + height, ColorHelper.rgbToARGB(drawing.getPaperColor()));

        var pW = (int)Math.floor(160.0F / Math.max(1, drawing.getWidth()));
        var pH = (int)Math.floor(160.0F / Math.max(1, drawing.getHeight()));
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

    public static void renderInHand(PoseStack poseStack, MultiBufferSource buffer, ItemDisplayContext context, int light, int overlay, Drawing drawing) {
        poseStack.pushPose();
        RenderSystem.disableDepthTest();
        RenderSystem.disableCull();
        if (context == ItemDisplayContext.FIXED) {
            poseStack.mulPose(Axis.YP.rotationDegrees(180));
            poseStack.scale(1, -1, 1);
            poseStack.translate(-1.5, -1.5, -0.5);
            poseStack.scale(0.0625F, 0.0625F, 0.0625F);
            poseStack.translate(0, 0, 0.01);
        } else {
            poseStack.scale(0.03125F, 0.03125F, 1);
            poseStack.scale(drawing.getWidth(), drawing.getHeight(), 1);
        }

//        blitHandheldBackground(poseStack, buffer, 0, 0, drawing.getWidth(), drawing.getHeight(), light, overlay);

        fillRect(poseStack, buffer, 0, 0, drawing.getWidth(), drawing.getHeight(),
                ColorHelper.rgbToARGB(drawing.getPaperColor()), light, overlay);
        if (!drawing.isEmpty()) {
            try (var ignored = GLSwitcher.blend().enable()) {
                for (var i = 0; i < drawing.getWidth(); i++) {
                    for (var j = 0; j < drawing.getHeight(); j++) {
                        var color = ColorHelper.pixelColorToARGB(drawing.getPixel(i, j), drawing.getInkColor());
                        fillRect(poseStack, buffer, i, j, i + 1, j + 1, color, light, overlay);
                    }
                }
            }
        }

        poseStack.popPose();
    }

    private static void fillGuiRect(GuiGraphics guiGraphics, int minX, int minY, int maxX, int maxY, int color) {
        var matrix4f = guiGraphics.pose().last().pose();
        if (minX < maxX) {
            var temp = minX;
            minX = maxX;
            maxX = temp;
        }
        if (minY < maxY) {
            var temp = minY;
            minY = maxY;
            maxY = temp;
        }
        var buffer = guiGraphics.bufferSource().getBuffer(RenderType.guiOverlay());
        buffer.vertex(matrix4f, minX, minY, 0).color(color).endVertex();
        buffer.vertex(matrix4f, minX, maxY, 0).color(color).endVertex();
        buffer.vertex(matrix4f, maxX, maxY, 0).color(color).endVertex();
        buffer.vertex(matrix4f, maxX, minY, 0).color(color).endVertex();
        guiGraphics.flush();
    }

    private static void fillRect(PoseStack poseStack, MultiBufferSource bufferSource,
                                 int minX, int minY, int maxX, int maxY,
                                 int color, int light, int overlay) {
        var buffer = bufferSource.getBuffer(RenderType.textBackground());
        buffer.vertex(poseStack.last().pose(), minX, minY, 0).color(color).uv2(light, overlay).endVertex();
        buffer.vertex(poseStack.last().pose(), minX, maxY, 0).color(color).uv2(light, overlay).endVertex();
        buffer.vertex(poseStack.last().pose(), maxX, maxY, 0).color(color).uv2(light, overlay).endVertex();
        buffer.vertex(poseStack.last().pose(), maxX, minY, 0).color(color).uv2(light, overlay).endVertex();
    }

    public static final ResourceLocation HANDHELD_BACKGROUND = new ResourceLocation(SinoBrush.MODID, "paper/");

    private static void blitHandheldBackground(PoseStack poseStack, MultiBufferSource bufferSource,
                                 int minX, int minY, int maxX, int maxY, int light, int overlay) {
        var buffer = bufferSource.getBuffer(RenderType.text(HANDHELD_BACKGROUND));
        var color = 0xFF000000;
        buffer.vertex(poseStack.last().pose(), minX, minY, 0).color(color).uv(0, 0).uv2(light, overlay).endVertex();
        buffer.vertex(poseStack.last().pose(), minX, maxY, 0).color(color).uv(0, 1).uv2(light, overlay).endVertex();
        buffer.vertex(poseStack.last().pose(), maxX, maxY, 0).color(color).uv(1, 1).uv2(light, overlay).endVertex();
        buffer.vertex(poseStack.last().pose(), maxX, minY, 0).color(color).uv(1, 0).uv2(light, overlay).endVertex();
    }
}
