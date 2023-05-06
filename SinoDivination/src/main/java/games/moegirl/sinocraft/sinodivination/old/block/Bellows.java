package games.moegirl.sinocraft.sinodivination.old.block;

import net.minecraft.core.Direction;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;

import java.util.EnumMap;
import java.util.Map;

public class Bellows extends HorizontalDirectionalBlock {

    public static final Map<Direction, VoxelShape> SHAPES;

    static {
        SHAPES = new EnumMap<>(Direction.class);
        SHAPES.put(Direction.UP, null);
        SHAPES.put(Direction.DOWN, null);
        SHAPES.put(Direction.EAST, null);
        SHAPES.put(Direction.WEST, null);
        SHAPES.put(Direction.NORTH, null);
        SHAPES.put(Direction.SOUTH, null);
    }

    public Bellows() {
        super(Properties.copy(Blocks.STONE));
        registerDefaultState(getStateDefinition().any().setValue(FACING, Direction.EAST));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder) {
        pBuilder.add(FACING);
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext pContext) {
        return defaultBlockState().setValue(FACING, pContext.getHorizontalDirection());
    }
}
