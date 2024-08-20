package games.moegirl.sinocraft.sinocore.fabric.mixin.event.client.render;

import com.mojang.blaze3d.vertex.PoseStack;
import games.moegirl.sinocraft.sinocore.event.client.RenderEvents;
import games.moegirl.sinocraft.sinocore.event.client.args.render.RenderItemInFrameArgs;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.ItemFrameRenderer;
import net.minecraft.world.entity.decoration.ItemFrame;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ItemFrameRenderer.class)
public abstract class ItemFrameRendererMixin {
    @Inject(method = "render(Lnet/minecraft/world/entity/decoration/ItemFrame;FFLcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;I)V", at = @At(value = "INVOKE", target = "Lcom/mojang/blaze3d/vertex/PoseStack;mulPose(Lorg/joml/Quaternionf;)V", ordinal = 2), cancellable = true)
    private void afterMulPose(ItemFrame entity, float entityYaw, float partialTicks,
                              PoseStack poseStack, MultiBufferSource buffer, int packedLight, CallbackInfo ci) {
        var item = entity.getItem();
        var result = RenderEvents.RENDER_ITEM_IN_FRAME.invoke(new RenderItemInFrameArgs(item, entity, (ItemFrameRenderer<?>) (Object) this, poseStack, buffer, packedLight));
        if (result.isCancelled()) {
            poseStack.popPose();
            ci.cancel();
        }
    }
}
