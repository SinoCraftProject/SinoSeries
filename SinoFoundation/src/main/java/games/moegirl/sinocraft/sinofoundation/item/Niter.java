package games.moegirl.sinocraft.sinofoundation.item;

import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;

import java.util.function.BiConsumer;

public class Niter extends Item {

    public Niter(Properties properties) {
        super(properties);
    }

    @Override
    public InteractionResult useOn(UseOnContext context) {
        Level level = context.getLevel();
        BlockPos pos = context.getClickedPos().relative(context.getClickedFace());
        BlockState state = level.getBlockState(pos);
        ItemStack item = context.getItemInHand();
        // 结冰
        if (state.is(Blocks.WATER) && state.getFluidState().isSource()) {
            level.setBlock(pos, Blocks.ICE.defaultBlockState(), 11);
            item.shrink(1);
            return InteractionResult.sidedSuccess(level.isClientSide());
        }
        return InteractionResult.FAIL;
    }
}
