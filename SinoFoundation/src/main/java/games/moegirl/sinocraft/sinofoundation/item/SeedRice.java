package games.moegirl.sinocraft.sinofoundation.item;

import games.moegirl.sinocraft.sinofoundation.block.SFDBlocks;
import net.minecraft.world.item.ItemNameBlockItem;
import net.minecraft.world.level.block.Block;

/**
 * 糯稻种子
 */
// todo 糯稻
@Deprecated
public class SeedRice extends ItemNameBlockItem {

    public SeedRice(Properties properties) {
        super(SFDBlocks.RICE.get(), properties);
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
