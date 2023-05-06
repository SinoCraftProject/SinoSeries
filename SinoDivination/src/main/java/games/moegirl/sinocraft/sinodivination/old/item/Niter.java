package games.moegirl.sinocraft.sinodivination.old.item;

import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;

public class Niter extends Item {

    public Niter() {
        super(new Properties());
    }

    @Override
    public InteractionResult useOn(UseOnContext pContext) {
        Level level = pContext.getLevel();
        BlockPos pos = pContext.getClickedPos().relative(pContext.getClickedFace());
        BlockState state = level.getBlockState(pos);
        ItemStack item = pContext.getItemInHand();
        if (state.is(Blocks.WATER) && state.getFluidState().isSource()) {
            level.setBlock(pos, Blocks.ICE.defaultBlockState(), 11);
            item.shrink(1);
            return InteractionResult.sidedSuccess(level.isClientSide());
        }
        return InteractionResult.FAIL;
    }
}
