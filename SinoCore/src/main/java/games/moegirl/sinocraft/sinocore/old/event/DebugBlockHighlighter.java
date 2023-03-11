package games.moegirl.sinocraft.sinocore.old.event;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.Camera;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.shapes.VoxelShape;

public class DebugBlockHighlighter {

    // todo fix: DrawSelectionEvent not found
//    @SubscribeEvent
//    public void onDrawBlockHighlightEvent(DrawSelectionEvent.HighlightBlock event) {
//        HitResult rayTraceResult = event.getTarget();
//        if (rayTraceResult.getType() != HitResult.Type.BLOCK) {
//            return;
//        }
//        Level level = getWorld();
//
//        BlockPos blockpos = ((BlockHitResult) rayTraceResult).getBlockPos();
//        BlockState blockstate = level.getBlockState(blockpos);
//
//        if (blockstate.isAir() || !level.getWorldBorder().isWithinBounds(blockpos)) {
//            return;
//        }
//
//        var color = FastColor.ARGB32.color(127, 255, 0, 0);
//
//        Camera camera = event.getCamera();
//        CollisionContext iSelectionContext = CollisionContext.of(camera.getEntity());
//        MultiBufferSource renderTypeBuffers = event.getMultiBufferSource();
//        PoseStack stack = event.getPoseStack();
//
//        stack.pushPose();
//        VoxelShape shape = blockstate.getShape(level, blockpos, iSelectionContext);
//        drawSelectionBox(renderTypeBuffers, stack, blockpos, camera, shape, color);
//        stack.popPose();
//
//        event.setCanceled(true);
//    }

    private static Level getWorld() {
        return Minecraft.getInstance().player.getCommandSenderWorld();
    }

    private static boolean loggedReflectionError = false;

    private static void drawSelectionBox(MultiBufferSource renderTypeBuffers, PoseStack stack,
                                         BlockPos blockPos, Camera camera, VoxelShape shape, int color) {
        var renderType = RenderType.lines();
        var vertexBuilder = renderTypeBuffers.getBuffer(renderType);

        var eyeX = camera.getPosition().x();
        var eyeY = camera.getPosition().y();
        var eyeZ = camera.getPosition().z();
        drawShapeOutline(stack, vertexBuilder, shape,
                blockPos.getX() - eyeX, blockPos.getY() - eyeY, blockPos.getZ() - eyeZ,
                color);
    }

    private static void drawShapeOutline(PoseStack stack,
                                         VertexConsumer vertexConsumer,
                                         VoxelShape voxelShape,
                                         double originX, double originY, double originZ,
                                         int color) {
        // Fixme: qyl27: Not working for debug show highlighted block shape box.
//        var pose = stack.last().pose();
//        voxelShape.forAllBoxes((x0, y0, z0, x1, y1, z1) -> {
//            vertexConsumer.vertex(pose, (float) (x0 + originX), (float)(y0 + originY), (float)(z0 + originZ)).color(color).normal().endVertex();
//            vertexConsumer.vertex(pose, (float) (x1 + originX), (float)(y1 + originY), (float)(z1 + originZ)).color(color).normal().endVertex();
//        });
    }
}
