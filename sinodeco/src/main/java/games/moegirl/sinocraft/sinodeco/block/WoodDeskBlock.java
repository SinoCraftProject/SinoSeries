package games.moegirl.sinocraft.sinodeco.block;

import games.moegirl.sinocraft.sinocore.utility.block.connection.ConnectUpdateResult;
import games.moegirl.sinocraft.sinocore.utility.block.connection.ConnectionState;
import games.moegirl.sinocraft.sinocore.utility.block.shape.BlockShapeHelper;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class WoodDeskBlock extends HorizontalDirectionalBlock {
    public static final EnumProperty<ConnectionState> CONNECTION_STATE = EnumProperty.create("connection", ConnectionState.class);
    public static final IntegerProperty EXTRA_MODEL = IntegerProperty.create("extra_model", 0, 1);

    public static final VoxelShape[][] SHAPES = new VoxelShape[7][4];

    static {
        var desktop = Block.box(0, 14, 0, 16, 16, 16);
        var desktopWaist1 = Block.box(4, 10, 1.6, 12, 14, 2.4);
        var desktopWaist2 = BlockShapeHelper.rotateY(desktopWaist1);
        var desktopWaist3 = BlockShapeHelper.rotateY(desktopWaist2);
        var desktopWaist4 = BlockShapeHelper.rotateY(desktopWaist3);
        var desktopLeftSideWaist1 = Block.box(0, 10, 1.6, 13, 14, 2.4);
        var desktopLeftSideWaist2 = BlockShapeHelper.rotateY(desktopLeftSideWaist1);
//        var desktopLeftSideWaist3 = BlockShapeHelper.rotateY(desktopLeftSideWaist2);
//        var desktopLeftSideWaist4 = BlockShapeHelper.rotateY(desktopLeftSideWaist3);
        var desktopRightSideWaist3 = Block.box(0, 10, 13.6, 12, 14, 14.4);
//        var desktopRightSideWaist4 = BlockShapeHelper.rotateY(desktopRightSideWaist3);
        var desktopLongWaist1 = Block.box(0, 10, 1.6, 16, 14, 2.4);
        var desktopLongWaist2 = BlockShapeHelper.rotateY(desktopLongWaist1);
        var desktopLongWaist3 = BlockShapeHelper.rotateY(desktopLongWaist2);
//        var desktopLongWaist4 = BlockShapeHelper.rotateY(desktopLongWaist3);
        var leg1 = Block.box(1, 0, 1, 4, 14, 4);
        var leg2 = BlockShapeHelper.rotateY(leg1);
        var leg3 = BlockShapeHelper.rotateY(leg2);
        var leg4 = BlockShapeHelper.rotateY(leg3);

        SHAPES[0][0] = Shapes.or(desktop, desktopWaist1, desktopWaist2, desktopWaist3, desktopWaist4, leg1, leg2, leg3, leg4);
        SHAPES[0][1] = SHAPES[0][0];
        SHAPES[0][2] = SHAPES[0][0];
        SHAPES[0][3] = SHAPES[0][0];
        SHAPES[1][0] = Shapes.or(desktop, desktopLeftSideWaist1, desktopWaist2, desktopRightSideWaist3, leg2, leg3);
        SHAPES[1][1] = BlockShapeHelper.rotateY(SHAPES[1][0]);
        SHAPES[1][2] = BlockShapeHelper.rotateY(SHAPES[1][1]);
        SHAPES[1][3] = BlockShapeHelper.rotateY(SHAPES[1][2]);
        SHAPES[2][2] = Shapes.or(desktop, desktopLongWaist1, desktopLongWaist3);
        SHAPES[2][1] = BlockShapeHelper.rotateY(SHAPES[2][2]);
        SHAPES[2][0] = BlockShapeHelper.rotateY(SHAPES[2][1]);
        SHAPES[2][3] = BlockShapeHelper.rotateY(SHAPES[2][0]);
        SHAPES[3][0] = Shapes.or(desktop, desktopRightSideWaist3, desktopLeftSideWaist2, leg3);
        SHAPES[3][1] = BlockShapeHelper.rotateY(SHAPES[3][0]);
        SHAPES[3][2] = BlockShapeHelper.rotateY(SHAPES[3][1]);
        SHAPES[3][3] = BlockShapeHelper.rotateY(SHAPES[3][2]);
        SHAPES[4][0] = Shapes.or(desktop, desktopRightSideWaist3, desktopLeftSideWaist2, leg1, leg3);
        SHAPES[4][1] = BlockShapeHelper.rotateY(SHAPES[4][0]);
        SHAPES[4][2] = BlockShapeHelper.rotateY(SHAPES[4][1]);
        SHAPES[4][3] = BlockShapeHelper.rotateY(SHAPES[4][2]);
        SHAPES[5][1] = Shapes.or(desktop, desktopLongWaist3);
        SHAPES[5][2] = BlockShapeHelper.rotateY(SHAPES[5][1]);
        SHAPES[5][3] = BlockShapeHelper.rotateY(SHAPES[5][2]);
        SHAPES[5][0] = BlockShapeHelper.rotateY(SHAPES[5][3]);
        SHAPES[6][0] = Shapes.or(desktop);
        SHAPES[6][1] = SHAPES[6][0];
        SHAPES[6][2] = SHAPES[6][0];
        SHAPES[6][3] = SHAPES[6][0];
    }

    public WoodDeskBlock() {
        super(Properties.copy(Blocks.OAK_PLANKS)
                .forceSolidOn()
                .dynamicShape()
                .strength(2.5f)
                .noOcclusion());
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder);
        builder.add(FACING);
        builder.add(CONNECTION_STATE);
        builder.add(EXTRA_MODEL);
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        var result = updateConnectState(context.getLevel(), context.getClickedPos(), true);
        var model = 0;
        if (result.state() == ConnectionState.ORTHO) {
            // Todo: qyl27: extra model for ortho with an extra leg.
        }

        return Objects.requireNonNull(super.getStateForPlacement(context))
                .setValue(FACING, result.facing())
                .setValue(CONNECTION_STATE, result.state())
                .setValue(EXTRA_MODEL, 0);
    }

    @Override
    public void neighborChanged(BlockState state, Level level, BlockPos pos, Block neighborBlock, BlockPos neighborPos, boolean movedByPiston) {
        super.neighborChanged(state, level, pos, neighborBlock, neighborPos, movedByPiston);

        updateConnectState(level, pos, false);
    }

    @Override
    public float getShadeBrightness(BlockState state, BlockGetter level, BlockPos pos) {
        return 1;
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return switch (state.getValue(CONNECTION_STATE)) {
            case NONE -> SHAPES[0][state.getValue(FACING).get2DDataValue()];
            case SINGLE -> SHAPES[1][state.getValue(FACING).get2DDataValue()];
            case PARA -> SHAPES[2][state.getValue(FACING).get2DDataValue()];
            case ORTHO -> SHAPES[3 + state.getValue(EXTRA_MODEL)][state.getValue(FACING).get2DDataValue()];
            case VICINAL -> SHAPES[5][state.getValue(FACING).get2DDataValue()];
            case ALL -> SHAPES[6][state.getValue(FACING).get2DDataValue()];
        };
    }

    private ConnectUpdateResult updateConnectState(Level level, BlockPos pos, boolean isStartPos) {
        return updateConnectState(level, pos, isStartPos, new HashSet<>());
    }

    private ConnectUpdateResult updateConnectState(Level level, BlockPos pos, boolean isStartPos, Set<BlockPos> visited) {
        var northPos = pos.north();
        var north = checkConnectState(level, northPos);
        var eastPos = pos.east();
        var east = checkConnectState(level, eastPos);
        var southPos = pos.south();
        var south = checkConnectState(level, southPos);
        var westPos = pos.west();
        var west = checkConnectState(level, westPos);

        var list = new ArrayList<BlockPos>();
        list.add(northPos);
        list.add(eastPos);
        list.add(southPos);
        list.add(westPos);
        for (var p : list) {
            if (visited.contains(p)) {
                continue;
            }

            if (!checkConnectState(level, p)) {
                continue;
            }

            visited.add(p);
            updateConnectState(level, p, false, visited);
        }

        var state = ConnectionState.fromSide(north, east, south, west);
        var rotation = ConnectionState.getRotateBySide(north, east, south, west);

        var direction = Direction.NORTH;
        for (var i = 0; i < rotation; i++) {
            direction = direction.getClockWise();
        }

        if (!isStartPos) {
            level.setBlock(pos, level.getBlockState(pos)
                    .setValue(CONNECTION_STATE, state)
                    .setValue(FACING, direction), Block.UPDATE_ALL);
        }

        return new ConnectUpdateResult(direction, state);
    }

    private boolean checkConnectState(Level level, BlockPos pos) {
        var state = level.getBlockState(pos);
        return state.getBlock() instanceof WoodDeskBlock;
    }
}
