package games.moegirl.sinocraft.sinodivination.item;

import games.moegirl.sinocraft.sinodivination.block.SDBlocks;
import net.minecraft.world.item.ItemNameBlockItem;

/**
 * 糯稻种子
 */
// todo 如果正确运行，该类可移除
@Deprecated(forRemoval = true)
public class SeedRice extends ItemNameBlockItem {

    public SeedRice() {
        super(SDBlocks.RICE.get(), new Properties());
    }

//    @Override
//    public InteractionResult place(BlockPlaceContext context) {
//        Rice block = SDBlocks.RICE.get();
//        BlockPos pos = context.getClickedPos();
//        Level level = context.getLevel();
//        BlockState state = level.getBlockState(pos);
//        ItemStack stack = context.getItemInHand();
//        if (block.mayPlaceOn(state, level, pos) && context.canPlace()) {
//            BlockState rise = block.defaultBlockState();
//            if (level.setBlock(context.getClickedPos(), rise, 11)) {
//                block.setPlacedBy(level, pos, rise, context.getPlayer(), stack);
//                stack.shrink(1);
//
//                return InteractionResult.SUCCESS;
//            }
//        }
//        return InteractionResult.FAIL;
//    }
}
