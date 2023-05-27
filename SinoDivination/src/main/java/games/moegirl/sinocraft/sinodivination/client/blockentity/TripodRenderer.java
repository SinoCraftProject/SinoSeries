package games.moegirl.sinocraft.sinodivination.client.blockentity;

import com.mojang.blaze3d.vertex.PoseStack;
import games.moegirl.sinocraft.sinocore.client.TextureMapClient;
import games.moegirl.sinocraft.sinocore.utility.texture.Rect;
import games.moegirl.sinocraft.sinocore.utility.texture.TextureMap;
import games.moegirl.sinocraft.sinodivination.SinoDivination;
import games.moegirl.sinocraft.sinodivination.blockentity.TripodEntity;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.core.Direction;
import net.minecraft.core.Vec3i;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

public record TripodRenderer(BlockEntityRendererProvider.Context context) implements BlockEntityRenderer<TripodEntity> {

    private static final TextureMap TEXTURE = TextureMap.of(new ResourceLocation(SinoDivination.MODID, "textures/in_world.png"));
    private static final TextureMapClient CLIENT = new TextureMapClient(TEXTURE);

    @Override
    public void render(TripodEntity tripod, float partialTick, PoseStack poseStack, MultiBufferSource bufferSource, int packedLight, int packedOverlay) {
        Level level = tripod.getLevel();
        if (level == null) return;
        // todo render structure
//        boolean e1 = renderErrorStructurePart(level, Direction.UP, tripod, poseStack, bufferSource, packedLight);
//        boolean e2 = renderErrorStructurePart(level, Direction.EAST, tripod, poseStack, bufferSource, packedLight);
//        boolean e3 = renderErrorStructurePart(level, Direction.WEST, tripod, poseStack, bufferSource, packedLight);
//        boolean e4 = renderErrorStructurePart(level, Direction.NORTH, tripod, poseStack, bufferSource, packedLight);
//        boolean e5 = renderErrorStructurePart(level, Direction.SOUTH, tripod, poseStack, bufferSource, packedLight);
//        if (e1 && e2 && e3 && e4 && e5) {
//            renderFullyStructure(tripod, partialTick, poseStack, bufferSource, packedLight, packedOverlay);
//        }
    }

    private boolean renderErrorStructurePart(Level level, Direction direction, TripodEntity tripod, PoseStack poseStack, MultiBufferSource bufferSource, int packedLight) {
//        AltarStructure structure = tripod.getStructure();
//        AltarStructure.Status type = structure.partStatus(level, direction);
//        if (type == AltarStructure.Status.OK) {
//            return true;
//        }
//        Vec3i normal = direction.getNormal();
//        float x = (normal.getX() + 1) * 0.333f;
//        float z = (normal.getZ() + 1) * 0.333f;
//        CLIENT.blitTexture(bufferSource, poseStack, type.texture, true, Rect.xz(x, 1.01f, z, 0.333f, 0.333f), packedLight);
        return false;
    }

    private void renderFullyStructure(TripodEntity tripod, float partialTick, PoseStack stack, MultiBufferSource source, int packedLight, int packedOverlay) {
        // todo render
    }

    @Override
    public boolean shouldRender(TripodEntity blockEntity, Vec3 cameraPos) {
        return BlockEntityRenderer.super.shouldRender(blockEntity, cameraPos);
    }

    @Override
    public boolean shouldRenderOffScreen(TripodEntity blockEntity) {
        return BlockEntityRenderer.super.shouldRenderOffScreen(blockEntity);
    }

    @Override
    public int getViewDistance() {
        return BlockEntityRenderer.super.getViewDistance();
    }
}
