package games.moegirl.sinocraft.sinodeco.block;

import games.moegirl.sinocraft.sinocore.helper.shape.BlockShapeHelper;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class MarbleBalustradeBlock extends Block implements SimpleWaterloggedBlock {
    // 0 for none, 1 for low, 2 for high
    public static final IntegerProperty UP = IntegerProperty.create("up", 0, 2);
    public static final BooleanProperty NORTH = BooleanProperty.create("north");
    public static final BooleanProperty EAST = BooleanProperty.create("east");
    public static final BooleanProperty SOUTH = BooleanProperty.create("south");
    public static final BooleanProperty WEST = BooleanProperty.create("west");

    public MarbleBalustradeBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(this.stateDefinition.any().setValue(UP, 0)
                .setValue(NORTH, false)
                .setValue(EAST, false)
                .setValue(SOUTH, false)
                .setValue(WEST, false)
                .setValue(BlockStateProperties.WATERLOGGED, false));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder);
        builder.add(UP, NORTH, EAST, SOUTH, WEST, BlockStateProperties.WATERLOGGED);
    }

    /// <editor-fold desc="Shapes.">

    // Up, North, East, South, West
    // False = 0, True = 1
    public static final VoxelShape[][][][][] SHAPES = new VoxelShape[3][2][2][2][2];
    public static final VoxelShape[][][][][] COLLISION_SHAPES = new VoxelShape[3][2][2][2][2];

    static {
        VoxelShape upTall;
        {
            var top1 = box(6, 15, 6, 10, 22, 10);
            var top2 = box(6, 20, 5, 10, 21, 11);
            var top3 = box(5, 20, 6, 11, 21, 10);
            var top4 = box(5, 17, 5, 11, 20, 11);
            var top5 = box(5, 16, 6, 11, 17, 10);
            var top6 = box(6, 17, 5, 10, 17, 11);
            var body = box(4, 3, 4, 12, 15, 12);
            var foot = box(3, 0, 3, 13, 2, 13);
            upTall = Shapes.or(top1, top2, top3, top4, top5, top6, body, foot);
        }

        VoxelShape upLow;
        {
            var part1 = box(3, 14, 3, 13, 16, 13);
            var part2 = box(4, 3, 4, 12, 14, 12);
            var part3 = box(3, 0, 3, 13, 2, 13);
            upLow = Shapes.or(part1, part2, part3);
        }

        VoxelShape sideN;
        {
            var part1 = box(6, 13, 0, 10, 14, 8);
            var part2 = box(5, 11, 0, 11, 13, 8);
            var part3 = box(5, 10, 5, 11, 11, 6);
            var part4 = box(5, 7, 6, 11, 11, 8);
            var part5 = box(5, 7, 5, 11, 8, 6);
            var part6 = box(5, 0, 0, 11, 7, 8);
            sideN = Shapes.or(part1, part2, part3, part4, part5, part6);
        }
        VoxelShape sideE = BlockShapeHelper.rotateY(sideN);
        VoxelShape sideS = BlockShapeHelper.rotateY(sideE);
        VoxelShape sideW = BlockShapeHelper.rotateY(sideS);

        SHAPES[0][0][0][0][0] = Shapes.empty();
        SHAPES[0][0][0][0][1] = Shapes.or(sideW);
        SHAPES[0][0][0][1][0] = Shapes.or(sideS);
        SHAPES[0][0][0][1][1] = Shapes.or(sideS, sideW);
        SHAPES[0][0][1][0][0] = Shapes.or(sideE);
        SHAPES[0][0][1][0][1] = Shapes.or(sideE, sideW);
        SHAPES[0][0][1][1][0] = Shapes.or(sideE, sideS);
        SHAPES[0][0][1][1][1] = Shapes.or(sideE, sideS, sideW);
        SHAPES[0][1][0][0][0] = Shapes.or(sideN);
        SHAPES[0][1][0][0][1] = Shapes.or(sideN, sideW);
        SHAPES[0][1][0][1][0] = Shapes.or(sideN, sideS);
        SHAPES[0][1][0][1][1] = Shapes.or(sideN, sideS, sideW);
        SHAPES[0][1][1][0][0] = Shapes.or(sideN, sideE);
        SHAPES[0][1][1][0][1] = Shapes.or(sideN, sideE, sideW);
        SHAPES[0][1][1][1][0] = Shapes.or(sideN, sideE, sideS);
        SHAPES[0][1][1][1][1] = Shapes.or(sideN, sideE, sideS, sideW);
        SHAPES[1][0][0][0][0] = Shapes.or(upLow);
        SHAPES[1][0][0][0][1] = Shapes.or(upLow, sideW);
        SHAPES[1][0][0][1][0] = Shapes.or(upLow, sideS);
        SHAPES[1][0][0][1][1] = Shapes.or(upLow, sideS, sideW);
        SHAPES[1][0][1][0][0] = Shapes.or(upLow, sideE);
        SHAPES[1][0][1][0][1] = Shapes.or(upLow, sideE, sideW);
        SHAPES[1][0][1][1][0] = Shapes.or(upLow, sideE, sideS);
        SHAPES[1][0][1][1][1] = Shapes.or(upLow, sideE, sideS, sideW);
        SHAPES[1][1][0][0][0] = Shapes.or(upLow, sideN);
        SHAPES[1][1][0][0][1] = Shapes.or(upLow, sideN, sideW);
        SHAPES[1][1][0][1][0] = Shapes.or(upLow, sideN, sideS);
        SHAPES[1][1][0][1][1] = Shapes.or(upLow, sideN, sideS, sideW);
        SHAPES[1][1][1][0][0] = Shapes.or(upLow, sideN, sideE);
        SHAPES[1][1][1][0][1] = Shapes.or(upLow, sideN, sideE, sideW);
        SHAPES[1][1][1][1][0] = Shapes.or(upLow, sideN, sideE, sideS);
        SHAPES[1][1][1][1][1] = Shapes.or(upLow, sideN, sideE, sideS, sideW);
        SHAPES[2][0][0][0][0] = Shapes.or(upTall);
        SHAPES[2][0][0][0][1] = Shapes.or(upTall, sideW);
        SHAPES[2][0][0][1][0] = Shapes.or(upTall, sideS);
        SHAPES[2][0][0][1][1] = Shapes.or(upTall, sideS, sideW);
        SHAPES[2][0][1][0][0] = Shapes.or(upTall, sideE);
        SHAPES[2][0][1][0][1] = Shapes.or(upTall, sideE, sideW);
        SHAPES[2][0][1][1][0] = Shapes.or(upTall, sideE, sideS);
        SHAPES[2][0][1][1][1] = Shapes.or(upTall, sideE, sideS, sideW);
        SHAPES[2][1][0][0][0] = Shapes.or(upTall, sideN);
        SHAPES[2][1][0][0][1] = Shapes.or(upTall, sideN, sideW);
        SHAPES[2][1][0][1][0] = Shapes.or(upTall, sideN, sideS);
        SHAPES[2][1][0][1][1] = Shapes.or(upTall, sideN, sideS, sideW);
        SHAPES[2][1][1][0][0] = Shapes.or(upTall, sideN, sideE);
        SHAPES[2][1][1][0][1] = Shapes.or(upTall, sideN, sideE, sideW);
        SHAPES[2][1][1][1][0] = Shapes.or(upTall, sideN, sideE, sideS);
        SHAPES[2][1][1][1][1] = Shapes.or(upTall, sideN, sideE, sideS, sideW);

        VoxelShape collisionUp = box(3, 0, 3, 10, 24, 10);
        VoxelShape collisionSideN = box(5, 0, 0, 11, 24, 8);
        VoxelShape collisionSideE = BlockShapeHelper.rotateY(collisionSideN);
        VoxelShape collisionSideS = BlockShapeHelper.rotateY(collisionSideE);
        VoxelShape collisionSideW = BlockShapeHelper.rotateY(collisionSideS);

        COLLISION_SHAPES[0][0][0][0][0] = Shapes.empty();
        COLLISION_SHAPES[0][0][0][0][1] = Shapes.or(collisionSideW);
        COLLISION_SHAPES[0][0][0][1][0] = Shapes.or(collisionSideS);
        COLLISION_SHAPES[0][0][0][1][1] = Shapes.or(collisionSideS, collisionSideW);
        COLLISION_SHAPES[0][0][1][0][0] = Shapes.or(collisionSideE);
        COLLISION_SHAPES[0][0][1][0][1] = Shapes.or(collisionSideE, collisionSideW);
        COLLISION_SHAPES[0][0][1][1][0] = Shapes.or(collisionSideE, collisionSideS);
        COLLISION_SHAPES[0][0][1][1][1] = Shapes.or(collisionSideE, collisionSideS, collisionSideW);
        COLLISION_SHAPES[0][1][0][0][0] = Shapes.or(collisionSideN);
        COLLISION_SHAPES[0][1][0][0][1] = Shapes.or(collisionSideN, collisionSideW);
        COLLISION_SHAPES[0][1][0][1][0] = Shapes.or(collisionSideN, collisionSideS);
        COLLISION_SHAPES[0][1][0][1][1] = Shapes.or(collisionSideN, collisionSideS, collisionSideW);
        COLLISION_SHAPES[0][1][1][0][0] = Shapes.or(collisionSideN, collisionSideE);
        COLLISION_SHAPES[0][1][1][0][1] = Shapes.or(collisionSideN, collisionSideE, collisionSideW);
        COLLISION_SHAPES[0][1][1][1][0] = Shapes.or(collisionSideN, collisionSideE, collisionSideS);
        COLLISION_SHAPES[0][1][1][1][1] = Shapes.or(collisionSideN, collisionSideE, collisionSideS, collisionSideW);
        COLLISION_SHAPES[1][0][0][0][0] = Shapes.or(collisionUp);
        COLLISION_SHAPES[1][0][0][0][1] = Shapes.or(collisionUp, collisionSideW);
        COLLISION_SHAPES[1][0][0][1][0] = Shapes.or(collisionUp, collisionSideS);
        COLLISION_SHAPES[1][0][0][1][1] = Shapes.or(collisionUp, collisionSideS, collisionSideW);
        COLLISION_SHAPES[1][0][1][0][0] = Shapes.or(collisionUp, collisionSideE);
        COLLISION_SHAPES[1][0][1][0][1] = Shapes.or(collisionUp, collisionSideE, collisionSideW);
        COLLISION_SHAPES[1][0][1][1][0] = Shapes.or(collisionUp, collisionSideE, collisionSideS);
        COLLISION_SHAPES[1][0][1][1][1] = Shapes.or(collisionUp, collisionSideE, collisionSideS, collisionSideW);
        COLLISION_SHAPES[1][1][0][0][0] = Shapes.or(collisionUp, collisionSideN);
        COLLISION_SHAPES[1][1][0][0][1] = Shapes.or(collisionUp, collisionSideN, collisionSideW);
        COLLISION_SHAPES[1][1][0][1][0] = Shapes.or(collisionUp, collisionSideN, collisionSideS);
        COLLISION_SHAPES[1][1][0][1][1] = Shapes.or(collisionUp, collisionSideN, collisionSideS, collisionSideW);
        COLLISION_SHAPES[1][1][1][0][0] = Shapes.or(collisionUp, collisionSideN, collisionSideE);
        COLLISION_SHAPES[1][1][1][0][1] = Shapes.or(collisionUp, collisionSideN, collisionSideE, collisionSideW);
        COLLISION_SHAPES[1][1][1][1][0] = Shapes.or(collisionUp, collisionSideN, collisionSideE, collisionSideS);
        COLLISION_SHAPES[1][1][1][1][1] = Shapes.or(collisionUp, collisionSideN, collisionSideE, collisionSideS, collisionSideW);
        COLLISION_SHAPES[2][0][0][0][0] = Shapes.or(collisionUp);
        COLLISION_SHAPES[2][0][0][0][1] = Shapes.or(collisionUp, collisionSideW);
        COLLISION_SHAPES[2][0][0][1][0] = Shapes.or(collisionUp, collisionSideS);
        COLLISION_SHAPES[2][0][0][1][1] = Shapes.or(collisionUp, collisionSideS, collisionSideW);
        COLLISION_SHAPES[2][0][1][0][0] = Shapes.or(collisionUp, collisionSideE);
        COLLISION_SHAPES[2][0][1][0][1] = Shapes.or(collisionUp, collisionSideE, collisionSideW);
        COLLISION_SHAPES[2][0][1][1][0] = Shapes.or(collisionUp, collisionSideE, collisionSideS);
        COLLISION_SHAPES[2][0][1][1][1] = Shapes.or(collisionUp, collisionSideE, collisionSideS, collisionSideW);
        COLLISION_SHAPES[2][1][0][0][0] = Shapes.or(collisionUp, collisionSideN);
        COLLISION_SHAPES[2][1][0][0][1] = Shapes.or(collisionUp, collisionSideN, collisionSideW);
        COLLISION_SHAPES[2][1][0][1][0] = Shapes.or(collisionUp, collisionSideN, collisionSideS);
        COLLISION_SHAPES[2][1][0][1][1] = Shapes.or(collisionUp, collisionSideN, collisionSideS, collisionSideW);
        COLLISION_SHAPES[2][1][1][0][0] = Shapes.or(collisionUp, collisionSideN, collisionSideE);
        COLLISION_SHAPES[2][1][1][0][1] = Shapes.or(collisionUp, collisionSideN, collisionSideE, collisionSideW);
        COLLISION_SHAPES[2][1][1][1][0] = Shapes.or(collisionUp, collisionSideN, collisionSideE, collisionSideS);
        COLLISION_SHAPES[2][1][1][1][1] = Shapes.or(collisionUp, collisionSideN, collisionSideE, collisionSideS, collisionSideW);
    }

    @Override
    public @NotNull VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        var up = state.getValue(UP);
        var north = state.getValue(NORTH) ? 1 : 0;
        var east = state.getValue(EAST) ? 1 : 0;
        var south = state.getValue(SOUTH) ? 1 : 0;
        var west = state.getValue(WEST) ? 1 : 0;

        return SHAPES[up][north][east][south][west];
    }

    @Override
    public @NotNull VoxelShape getCollisionShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        var up = state.getValue(UP);
        var north = state.getValue(NORTH) ? 1 : 0;
        var east = state.getValue(EAST) ? 1 : 0;
        var south = state.getValue(SOUTH) ? 1 : 0;
        var west = state.getValue(WEST) ? 1 : 0;

        return COLLISION_SHAPES[up][north][east][south][west];
    }

    @Override
    public @NotNull BlockState updateShape(BlockState state, Direction direction, BlockState neighborState,
                                           LevelAccessor level, BlockPos pos, BlockPos neighborPos) {
        if (state.getValue(BlockStateProperties.WATERLOGGED)) {
            level.scheduleTick(pos, Fluids.WATER, Fluids.WATER.getTickDelay(level));
        }

        return updateShape(state, pos, level);
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        return updateShape(defaultBlockState(), context.getClickedPos(), context.getLevel());
    }

    private boolean canConnectTo(BlockState state, boolean isFaceSturdy, Direction direction) {
        Block block = state.getBlock();
        boolean fenceConnectResult = block instanceof FenceGateBlock && FenceGateBlock.connectsToDirection(state, direction);
        return state.is(BlockTags.WALLS) || !isExceptionForConnection(state) && isFaceSturdy || block instanceof IronBarsBlock || fenceConnectResult;
    }

    private BlockState updateShape(BlockState state, BlockPos pos, LevelAccessor level) {
        var upPos = pos.above();
        var northPos = pos.north();
        var eastPos = pos.east();
        var southPos = pos.south();
        var westPos = pos.west();

        var upBlock = level.getBlockState(upPos);
        var northBlock = level.getBlockState(northPos);
        var eastBlock = level.getBlockState(eastPos);
        var southBlock = level.getBlockState(southPos);
        var westBlock = level.getBlockState(westPos);

        var north = canConnectTo(northBlock, northBlock.isFaceSturdy(level, northPos, Direction.NORTH), Direction.NORTH);
        var east = canConnectTo(eastBlock, eastBlock.isFaceSturdy(level, eastPos, Direction.EAST), Direction.EAST);
        var south = canConnectTo(southBlock, southBlock.isFaceSturdy(level, southPos, Direction.SOUTH), Direction.SOUTH);
        var west = canConnectTo(westBlock, westBlock.isFaceSturdy(level, westPos, Direction.WEST), Direction.WEST);

        var blC = north ? 1 : 0;
        blC += east ? 1 : 0;
        blC += south ? 1 : 0;
        blC += west ? 1 : 0;
        var up = ((north && south) || (east && west)) && blC != 3 ? (upBlock.isAir() ? 0 : 1) : 2;

        return state.setValue(UP, up)
                .setValue(NORTH, north)
                .setValue(EAST, east)
                .setValue(SOUTH, south)
                .setValue(WEST, west);
    }

    /// </editor-fold>

    @Override
    protected boolean isPathfindable(BlockState state, PathComputationType pathComputationType) {
        return false;
    }

    @Override
    public @NotNull FluidState getFluidState(BlockState state) {
        return state.getValue(BlockStateProperties.WATERLOGGED) ? Fluids.WATER.getSource(false) : super.getFluidState(state);
    }

    @Override
    public boolean propagatesSkylightDown(BlockState state, BlockGetter level, BlockPos pos) {
        return !state.getValue(BlockStateProperties.WATERLOGGED);
    }

    @Override
    public @NotNull BlockState rotate(BlockState state, Rotation rotation) {
        return switch (rotation) {
            case CLOCKWISE_180 -> state.setValue(NORTH, state.getValue(SOUTH))
                    .setValue(EAST, state.getValue(WEST))
                    .setValue(SOUTH, state.getValue(NORTH))
                    .setValue(WEST, state.getValue(EAST));
            case COUNTERCLOCKWISE_90 -> state.setValue(NORTH, state.getValue(EAST))
                    .setValue(EAST, state.getValue(SOUTH))
                    .setValue(SOUTH, state.getValue(WEST))
                    .setValue(WEST, state.getValue(NORTH));
            case CLOCKWISE_90 -> state.setValue(NORTH, state.getValue(WEST))
                    .setValue(EAST, state.getValue(NORTH))
                    .setValue(SOUTH, state.getValue(EAST))
                    .setValue(WEST, state.getValue(SOUTH));
            default -> state;
        };
    }

    @Override
    public @NotNull BlockState mirror(BlockState state, Mirror mirror) {
        return switch (mirror) {
            case LEFT_RIGHT -> state.setValue(NORTH, state.getValue(SOUTH)).setValue(SOUTH, state.getValue(NORTH));
            case FRONT_BACK -> state.setValue(EAST, state.getValue(WEST)).setValue(WEST, state.getValue(EAST));
            default -> super.mirror(state, mirror);
        };
    }
}
