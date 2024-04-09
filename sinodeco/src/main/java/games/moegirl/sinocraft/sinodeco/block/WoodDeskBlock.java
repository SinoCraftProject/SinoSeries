package games.moegirl.sinocraft.sinodeco.block;

import games.moegirl.sinocraft.sinocore.utility.block.connection.ConnectUpdateResult;
import games.moegirl.sinocraft.sinocore.utility.block.connection.ConnectionState;
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
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class WoodDeskBlock extends HorizontalDirectionalBlock {
    public static final EnumProperty<ConnectionState> CONNECTION_STATE = EnumProperty.create("connection", ConnectionState.class);
    public static final IntegerProperty EXTRA_MODEL = IntegerProperty.create("extra_model", 0, 1);

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
        return super.getShape(state, level, pos, context);

        // Todo: qyl27: shapes.
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
