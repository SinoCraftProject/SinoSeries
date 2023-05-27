package games.moegirl.sinocraft.sinodivination.client.blockentity;

import com.mojang.blaze3d.vertex.PoseStack;
import games.moegirl.sinocraft.sinodivination.blockentity.AltarEntity;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.world.phys.Vec3;

public record AltarRenderer(BlockEntityRendererProvider.Context context) implements BlockEntityRenderer<AltarEntity> {

    @Override
    public void render(AltarEntity blockEntity, float partialTick, PoseStack poseStack, MultiBufferSource source, int packedLight, int packedOverlay) {
        // todo 祭坛渲染
    }

    @Override
    public boolean shouldRender(AltarEntity pBlockEntity, Vec3 pCameraPos) {
        return BlockEntityRenderer.super.shouldRender(pBlockEntity, pCameraPos);
    }

    @Override
    public boolean shouldRenderOffScreen(AltarEntity pBlockEntity) {
        return BlockEntityRenderer.super.shouldRenderOffScreen(pBlockEntity);
    }

    @Override
    public int getViewDistance() {
        return BlockEntityRenderer.super.getViewDistance();
    }
}
