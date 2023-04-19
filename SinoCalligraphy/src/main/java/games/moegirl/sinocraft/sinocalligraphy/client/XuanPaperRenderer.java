package games.moegirl.sinocraft.sinocalligraphy.client;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import games.moegirl.sinocraft.sinocalligraphy.SCAConstants;
import games.moegirl.sinocraft.sinocalligraphy.drawing.DrawingDataVersion;
import games.moegirl.sinocraft.sinocalligraphy.drawing.simple.ISimpleDrawing;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderDispatcher;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;

import javax.annotation.Nullable;

/**
 * Renderer of Xuan Paper.
 * @author qyl27
 */
public class XuanPaperRenderer extends BlockEntityWithoutLevelRenderer {
    // BlockEntity Without Level Renderer is the new ISTER.
    // A mod should only one BEWLR.

    @Nullable
    private static XuanPaperRenderer INSTANCE;

    public static XuanPaperRenderer getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new XuanPaperRenderer(Minecraft.getInstance().getBlockEntityRenderDispatcher(), Minecraft.getInstance().getEntityModels());
        }
        return INSTANCE;
    }

    XuanPaperRenderer(BlockEntityRenderDispatcher pBlockEntityRenderDispatcher, EntityModelSet pEntityModelSet) {
        super(pBlockEntityRenderDispatcher, pEntityModelSet);
    }

    @Override
    public void renderByItem(ItemStack stack, ItemDisplayContext transformType, PoseStack poseStack, MultiBufferSource buffer, int packedLight, int packedOverlay) {
        var tag = stack.getOrCreateTag();
        CompoundTag nbt;
        if (tag.contains(SCAConstants.DRAWING_TAG_NAME)) {
            nbt = tag.getCompound(SCAConstants.DRAWING_TAG_NAME);
        } else {
            nbt = new CompoundTag();
        }

        ISimpleDrawing drawing = DrawingDataVersion.getLatest().fromTag(nbt);

        RenderSystem.disableDepthTest();
        RenderSystem.disableCull();
        poseStack.pushPose();
        if (transformType == ItemDisplayContext.FIXED) {
            poseStack.mulPose(Axis.YP.rotationDegrees(180));
            poseStack.scale(1, -1, 1);
            poseStack.translate(-1.5, -1.5, -0.5);
            poseStack.scale(0.0625f, 0.0625f, 0.0625f);
            poseStack.translate(0.0D, 0.0D, 0.01D);
        } else {
            poseStack.scale(0.03125f, 0.03125f, 1.0f);
            poseStack.scale(drawing.getSize(), drawing.getSize(), drawing.getSize());
        }
        drawing.getRenderer().draw(poseStack, buffer, packedLight);
        poseStack.popPose();
    }
}
