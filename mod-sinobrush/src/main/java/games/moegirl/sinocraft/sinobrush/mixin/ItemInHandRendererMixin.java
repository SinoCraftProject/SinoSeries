package games.moegirl.sinocraft.sinobrush.mixin;

import com.mojang.blaze3d.vertex.PoseStack;
import games.moegirl.sinocraft.sinobrush.item.SBRItems;
import games.moegirl.sinocraft.sinobrush.client.DrawingRenderer;
import games.moegirl.sinocraft.sinobrush.item.component.Drawing;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.renderer.ItemInHandRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ItemInHandRenderer.class)
public abstract class ItemInHandRendererMixin {
    @Shadow private ItemStack offHandItem;

    @Shadow protected abstract void renderTwoHandedMap(PoseStack poseStack, MultiBufferSource buffer, int combinedLight, float pitch, float equippedProgress, float swingProgress);

    @Shadow protected abstract void renderOneHandedMap(PoseStack poseStack, MultiBufferSource buffer, int combinedLight, float equippedProgress, HumanoidArm hand, float swingProgress, ItemStack stack);

    @Inject(method = "renderArmWithItem", at = @At(value = "HEAD"), cancellable = true)
    private void beforeRenderArmWithItem(AbstractClientPlayer player, float partialTicks, float pitch,
                                   InteractionHand hand, float swingProgress, ItemStack stack,
                                   float equippedProgress, PoseStack poseStack, MultiBufferSource buffer,
                                   int combinedLight, CallbackInfo ci) {
        if (stack.is(SBRItems.FILLED_XUAN_PAPER.get())) {
            if (!player.isScoping()) {
                var flag = hand == InteractionHand.MAIN_HAND;
                var humanoidarm = flag ? player.getMainArm() : player.getMainArm().getOpposite();
                poseStack.pushPose();
                if (flag && offHandItem.isEmpty()) {
                    renderTwoHandedMap(poseStack, buffer, combinedLight, pitch, equippedProgress, swingProgress);
                } else {
                    renderOneHandedMap(poseStack, buffer, combinedLight, equippedProgress, humanoidarm, swingProgress, stack);
                }
                poseStack.popPose();
                ci.cancel();
            }
        }
    }

    @Inject(method = "renderMap", at = @At("HEAD"), cancellable = true)
    private void beforeRenderMap(PoseStack poseStack, MultiBufferSource buffer, int combinedLight,
                                 ItemStack stack, CallbackInfo ci) {
        if (stack.is(SBRItems.FILLED_XUAN_PAPER.get())) {
            var drawing = Drawing.get(stack);
            DrawingRenderer.renderInHand(poseStack, buffer, combinedLight, drawing);
            ci.cancel();
        }
    }
}
