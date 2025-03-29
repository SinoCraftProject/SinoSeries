package games.moegirl.sinocraft.sinobrush.client;

import com.mojang.blaze3d.vertex.PoseStack;
import games.moegirl.sinocraft.sinobrush.SinoBrush;
import games.moegirl.sinocraft.sinobrush.item.FanItem;
import games.moegirl.sinocraft.sinobrush.item.SBRItems;
import games.moegirl.sinocraft.sinocore.gui.widgets.WidgetLoader;
import games.moegirl.sinocraft.sinocore.gui.widgets.Widgets;
import games.moegirl.sinocraft.sinocore.gui.widgets.entry.TextureEntry;
import games.moegirl.sinocraft.sinocore.utility.config.Configs;
import games.moegirl.sinocraft.sinocore.utility.config.IConfigVisitor;
import net.minecraft.Util;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.block.model.ItemTransform;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import org.apache.logging.log4j.util.Lazy;
import org.joml.Matrix4f;

import java.io.IOException;
import java.util.EnumSet;
import java.util.List;

public class FanRenderer extends BlockEntityWithoutLevelRenderer {

    public static final int MAX_DISPLAY_LINES = 21;
    public static final ModelResourceLocation MODEL_FAN = ModelResourceLocation.inventory(SBRItems.FAN.getId());
    public static BakedModel DEFAULT_FAN_MODEL;

    private static final EnumSet<ItemDisplayContext> TEXT_TO_RENDER = EnumSet.of(
            ItemDisplayContext.FIRST_PERSON_LEFT_HAND,
            ItemDisplayContext.FIRST_PERSON_RIGHT_HAND,
            ItemDisplayContext.THIRD_PERSON_RIGHT_HAND,
            ItemDisplayContext.THIRD_PERSON_LEFT_HAND);

    public static final Lazy<Widgets> TEXTURE = Lazy.lazy(() -> WidgetLoader.loadWidgets(ResourceLocation.fromNamespaceAndPath(SinoBrush.MODID, "textures/gui/fan")));
    public static final double[] LINE_X_IN_HAND = {0.440, 0.400, 0.350, 0.310, 0.270, 0.220, 0.170, 0.110, 0.050, -0.010, -0.080, -0.150, -0.220, -0.280, -0.330, -0.380, -0.420, -0.450, -0.470, -0.510, -0.540};
    public static final double[] LINE_Y_IN_HAND = {0.060, 0.080, 0.090, 0.110, 0.140, 0.160, 0.180, 0.200, 0.220, 0.220, 0.210, 0.200, 0.170, 0.150, 0.130, 0.120, 0.080, 0.060, 0.030, 0.010, -0.030};
    public static final double[] LINE_R_IN_HAND = {45, 41, 38, 36, 33, 30, 28, 22, 17, 14, 7, 1, -7, -13, -17, -21, -25, -26, -30, -34, -42};
    private static final float[] LINE_X_IN_GUI = {231, 223, 213, 203, 191, 181, 165, 152, 138, 117, 102, 89, 77, 67, 56, 46, 37, 28, 20, 14, 9,};
    private static final float[] LINE_Y_IN_GUI = {64, 54, 43, 35, 27, 22, 17, 13, 10, 11, 13, 18, 22, 28, 34, 43, 52, 62, 72, 83, 95,};
    private static final float[] LINE_R_IN_GUI = {45, 41, 35, 30, 25, 22, 14, 9, 4, -6, -13, -19, -23, -26, -30, -35, -39, -45, -50, -55, -58,};

    public FanRenderer() {
        super(Minecraft.getInstance().getBlockEntityRenderDispatcher(), Minecraft.getInstance().getEntityModels());
    }

    @Override
    public void renderByItem(ItemStack stack, ItemDisplayContext displayContext, PoseStack poseStack, MultiBufferSource buffer, int packedLight, int packedOverlay) {
        if (stack.isEmpty() || DEFAULT_FAN_MODEL == null) return;

        poseStack.popPose();

        // 绘制模型
        boolean leftHand = displayContext == ItemDisplayContext.FIRST_PERSON_LEFT_HAND
                || displayContext == ItemDisplayContext.THIRD_PERSON_LEFT_HAND;
        ItemRenderer renderer = Minecraft.getInstance().getItemRenderer();
        renderer.render(stack, displayContext, leftHand, poseStack, buffer, packedLight, packedOverlay, DEFAULT_FAN_MODEL);

        // 绘制文字
        List<Component> lines = FanItem.getLines(stack);
        if (TEXT_TO_RENDER.contains(displayContext) && !lines.isEmpty()) {
            Font font = Minecraft.getInstance().font;
            ItemTransform transform = DEFAULT_FAN_MODEL.getTransforms().getTransform(displayContext);
            transform.apply(leftHand, poseStack);
            poseStack.mulPose(new Matrix4f().rotate((float) Math.toRadians(-45), 0, 0, 1));
            for (int i = 0; i < Math.min(lines.size(), MAX_DISPLAY_LINES); i++) {
                float r = (float) Math.toRadians(LINE_R_IN_HAND[i]);
                poseStack.pushPose();
                poseStack.translate(LINE_X_IN_HAND[i], LINE_Y_IN_HAND[i], 0.05);
                poseStack.scale(0.003f, -0.003f, 1);
                poseStack.mulPose(new Matrix4f().rotate(r, 0, 0, 1));
                drawCharactersInHand(lines.get(i), font, poseStack, buffer, packedLight);
                poseStack.popPose();
            }
        }

        poseStack.pushPose();
    }

    private void drawCharactersInHand(Component text, Font font, PoseStack poseStack, MultiBufferSource buffer, int packedLight) {
        String line = text.getString();
        int y = 0;
        for (char c : line.toCharArray()) {
            String s = String.valueOf(c);
            int w = font.width(s);
            int h = font.wordWrapHeight(s, w);
            font.drawInBatch(s, 0, y, 0x414141, false, poseStack.last().pose(), buffer,
                    Font.DisplayMode.NORMAL, 0, packedLight, font.isBidirectional());
            y += h;
        }
    }

    public static void renderInGui(GuiGraphics guiGraphics, Font font, int x, int y,
                                   List<Component> lines, int currentLine, long focusedTime) {
        TextureEntry background = (TextureEntry) TEXTURE.get().getWidget("background");
        guiGraphics.blit(TEXTURE.get().getTexture(), x, y,
                background.getWidth(), background.getHeight(),
                background.getTextureX(), background.getTextureY(),
                background.getTextureWidth(), background.getTextureHeight(),
                TEXTURE.get().getWidth(), TEXTURE.get().getHeight());
        PoseStack pose = guiGraphics.pose();
        for (int i = 0; i < MAX_DISPLAY_LINES; i++) {
            if (i >= lines.size() && currentLine != i)
                continue;

            float offX = x + LINE_X_IN_GUI[i];
            float offY = y + LINE_Y_IN_GUI[i];
            float r = (float) Math.toRadians(LINE_R_IN_GUI[i]);
            pose.pushPose();
            pose.translate(offX, offY, 0);
            pose.scale(0.6f, 0.6f, 1);
            pose.mulPose(new Matrix4f().rotate(r, 0, 0, 1));
            int ny = 0;
            // 文本内容
            if (i < lines.size()) {
                ny = drawCharactersInGui(guiGraphics, font, lines.get(i).getString());
            }
            // 编辑标记
            if (currentLine == i && (Util.getMillis() - focusedTime) / 300L % 2L == 0L) {
                guiGraphics.drawString(font, "_", 0, ny, 0x414141);
            }
            pose.popPose();
        }
    }

    private static int drawCharactersInGui(GuiGraphics guiGraphics, Font font, String line) {
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

    public static void renderInHud(GuiGraphics guiGraphics) {
        LocalPlayer player = Minecraft.getInstance().player;
        if (player != null) {
            ItemStack stack = player.getItemInHand(InteractionHand.MAIN_HAND);
            if (stack.is(SBRItems.FAN.get())) {
                renderInHud(guiGraphics, stack);
                return;
            }

            stack = player.getItemInHand(InteractionHand.OFF_HAND);
            if (stack.is(SBRItems.FAN.get())) {
                renderInHud(guiGraphics, stack);
                return;
            }

            Inventory inventory = player.getInventory();
            for (int i = 0; i < 9; i++) {
                stack = inventory.getItem(i);
                if (stack.is(SBRItems.FAN.get())) {
                    renderInHud(guiGraphics, stack);
                    return;
                }
            }
        }
    }

    private static void renderInHud(GuiGraphics guiGraphics, ItemStack stack) {
        int x = 0, y = 0;
        float scale = 0.5f;
        try {
            Configs clientConfigs = SinoBrush.CONFIGURATIONS.getClientConfigs();
            IConfigVisitor fanHud = clientConfigs.getObject("FanHUD");
            x = fanHud.getInteger("x", 0);
            y = fanHud.getInteger("y", 0);
            scale = fanHud.getFloat("scale", 0.5f);
        } catch (IOException e) {
            e.printStackTrace(System.err);
        }

        List<Component> lines = FanItem.getLines(stack);
        PoseStack pose = guiGraphics.pose();
        pose.pushPose();
        pose.translate(x, y, 0);
        pose.scale(scale, scale, 1);
        renderInGui(guiGraphics, Minecraft.getInstance().font, 0, 0, lines, -1, 0);
        pose.popPose();
    }
}
