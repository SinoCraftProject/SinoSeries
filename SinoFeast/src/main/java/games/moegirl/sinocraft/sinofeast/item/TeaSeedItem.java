package games.moegirl.sinocraft.sinofeast.item;

import games.moegirl.sinocraft.sinofeast.block.SFBlocks;
import games.moegirl.sinocraft.sinofeast.block.TeaTreeBlock;
import net.minecraft.core.Direction;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;

public class TeaSeedItem extends Item {
    public TeaSeedItem() {
        super(new Properties());
    }

    @Override
    public InteractionResult useOn(UseOnContext context) {
        var level = context.getLevel();
        var pos = context.getClickedPos();

        if (context.getClickedFace() == Direction.UP) {
            if (level.getBlockState(pos).is(BlockTags.DIRT)
                    || level.getBlockState(pos).is(Blocks.FARMLAND)) {
                level.setBlock(pos.above(), SFBlocks.TEA_TREE_BLOCK.get()
                        .defaultBlockState().setValue(TeaTreeBlock.STAGE, 0), Block.UPDATE_ALL);
                context.getPlayer().getItemInHand(context.getHand()).shrink(1);
                return InteractionResult.SUCCESS;
            }
        }

        return super.useOn(context);
    }
}
