package games.moegirl.sinocraft.sinodivination.old.block;

import games.moegirl.sinocraft.sinocore.tree.Tree;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.FenceGateBlock;
import net.minecraft.world.level.block.state.BlockState;

public class JujubeFenceGate extends FenceGateBlock {

    public JujubeFenceGate(Tree tree, Properties properties) {
        super(properties, tree.getWoodType());
    }

    @Override
    public int getSignal(BlockState state, BlockGetter level, BlockPos pos, Direction direction) {
        return state.getValue(OPEN) ? 1 : 0;
    }
}
