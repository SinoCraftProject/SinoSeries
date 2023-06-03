package games.moegirl.sinocraft.sinofoundation.block;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.WallBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

public class MarbleWallBlock extends WallBlock {
    public MarbleWallBlock(Properties arg) {
        super(arg);
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        // Todo: qyl27: custom block shape.
        return super.getShape(state, level, pos, context);
    }
}
