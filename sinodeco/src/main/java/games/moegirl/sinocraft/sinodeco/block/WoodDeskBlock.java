package games.moegirl.sinocraft.sinodeco.block;

import games.moegirl.sinocraft.sinocore.utility.block.shape.BlockShapeHelper;
import net.minecraft.core.BlockPos;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class WoodDeskBlock extends Block {

    /// <editor-fold desc="Connections.">

    public static final BooleanProperty NORTH = BooleanProperty.create("north");
    public static final BooleanProperty SOUTH = BooleanProperty.create("south");
    public static final BooleanProperty WEST = BooleanProperty.create("west");
    public static final BooleanProperty EAST = BooleanProperty.create("east");
    public static final BooleanProperty NORTH_WEST = BooleanProperty.create("north_west");
    public static final BooleanProperty SOUTH_WEST = BooleanProperty.create("south_west");
    public static final BooleanProperty NORTH_EAST = BooleanProperty.create("north_east");
    public static final BooleanProperty SOUTH_EAST = BooleanProperty.create("south_east");
    public static final BooleanProperty[] CONNECTION_PROPERTIES = {NORTH_WEST, NORTH, NORTH_EAST, EAST, SOUTH_EAST, SOUTH, SOUTH_WEST, WEST};

    @Override
    public void neighborChanged(BlockState state, Level level, BlockPos pos,
                                Block neighborBlock, BlockPos neighborPos, boolean movedByPiston) {
        super.neighborChanged(state, level, pos, neighborBlock, neighborPos, movedByPiston);
        updateConnectState(level, pos);
    }

    private void updateConnectState(Level level, BlockPos pos) {
        updateConnectState(level, pos, new HashSet<>());
    }

    private void updateConnectState(Level level, BlockPos pos, Set<BlockPos> visited) {
        var toVisit = new ArrayList<BlockPos>();
        for (var x = -1; x <= 1; x++) {
            for (var z = -1; z<= 1; z++) {
                var neighbor = pos.offset(x, 0, z);
                toVisit.add(neighbor);
            }
        }

        for (var p : toVisit) {
            if (visited.contains(p)) {
                continue;
            }

            visited.add(p);
            if (!isDeskBlock(level, p)) {
                continue;
            }

            doUpdateState(level, p);
            updateConnectState(level, p, visited);
        }
    }

    private boolean isDeskBlock(Level level, BlockPos pos) {
        if (level.isLoaded(pos)) {
            return level.getBlockState(pos).getBlock() instanceof WoodDeskBlock;
        }
        return false;
    }

    private void doUpdateState(Level level, BlockPos pos) {
        if (!level.isLoaded(pos)) {
            return;
        }

        var state = level.getBlockState(pos);
        var cursor = 0;
        for (var x = -1; x <= 1; x++) {
            for (var z = -1; z<= 1; z++) {
                var neighbor = pos.offset(x, 0, z);
                if (x == 0 && z == 0) {
                    continue;
                }
                state = state.setValue(CONNECTION_PROPERTIES[cursor], isDeskBlock(level, neighbor));
                cursor += 1;
            }
        }

        level.setBlock(pos, state, Block.UPDATE_ALL);
    }

    /// </editor-fold>

    /// <editor-fold desc="Shapes.">

    public static final VoxelShape[][] SHAPES = new VoxelShape[16][2];

    static {
        var desktop = Block.box(0, 14, 0, 16, 16, 16);
        var leg1 = Block.box(1, 0, 1, 4, 14, 4);
        var leg2 = BlockShapeHelper.rotateY(leg1);
        var leg3 = BlockShapeHelper.rotateY(leg2);
        var leg4 = BlockShapeHelper.rotateY(leg3);

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
    }

    private VoxelShape getIndexedShape(boolean north, boolean east, boolean south, boolean west, boolean extra) {
        var index = north ? 1 : 0;
        index <<= 1;
        index |= east ? 1 : 0;
        index <<= 1;
        index |= south ? 1 : 0;
        index <<= 1;
        index |= west ? 1 : 0;
        return SHAPES[index][extra ? 0 : 1];
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        var north = state.getValue(NORTH);
        var east = state.getValue(EAST);
        var south = state.getValue(SOUTH);
        var west = state.getValue(WEST);

        var extra = false;
        if (north && west) {
            extra = state.getValue(NORTH_WEST);
        } else if (south && west) {
            extra = state.getValue(SOUTH_WEST);
        } else if (north && east) {
            extra = state.getValue(NORTH_EAST);
        } else if (south && east) {
            extra = state.getValue(SOUTH_EAST);
        }

        return getIndexedShape(north, east, south, west, extra);
    }

    /// </editor-fold>

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
        builder.add(NORTH, SOUTH, WEST, EAST, NORTH_WEST, SOUTH_WEST, NORTH_EAST, SOUTH_EAST);
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        return Objects.requireNonNull(super.getStateForPlacement(context))
                .setValue(NORTH, false)
                .setValue(SOUTH, false)
                .setValue(WEST, false)
                .setValue(EAST, false)
                .setValue(NORTH_WEST, false)
                .setValue(SOUTH_WEST, false)
                .setValue(NORTH_EAST, false)
                .setValue(SOUTH_EAST, false);
    }

    @Override
    public float getShadeBrightness(BlockState state, BlockGetter level, BlockPos pos) {
        return 1;
    }

}
