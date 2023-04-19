package games.moegirl.sinocraft.sinocalligraphy.client;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import games.moegirl.sinocraft.sinocalligraphy.SCAConstants;
import games.moegirl.sinocraft.sinocalligraphy.data.SCAItemTags;
import games.moegirl.sinocraft.sinocalligraphy.drawing.data.DrawingDataVersion;
import games.moegirl.sinocraft.sinocalligraphy.drawing.simple.ISimpleDrawing;
import games.moegirl.sinocraft.sinocalligraphy.item.SCAItems;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.Sheets;
import net.minecraft.client.renderer.entity.player.PlayerRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.decoration.ItemFrame;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RenderHandEvent;
import net.minecraftforge.client.event.RenderItemInFrameEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

/**
 * Client render event subscriber.
 */
@Mod.EventBusSubscriber(Dist.CLIENT)
public class XuanPaperRenderEvent {
    @SubscribeEvent
    public static void onRenderInFrame(RenderItemInFrameEvent event) {
        // Re-render the frame, in order to prevent asm to net.minecraft.client.renderer.entity.ItemFrameRenderer.render.

        var item = event.getItemStack();
        var frame = event.getItemFrameEntity();

        if (item.is(SCAItemTags.FILLED_PAPERS)) {
            var invisible = frame.isInvisible();
            var stack = event.getPoseStack();
            var buffers = event.getMultiBufferSource();
            var light = event.getPackedLight();
            var mc = Minecraft.getInstance();

            if (!invisible) {
                stack.mulPose(Axis.ZP.rotationDegrees(-45 * frame.getRotation()));
                stack.translate(0, 0, -0.4375);
                var dispatcher = mc.getBlockRenderer();
                var model = dispatcher.getBlockModelShaper().getModelManager().getModel(getBigFrameModel(frame));
                var consumer = buffers.getBuffer(Sheets.solidBlockSheet());
                stack.pushPose();
                stack.translate(-0.5D, -0.5D, -0.5D);
                dispatcher.getModelRenderer().renderModel(stack.last(), consumer, null, model,
                        1.0F, 1.0F, 1.0F, getLightVal(frame, light), OverlayTexture.NO_OVERLAY);
                stack.popPose();
                stack.translate(0.0D, 0.0D, 0.4375D);
            }
            stack.mulPose(Axis.ZP.rotationDegrees(frame.getRotation() % 4 * 90));
        }
    }

    private static ModelResourceLocation getBigFrameModel(ItemFrame frame) {
        if (frame.getType() == EntityType.GLOW_ITEM_FRAME) {
            return new ModelResourceLocation(new ResourceLocation("minecraft", "glow_item_frame"), "map=true");
        } else {
            return new ModelResourceLocation(new ResourceLocation("minecraft", "item_frame"), "map=true");
        }
    }

    private static int getLightVal(ItemFrame frame, int light) {
        return frame.getType() == EntityType.GLOW_ITEM_FRAME ? 15728880 : light;
    }

    /**
     * @see net.minecraft.client.renderer.ItemInHandRenderer#renderHandsWithItems(float, PoseStack, MultiBufferSource.BufferSource, LocalPlayer, int)
     */
    @SubscribeEvent
    public static void onRenderInHand(RenderHandEvent event) {
        Minecraft mc = Minecraft.getInstance();
        LocalPlayer player = mc.player;
        ItemStack stack = event.getItemStack();

        var tag = stack.getOrCreateTag();
        CompoundTag nbt = null;
        if (tag.contains(SCAConstants.DRAWING_TAG_NAME)) {
            nbt = tag.getCompound(SCAConstants.DRAWING_TAG_NAME);
        } else {
            nbt = new CompoundTag();
        }

        var light = event.getPackedLight();

        if (stack.is(SCAItems.FILLED_XUAN_PAPER.get()) && player != null && !player.isScoping()) {

            ISimpleDrawing drawing = DrawingDataVersion.getLatest().fromTag(nbt);

            InteractionHand hand = event.getHand();
            switch (hand) {
                case MAIN_HAND -> {
                    if (player.getOffhandItem().isEmpty()) {
                        renderTwoHandedPaper(player, event, drawing, light);
                    } else {
                        renderOneHandedPaper(player, event, player.getMainArm(), drawing, light);
                    }
                }
                case OFF_HAND -> renderOneHandedPaper(player, event, player.getMainArm().getOpposite(), drawing, light);
            }
            event.setCanceled(true);
        }
    }

    private static void renderXuanPaperInHand(PoseStack stack, MultiBufferSource buffer, ISimpleDrawing draw, int light) {
        stack.mulPose(Axis.YP.rotationDegrees(180.0F));
        stack.mulPose(Axis.ZP.rotationDegrees(180.0F));
        stack.scale(0.38F, 0.38F, 0.38F);
        stack.translate(-0.5D, -0.5D, 0.0D);
        stack.scale(0.0078125F, 0.0078125F, 0.0078125F);
        float step = 128f / draw.getSize();
        stack.scale(step, step, step);
        draw.getRenderer().draw(stack, buffer, light);
    }

    private static void renderOneHandedPaper(LocalPlayer player, RenderHandEvent event, HumanoidArm arm,
                                             ISimpleDrawing draw, int light) {
        PoseStack stack = event.getPoseStack();
        MultiBufferSource buffer = event.getMultiBufferSource();
        float equippedProgress = event.getEquipProgress();
        float swingProgress = event.getSwingProgress();

        float f = arm == HumanoidArm.RIGHT ? 1.0F : -1.0F;
        // Timicasto: This broke the itemstack rendering in the offhand
        // stack.translate(f * 0.125F, -0.125D, 0.0D);
        if (!player.isInvisible()) {
            stack.pushPose();
            stack.mulPose(Axis.ZP.rotationDegrees(f * 10.0F));
            renderPlayerArm(player, stack, buffer, light, equippedProgress, swingProgress, arm);
            stack.popPose();
        }

        stack.pushPose();
        stack.translate(f * 0.51F, -0.08F + equippedProgress * -1.2F, -0.75D);
        float f1 = Mth.sqrt(swingProgress);
        float f2 = Mth.sin(f1 * (float) Math.PI);
        float f3 = -0.5F * f2;
        float f4 = 0.4F * Mth.sin(f1 * ((float) Math.PI * 2F));
        float f5 = -0.3F * Mth.sin(swingProgress * (float) Math.PI);
        stack.translate(f * f3, f4 - 0.3F * f2, f5);
        stack.mulPose(Axis.XP.rotationDegrees(f2 * -45.0F));
        stack.mulPose(Axis.YP.rotationDegrees(f * f2 * -30.0F));
        renderXuanPaperInHand(stack, buffer, draw, light);
        stack.popPose();
    }

    private static void renderTwoHandedPaper(LocalPlayer player, RenderHandEvent event, ISimpleDrawing draw, int light) {
        PoseStack stack = event.getPoseStack();
        MultiBufferSource buffer = event.getMultiBufferSource();
        float equippedProgress = event.getEquipProgress();
        float swingProgress = event.getSwingProgress();

        stack.pushPose();
        float f = Mth.sqrt(swingProgress);
        float f1 = -0.2F * Mth.sin(swingProgress * (float) Math.PI);
        float f2 = -0.4F * Mth.sin(f * (float) Math.PI);
        stack.translate(0.0D, -f1 / 2.0F, f2);
        float f3 = calculateMapTilt(event.getInterpolatedPitch());
        stack.translate(0.0D, 0.04F + equippedProgress * -1.2F + f3 * -0.5F, -0.72F);
        stack.mulPose(Axis.XP.rotationDegrees(f3 * -85.0F));
        if (!player.isInvisible()) {
            stack.pushPose();
            stack.mulPose(Axis.YP.rotationDegrees(90.0F));
            renderMapHand(player, stack, buffer, light, HumanoidArm.RIGHT);
            renderMapHand(player, stack, buffer, light, HumanoidArm.LEFT);
            stack.popPose();
        }

        float f4 = Mth.sin(f * (float) Math.PI);
        stack.mulPose(Axis.XP.rotationDegrees(f4 * 20.0F));
        stack.scale(2.0F, 2.0F, 2.0F);
        renderXuanPaperInHand(stack, buffer, draw, light);
        stack.popPose();
    }

    private static void renderPlayerArm(LocalPlayer player, PoseStack stack, MultiBufferSource pBuffer, int pCombinedLight,
                                        float pEquippedProgress, float pSwingProgress, HumanoidArm pSide) {
        boolean flag = pSide != HumanoidArm.LEFT;
        float f = flag ? 1.0F : -1.0F;
        float f1 = Mth.sqrt(pSwingProgress);
        float f2 = -0.3F * Mth.sin(f1 * (float) Math.PI);
        float f3 = 0.4F * Mth.sin(f1 * ((float) Math.PI * 2F));
        float f4 = -0.4F * Mth.sin(pSwingProgress * (float) Math.PI);
        stack.translate(f * (f2 + 0.64), f3 - 0.6 + pEquippedProgress * -0.6F, f4 - 0.72);
        stack.mulPose(Axis.YP.rotationDegrees(f * 45.0F));
        float f5 = Mth.sin(pSwingProgress * pSwingProgress * (float) Math.PI);
        float f6 = Mth.sin(f1 * (float) Math.PI);
        stack.mulPose(Axis.YP.rotationDegrees(f * f6 * 70.0F));
        stack.mulPose(Axis.ZP.rotationDegrees(f * f5 * -20.0F));
        RenderSystem.setShaderTexture(0, player.getSkinTextureLocation());
        stack.translate(f * -1.0F, 3.6F, 3.5D);
        stack.mulPose(Axis.ZP.rotationDegrees(f * 120.0F));
        stack.mulPose(Axis.XP.rotationDegrees(200.0F));
        stack.mulPose(Axis.YP.rotationDegrees(f * -135.0F));
        stack.translate(f * 5.6F, 0.0D, 0.0D);
        PlayerRenderer renderer = (PlayerRenderer) Minecraft.getInstance().getEntityRenderDispatcher().getRenderer(player);
        if (flag) {
            renderer.renderRightHand(stack, pBuffer, pCombinedLight, player);
        } else {
            renderer.renderLeftHand(stack, pBuffer, pCombinedLight, player);
        }
    }

    private static float calculateMapTilt(float pitch) {
        float f = 1.0F - pitch / 45.0F + 0.1F;
        f = Mth.clamp(f, 0.0F, 1.0F);
        return -Mth.cos(f * (float) Math.PI) * 0.5F + 0.5F;
    }

    private static void renderMapHand(LocalPlayer player, PoseStack stack, MultiBufferSource buffer, int light, HumanoidArm arm) {
        RenderSystem.setShaderTexture(0, player.getSkinTextureLocation());
        PlayerRenderer renderer = (PlayerRenderer) Minecraft.getInstance().getEntityRenderDispatcher().getRenderer(player);
        stack.pushPose();
        float f = arm == HumanoidArm.RIGHT ? 1.0F : -1.0F;
        stack.mulPose(Axis.YP.rotationDegrees(92.0F));
        stack.mulPose(Axis.XP.rotationDegrees(45.0F));
        stack.mulPose(Axis.ZP.rotationDegrees(f * -41.0F));
        stack.translate(f * 0.3, -1.1, 0.45);
        if (arm == HumanoidArm.RIGHT) {
            renderer.renderRightHand(stack, buffer, light, player);
        } else {
            renderer.renderLeftHand(stack, buffer, light, player);
        }

        stack.popPose();
    }
}
