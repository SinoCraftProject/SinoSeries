package games.moegirl.sinocraft.sinocore.tree.client;

import com.mojang.blaze3d.vertex.PoseStack;
import games.moegirl.sinocraft.sinocore.tree.Tree;
import games.moegirl.sinocraft.sinocore.tree.TreeBlockType;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.core.BlockPos;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.client.extensions.common.IClientItemExtensions;
import net.minecraftforge.common.util.Lazy;

/**
 * @author luqin2007
 */
public class TreeChestItemRenderer extends BlockEntityWithoutLevelRenderer implements IClientItemExtensions {

    private final Lazy<BlockEntity> entity;

    public TreeChestItemRenderer(TreeBlockType block, Tree tree) {
        super(Minecraft.getInstance().getBlockEntityRenderDispatcher(), Minecraft.getInstance().getEntityModels());
        this.entity = Lazy.of(() -> {
            BlockState state = tree.getBlock(block).defaultBlockState();
            return tree.getBlockEntityType(block).create(BlockPos.ZERO, state);
        });
    }

    @Override
    public BlockEntityWithoutLevelRenderer getCustomRenderer() {
        return this;
    }

    @Override
    public void renderByItem(ItemStack itemStack, ItemDisplayContext context, PoseStack stack, MultiBufferSource source, int light, int overlay) {
        Minecraft.getInstance().getBlockEntityRenderDispatcher().renderItem(entity.get(), stack, source, light, overlay);
    }
}
