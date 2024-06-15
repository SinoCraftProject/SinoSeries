package games.moegirl.sinocraft.sinobrush.cllient;

import com.mojang.blaze3d.vertex.PoseStack;
import games.moegirl.sinocraft.sinobrush.item.FilledXuanPaperItem;
import games.moegirl.sinocraft.sinobrush.utility.DrawingRenderer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderDispatcher;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.Nullable;

public class FilledXuanPaperRenderer extends BlockEntityWithoutLevelRenderer {

    @Nullable
    private static FilledXuanPaperRenderer INSTANCE;

    public static FilledXuanPaperRenderer getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new FilledXuanPaperRenderer(Minecraft.getInstance().getBlockEntityRenderDispatcher(), Minecraft.getInstance().getEntityModels());
        }
        return INSTANCE;
    }

    public FilledXuanPaperRenderer(BlockEntityRenderDispatcher blockEntityRenderDispatcher, EntityModelSet entityModelSet) {
        super(blockEntityRenderDispatcher, entityModelSet);
    }

    @Override
    public void renderByItem(ItemStack stack, ItemDisplayContext displayContext, PoseStack poseStack,
                             MultiBufferSource buffer, int packedLight, int packedOverlay) {
        var drawing = FilledXuanPaperItem.getDrawing(stack);
        DrawingRenderer.renderInHand(poseStack, buffer, displayContext, packedLight, packedOverlay, drawing);
    }
}
