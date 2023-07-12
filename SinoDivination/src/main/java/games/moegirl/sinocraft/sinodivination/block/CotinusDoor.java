package games.moegirl.sinocraft.sinodivination.block;

import games.moegirl.sinocraft.sinocore.tree.Tree;
import games.moegirl.sinocraft.sinocore.tree.TreeBlockType;
import games.moegirl.sinocraft.sinofoundation.block.entity.tree.CotinusDoorEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.DoorBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;

import javax.annotation.Nullable;

/**
 * 无患木门
 */
public class CotinusDoor extends DoorBlock implements CotinusBlock {

    private final Tree tree;

    public CotinusDoor(Tree tree, Properties properties) {
        super(properties, tree.getBlockSetType());
        this.tree = tree;
    }

    @Override
    public void setPlacedBy(Level level, BlockPos pos, BlockState state, LivingEntity placer, ItemStack stack) {
        super.setPlacedBy(level, pos, state, placer, stack);
        placedBy(level, pos, placer);
        CotinusDoorEntity above = (CotinusDoorEntity) level.getBlockEntity(pos.above());
        assert above != null;
        above.above();
    }

    @Override
    public InteractionResult use(BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit) {
        return isAllowed(pos, player) ? super.use(state, level, pos, player, hand, hit) : InteractionResult.FAIL;
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return tree.getBlockEntityType(TreeBlockType.DOOR).create(pos, state);
    }
}
