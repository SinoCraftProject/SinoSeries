package games.moegirl.sinocraft.sinodivination.old.client;

import com.mojang.blaze3d.vertex.PoseStack;
import games.moegirl.sinocraft.sinodivination.old.blockentity.AltarEntity;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.world.phys.Vec3;

public class AltarRenderer implements BlockEntityRenderer<AltarEntity> {

    public final BlockEntityRendererProvider.Context context;

    public AltarRenderer(BlockEntityRendererProvider.Context context) {
        this.context = context;
    }

    @Override
    public void render(AltarEntity pBlockEntity, float pPartialTick, PoseStack pPoseStack, MultiBufferSource pBufferSource, int pPackedLight, int pPackedOverlay) {

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
