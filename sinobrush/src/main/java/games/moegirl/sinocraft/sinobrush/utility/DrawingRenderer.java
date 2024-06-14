package games.moegirl.sinocraft.sinobrush.utility;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import games.moegirl.sinocraft.sinobrush.SinoBrush;
import games.moegirl.sinocraft.sinobrush.drawing.Drawing;
import games.moegirl.sinocraft.sinocore.utility.GLSwitcher;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.renderer.ItemInHandRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.player.PlayerRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.HumanoidArm;
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
        var minecraft = Minecraft.getInstance();
        var player = minecraft.player;
        assert player != null;

        var partialTicks = minecraft.getFrameTime();
        var attackAnim = player.getAttackAnim(partialTicks);
        var rightHanded = player.getMainArm() == HumanoidArm.RIGHT;
        var handRenderer = minecraft.getEntityRenderDispatcher().getItemInHandRenderer();

        var onMainHand = true;

        if (rightHanded) {
            if (context == ItemDisplayContext.FIRST_PERSON_LEFT_HAND) {
                onMainHand = false;
            }
        } else {
            if (context == ItemDisplayContext.FIRST_PERSON_RIGHT_HAND) {
                onMainHand = false;
            }
        }

        if (onMainHand) {
            var swingProgress = player.swingingArm == InteractionHand.MAIN_HAND ? attackAnim : 0;
            var equippedProgress = 1.0f - Mth.lerp(partialTicks, handRenderer.oMainHandHeight, handRenderer.mainHandHeight);
            if (player.getOffhandItem().isEmpty()) {
                var pitch = Mth.lerp(partialTicks, player.xRotO, player.getXRot());
                renderBothHoldingPaper(handRenderer, player, swingProgress, equippedProgress, pitch, poseStack, buffer, light, overlay, drawing);
            } else {
                renderSingleHoldingPaper(handRenderer, player, HumanoidArm.RIGHT, swingProgress, equippedProgress, poseStack, buffer, light, overlay, drawing);
            }
        } else {
            var swingProgress = player.swingingArm == InteractionHand.OFF_HAND ? attackAnim : 0;
            var equippedProgress = 1.0f - Mth.lerp(partialTicks, handRenderer.oOffHandHeight, handRenderer.offHandHeight);
            renderSingleHoldingPaper(handRenderer, player, HumanoidArm.LEFT, swingProgress, equippedProgress, poseStack, buffer, light, overlay, drawing);
        }
    }

    private static void renderPaperInHand(PoseStack poseStack, MultiBufferSource buffer,
                                          int light, int overlay, Drawing drawing) {
        poseStack.mulPose(Axis.YP.rotationDegrees(180.0F));
        poseStack.mulPose(Axis.ZP.rotationDegrees(180.0F));
        poseStack.scale(0.38F, 0.38F, 0.38F);
        poseStack.translate(-0.5D, -0.5D, 0.0D);
        poseStack.scale(0.0078125F, 0.0078125F, 0.0078125F);
        poseStack.scale(drawing.getWidth(), drawing.getHeight(), 1);

        try (var ignored1 = GLSwitcher.blend().enable(); var ignored2 = GLSwitcher.depth().enable()) {
            blitHandheldBackground(poseStack, buffer, 0, 0, drawing.getWidth(), drawing.getHeight(), light, overlay);
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
        }
    }

    private static void renderSingleHoldingPaper(ItemInHandRenderer handRenderer, LocalPlayer player, HumanoidArm arm,
                                                 float swingProgress, float equippedProgress,
                                                 PoseStack poseStack, MultiBufferSource buffer,
                                                 int light, int overlay,
                                                 Drawing drawing) {
        var f = arm == HumanoidArm.RIGHT ? 1.0f : -1.0f;
        // Timicasto: This broke the ItemStack rendering in the offhand
//        poseStack.translate(f * 0.125f, -0.125f, 0.0f);
        if (!player.isInvisible()) {
            poseStack.pushPose();
            poseStack.mulPose(Axis.ZP.rotationDegrees(f * 10.0f));
            handRenderer.renderPlayerArm(poseStack, buffer, light, equippedProgress, swingProgress, arm);
            poseStack.popPose();
        }
        poseStack.pushPose();
        poseStack.translate(f * 0.51f, -0.08f + equippedProgress * -1.2f, -0.75f);
        float f1 = Mth.sqrt(swingProgress);
        float f2 = Mth.sin(f1 * (float)Math.PI);
        float f3 = -0.5f * f2;
        float f4 = 0.4f * Mth.sin(f1 * ((float)Math.PI * 2));
        float f5 = -0.3f * Mth.sin(swingProgress * (float)Math.PI);
        poseStack.translate(f * f3, f4 - 0.3f * f2, f5);
        poseStack.mulPose(Axis.XP.rotationDegrees(f2 * -45.0f));
        poseStack.mulPose(Axis.YP.rotationDegrees(f * f2 * -30.0f));
        renderPaperInHand(poseStack, buffer, light, overlay, drawing);
        poseStack.popPose();
    }

    private static void renderBothHoldingPaper(ItemInHandRenderer handRenderer, LocalPlayer player,
                                               float swingProgress, float equippedProgress, float pitch,
                                               PoseStack poseStack, MultiBufferSource buffer,
                                               int light, int overlay,
                                               Drawing drawing) {
        float f = Mth.sqrt(swingProgress);
        float f1 = -0.2f * Mth.sin(swingProgress * (float)Math.PI);
        float f2 = -0.4f * Mth.sin(f * (float)Math.PI);
        poseStack.translate(0.0f, -f1 / 2.0f, f2);
        float f3 = handRenderer.calculateMapTilt(pitch);
        poseStack.translate(0.0f, 0.04f + equippedProgress * -1.2f + f3 * -0.5f, -0.72f);
        poseStack.mulPose(Axis.XP.rotationDegrees(f3 * -85.0f));
        if (!player.isInvisible()) {
            poseStack.pushPose();
            poseStack.mulPose(Axis.YP.rotationDegrees(90.0f));
            handRenderer.renderMapHand(poseStack, buffer, light, HumanoidArm.RIGHT);
            handRenderer.renderMapHand(poseStack, buffer, light, HumanoidArm.LEFT);
            poseStack.popPose();
        }
        float f4 = Mth.sin(f * (float)Math.PI);
        poseStack.mulPose(Axis.XP.rotationDegrees(f4 * 20.0f));
        poseStack.scale(2.0f, 2.0f, 2.0f);
        renderPaperInHand(poseStack, buffer, light, overlay, drawing);
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
