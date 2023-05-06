package games.moegirl.sinocraft.sinodivination.old.item;

import games.moegirl.sinocraft.sinodivination.old.block.Rice;
import games.moegirl.sinocraft.sinodivination.old.block.SDBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.ItemNameBlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

public class SeedRice extends ItemNameBlockItem {

    public SeedRice() {
        super(SDBlocks.RICE.get(), new Properties());
    }

    @Override
    public InteractionResult place(BlockPlaceContext pContext) {
        Rice block = SDBlocks.RICE.get();
        BlockPos pos = pContext.getClickedPos();
        Level level = pContext.getLevel();
        BlockState state = level.getBlockState(pos);
        ItemStack stack = pContext.getItemInHand();
        if (block.mayPlaceOn(state, level, pos)) {
            BlockState rise = block.defaultBlockState();
            if (level.setBlock(pContext.getClickedPos(), rise, 11)) {
                block.setPlacedBy(level, pos, rise, pContext.getPlayer(), stack);
                stack.shrink(1);
                return InteractionResult.SUCCESS;
            }
        }
        return InteractionResult.FAIL;
    }
}
