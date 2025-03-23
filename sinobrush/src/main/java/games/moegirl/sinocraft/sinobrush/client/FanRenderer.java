package games.moegirl.sinocraft.sinobrush.client;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import games.moegirl.sinocraft.sinobrush.item.FanItem;
import games.moegirl.sinocraft.sinobrush.item.SBRItems;
import games.moegirl.sinocraft.sinocore.gui.widgets.WidgetLoader;
import games.moegirl.sinocraft.sinocore.gui.widgets.Widgets;
import games.moegirl.sinocraft.sinocore.gui.widgets.entry.TextureEntry;
import net.minecraft.Util;
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.decoration.ItemFrame;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.item.ItemStack;
import org.joml.Matrix4f;

import java.util.List;

public class FanRenderer {
    public static final int MAX_DISPLAY_LINES = 21;

    public static final Widgets TEXTURE = WidgetLoader.loadWidgets(ResourceLocation.parse("sinobrush:textures/gui/fan"));
    public static final float[] LINE_X = {231, 223, 213, 203, 191, 181, 165, 152, 138, 117, 102, 89, 77, 67, 56, 46, 37, 28, 20, 14, 9,};
    public static final float[] LINE_Y = {64, 54, 43, 35, 27, 22, 17, 13, 10, 11, 13, 18, 22, 28, 34, 43, 52, 62, 72, 83, 95,};
    public static final float[] LINE_R = {45, 41, 35, 30, 25, 22, 14, 9, 4, -6, -13, -19, -23, -26, -30, -35, -39, -45, -50, -55, -58,};

    public static void renderInGui(GuiGraphics guiGraphics, Font font, int x, int y,
                                   List<Component> lines, int currentLine, long focusedTime) {
//        guiGraphics.fill(x, y, x + width, y + height, ColorHelper.rgbToARGB(drawing.getPaperColor()));
//
//        var pW = width / Math.max(1, drawing.getWidth());
//        var pH = height / Math.max(1, drawing.getHeight());
//        if (!drawing.isEmpty()) {
//            try (var ignored = GLSwitcher.blend().enable()) {
//                for (var i = 0; i < drawing.getWidth(); i++) {
//                    for (var j = 0; j < drawing.getHeight(); j++) {
//                        var pX = x + (i * pW);
//                        var pY = y + (j * pH);
//                        var color = drawing.getPixel(i, j);
//                        fillGuiRect(guiGraphics, pX, pY, pX + pW, pY + pH, ColorHelper.pixelColorToARGB(color, drawing.getInkColor()));
//                    }
//                }
//            }
//        }
        TextureEntry background = (TextureEntry) TEXTURE.getWidget("background");
        guiGraphics.blit(TEXTURE.getTexture(), x, y,
                background.getWidth(), background.getHeight(),
                background.getTextureX(), background.getTextureY(),
                background.getTextureWidth(), background.getTextureHeight(),
                TEXTURE.getWidth(), TEXTURE.getHeight());
        PoseStack pose = guiGraphics.pose();
        for (int i = 0; i < MAX_DISPLAY_LINES; i++) {
            if (i >= lines.size() && currentLine != i)
                continue;

            float offX = x + LINE_X[i];
            float offY = y + LINE_Y[i];
            float r = (float) Math.toRadians(LINE_R[i]);
            pose.pushPose();
            pose.translate(offX, offY, 0);
            pose.scale(0.6f, 0.6f, 1);
            pose.mulPose(new Matrix4f().rotate(r, 0, 0, 1));
            int ny = 0;
            // 文本内容
            if (i < lines.size()) {
                ny = drawCharacters(guiGraphics, font, lines.get(i).getString());
            }
            // 编辑标记
            if (currentLine == i && (Util.getMillis() - focusedTime) / 300L % 2L == 0L) {
                guiGraphics.drawString(font, "_", 0, ny, 0x414141);
            }
            pose.translate(-offX, -offY, 0);
            pose.popPose();
        }
    }

    private static int drawCharacters(GuiGraphics guiGraphics, Font font, String line) {
        int y = 0;
        for (char c : line.toCharArray()) {
            String s = String.valueOf(c);
            int w = font.width(s);
            int h = font.wordWrapHeight(s, w);
            guiGraphics.drawString(font, s, 0, y, 0x414141, false);
            y += h;
        }
        return y;
    }

    public static void renderInHud(GuiGraphics guiGraphics, DeltaTracker partialTick) {
        LocalPlayer player = Minecraft.getInstance().player;
        if (player != null) {
            ItemStack stack = player.getItemInHand(InteractionHand.MAIN_HAND);
            if (stack.is(SBRItems.FAN.get())) {
                renderInHud(guiGraphics, partialTick, stack);
                return;
            }

            stack = player.getItemInHand(InteractionHand.OFF_HAND);
            if (stack.is(SBRItems.FAN.get())) {
                renderInHud(guiGraphics, partialTick, stack);
                return;
            }

            Inventory inventory = player.getInventory();
            for (int i = 0; i < 9; i++) {
                stack = inventory.getItem(i);
                if (stack.is(SBRItems.FAN.get())) {
                    renderInHud(guiGraphics, partialTick, stack);
                    return;
                }
            }
        }
    }

    private static void renderInHud(GuiGraphics guiGraphics, DeltaTracker partialTick, ItemStack stack) {
        List<Component> lines = FanItem.getLines(stack);
        PoseStack pose = guiGraphics.pose();
        pose.pushPose();
        pose.scale(0.5f, 0.5f, 1);
        renderInGui(guiGraphics, Minecraft.getInstance().font, 0, 0, lines, -1, 0);
        pose.popPose();
    }

    private static int getLightVal(ItemFrame itemFrame, int glowLightVal, int regularLightVal) {
        return itemFrame.getType() == EntityType.GLOW_ITEM_FRAME ? glowLightVal : regularLightVal;
    }

    private static void fillGuiRect(GuiGraphics guiGraphics, float minX, float minY, float maxX, float maxY, int color) {
        var matrix4f = guiGraphics.pose().last().pose();
        var buffer = guiGraphics.bufferSource().getBuffer(RenderType.guiOverlay());
        buffer.addVertex(matrix4f, minX, minY, 0).setColor(color);
        buffer.addVertex(matrix4f, minX, maxY, 0).setColor(color);
        buffer.addVertex(matrix4f, maxX, maxY, 0).setColor(color);
        buffer.addVertex(matrix4f, maxX, minY, 0).setColor(color);
        guiGraphics.flush();
    }

    private static void fillRect(PoseStack poseStack, VertexConsumer buffer,
                                 float minX, float minY, float maxX, float maxY,
                                 float zIndex,
                                 int color, int combinedLight) {
        buffer.addVertex(poseStack.last().pose(), minX, minY, zIndex).setColor(color).setLight(combinedLight);
        buffer.addVertex(poseStack.last().pose(), minX, maxY, zIndex).setColor(color).setLight(combinedLight);
        buffer.addVertex(poseStack.last().pose(), maxX, maxY, zIndex).setColor(color).setLight(combinedLight);
        buffer.addVertex(poseStack.last().pose(), maxX, minY, zIndex).setColor(color).setLight(combinedLight);
    }
}
