package games.moegirl.sinocraft.sinodivination.block;

import games.moegirl.sinocraft.sinocore.block.BaseTrappedChestBlock;
import games.moegirl.sinocraft.sinocore.tree.Tree;
import games.moegirl.sinocraft.sinodivination.block.base.CotinusBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;

/**
 * 无患木匣
 */
public class CotinusTrappedChest extends BaseTrappedChestBlock implements CotinusBlock {

    public CotinusTrappedChest(Tree tree, Properties properties) {
        super(properties, tree);
    }

    @Override
    public void setPlacedBy(Level level, BlockPos pos, BlockState state, LivingEntity placer, ItemStack stack) {
        super.setPlacedBy(level, pos, state, placer, stack);
        placedBy(level, pos, placer);
    }

    @Override
    public InteractionResult use(BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit) {
        return isAllowed(pos, player) ? super.use(state, level, pos, player, hand, hit) : InteractionResult.FAIL;
    }
}