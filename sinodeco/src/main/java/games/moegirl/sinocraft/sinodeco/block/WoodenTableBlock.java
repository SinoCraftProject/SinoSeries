package games.moegirl.sinocraft.sinodeco.block;

import games.moegirl.sinocraft.sinocore.utility.shape.BlockShapeHelper;
import games.moegirl.sinocraft.sinocore.utility.shape.VoxelShapeHelper;
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
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.*;

public class WoodenTableBlock extends Block {

    /// <editor-fold desc="Connections.">

    public static final BooleanProperty NORTH = BooleanProperty.create("north");
    public static final BooleanProperty SOUTH = BooleanProperty.create("south");
    public static final BooleanProperty WEST = BooleanProperty.create("west");
    public static final BooleanProperty EAST = BooleanProperty.create("east");
    public static final BooleanProperty NORTH_WEST = BooleanProperty.create("north_west");
    public static final BooleanProperty SOUTH_WEST = BooleanProperty.create("south_west");
    public static final BooleanProperty NORTH_EAST = BooleanProperty.create("north_east");
    public static final BooleanProperty SOUTH_EAST = BooleanProperty.create("south_east");

    public static final BooleanProperty[] CONNECTION_PROPERTIES = {NORTH_WEST, WEST, SOUTH_WEST, NORTH, SOUTH, NORTH_EAST, EAST, SOUTH_EAST};

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
            return level.getBlockState(pos).getBlock() instanceof WoodenTableBlock;
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

    public static final DeskPart[][][] PARTS = new DeskPart[16][16][];
    public static final VoxelShape[][] SHAPES = new VoxelShape[16][16];

    static {
        var top = new DeskPart("top", 0, Block.box(0, 14, 0, 16, 16, 16));
        var leg1 = new DeskPart("leg", 0, Block.box(1, 0, 1, 4, 14, 4));
        var leg2 = new DeskPart("leg", 90, BlockShapeHelper.rotateY(leg1.shape()));
        var leg3 = new DeskPart("leg", 180, BlockShapeHelper.rotateY(leg2.shape()));
        var leg4 = new DeskPart("leg", 270, BlockShapeHelper.rotateY(leg3.shape()));

        var conditionNotLeg1 = new DeskPart(leg1.name(), leg1.rotate(), leg1.shape(), NORTH_WEST, false);
        var conditionNotLeg2 = new DeskPart(leg2.name(), leg2.rotate(), leg2.shape(), NORTH_EAST, false);
        var conditionNotLeg3 = new DeskPart(leg3.name(), leg3.rotate(), leg3.shape(), SOUTH_EAST, false);
        var conditionNotLeg4 = new DeskPart(leg4.name(), leg4.rotate(), leg4.shape(), SOUTH_WEST, false);

        DeskPart desktopWaistBoth1;
        {
            var waistPart1 = Block.box(10, 11, 1.6, 16, 12, 2.4);
            var waistPart2 = Block.box(14, 12, 1.6, 15, 14, 2.4);
            var waistPart3 = Block.box(12, 12, 1.6, 13, 14, 2.4);
            var waistPart4 = Block.box(5, 12, 1.6, 11, 13, 2.4);
            var waistPart5 = Block.box(5, 10, 1.6, 11, 11, 2.4);
            var waistPart6 = Block.box(0, 11, 1.6, 6, 12, 2.4);
            var waistPart7 = Block.box(3, 12, 1.6, 4, 14, 2.4);
            var waistPart8 = Block.box(1, 12, 1.6, 2, 14, 2.4);
            desktopWaistBoth1 = new DeskPart("waist_both", 0, Shapes.or(waistPart1, waistPart2, waistPart3, waistPart4, waistPart5, waistPart6, waistPart7, waistPart8));
        }
        var desktopWaistBoth2 = new DeskPart("waist_both", 90, BlockShapeHelper.rotateY(desktopWaistBoth1.shape()));
        var desktopWaistBoth3 = new DeskPart("waist_both", 180, BlockShapeHelper.rotateY(desktopWaistBoth2.shape()));
        var desktopWaistBoth4 = new DeskPart("waist_both", 270, BlockShapeHelper.rotateY(desktopWaistBoth3.shape()));

        DeskPart desktopWaistNone1;
        {
            var waistPart1 = Block.box(9, 13, 1.6, 12, 14, 2.4);
            var waistPart2 = Block.box(11, 11, 1.6, 12, 13, 2.4);
            var waistPart3 = Block.box(9, 12, 1.6, 10, 13, 2.4);
            var waistPart4 = Block.box(4, 10, 1.6, 12, 11, 2.4);
            var waistPart5 = Block.box(6, 12, 1.6, 7, 13, 2.4);
            var waistPart6 = Block.box(4, 13, 1.6, 7, 14, 2.4);
            var waistPart7 = Block.box(4, 11, 1.6, 5, 13, 2.4);
            desktopWaistNone1 = new DeskPart("waist_none", 0, Shapes.or(waistPart1, waistPart2, waistPart3, waistPart4, waistPart5, waistPart6, waistPart7));
        }
        var desktopWaistNone2 = new DeskPart("waist_none", 90, BlockShapeHelper.rotateY(desktopWaistNone1.shape()));
        var desktopWaistNone3 = new DeskPart("waist_none", 180, BlockShapeHelper.rotateY(desktopWaistNone2.shape()));
        var desktopWaistNone4 = new DeskPart("waist_none", 270, BlockShapeHelper.rotateY(desktopWaistNone3.shape()));

        DeskPart desktopWaistLeft1;
        {
            var waistPart1 = Block.box(11, 11, 1.6, 12, 13, 2.4);
            var waistPart2 = Block.box(10, 12, 1.6, 11, 13, 2.4);
            var waistPart3 = Block.box(2, 10, 1.6, 12, 11, 2.4);
            var waistPart4 = Block.box(8, 13, 1.6, 9, 14, 2.4);
            var waistPart5 = Block.box(4, 12, 1.6, 9, 13, 2.4);
            var waistPart6 = Block.box(7, 11, 1.6, 8, 12, 2.4);
            var waistPart7 = Block.box(5, 11, 1.6, 6, 12, 2.4);
            var waistPart8 = Block.box(4, 13, 1.6, 5, 14, 2.4);
            var waistPart9 = Block.box(0, 11, 1.6, 3, 12, 2.4);
            var waistPart10 = Block.box(1, 12, 1.6, 2, 14, 2.4);
            desktopWaistLeft1 = new DeskPart("waist_left", 0, Shapes.or(waistPart1, waistPart2, waistPart3, waistPart4, waistPart5, waistPart6, waistPart7, waistPart8, waistPart9, waistPart10));
        }
        var desktopWaistLeft2 = new DeskPart("waist_left", 90, BlockShapeHelper.rotateY(desktopWaistLeft1.shape()));
        var desktopWaistLeft3 = new DeskPart("waist_left", 180, BlockShapeHelper.rotateY(desktopWaistLeft2.shape()));
        var desktopWaistLeft4 = new DeskPart("waist_left", 270, BlockShapeHelper.rotateY(desktopWaistLeft3.shape()));

        DeskPart desktopWaistRight1;
        {
            var waistPart1 = Block.box(14, 12, 1.6, 15, 14, 2.4);
            var waistPart2 = Block.box(13, 11, 1.6, 16, 12, 2.4);
            var waistPart3 = Block.box(4, 10, 1.6, 14, 11, 2.4);
            var waistPart4 = Block.box(11, 13, 1.6, 12, 14, 2.4);
            var waistPart5 = Block.box(7, 12, 1.6, 12, 13, 2.4);
            var waistPart6 = Block.box(10, 11, 1.6, 11, 12, 2.4);
            var waistPart7 = Block.box(8, 11, 1.6, 9, 12, 2.4);
            var waistPart8 = Block.box(7, 13, 1.6, 8, 14, 2.4);
            var waistPart9 = Block.box(5, 12, 1.6, 6, 13, 2.4);
            var waistPart10 = Block.box(4, 11, 1.6, 5, 13, 2.4);
            desktopWaistRight1 = new DeskPart("waist_right", 0, Shapes.or(waistPart1, waistPart2, waistPart3, waistPart4, waistPart5, waistPart6, waistPart7, waistPart8, waistPart9, waistPart10));
        }
        var desktopWaistRight2 = new DeskPart("waist_right", 90, BlockShapeHelper.rotateY(desktopWaistRight1.shape()));
        var desktopWaistRight3 = new DeskPart("waist_right", 180, BlockShapeHelper.rotateY(desktopWaistRight2.shape()));
        var desktopWaistRight4 = new DeskPart("waist_right", 270, BlockShapeHelper.rotateY(desktopWaistRight3.shape()));

        PARTS[0][0] = new DeskPart[] {top, leg1, desktopWaistNone1, leg2, desktopWaistNone2, leg3, desktopWaistNone3, leg4, desktopWaistNone4};
        PARTS[1][0] = new DeskPart[] {top, desktopWaistLeft1, leg2, desktopWaistNone2, leg3, desktopWaistRight3};
        PARTS[2][0] = new DeskPart[] {top, leg1, desktopWaistNone1, leg2, desktopWaistRight2, desktopWaistLeft4};
        PARTS[3][0] = new DeskPart[] {top, desktopWaistLeft1, leg2, desktopWaistRight2};
        PARTS[3][1] = new DeskPart[] {top, desktopWaistLeft1, leg2, desktopWaistRight2, conditionNotLeg4};
        PARTS[4][0] = new DeskPart[] {top, leg1, desktopWaistRight1, desktopWaistLeft3, leg4, desktopWaistNone4};
        PARTS[5][0] = new DeskPart[] {top, desktopWaistBoth1, desktopWaistBoth3};
        PARTS[6][0] = new DeskPart[] {top, leg1, desktopWaistRight1, desktopWaistLeft4};
        PARTS[6][1] = new DeskPart[] {top, leg1, desktopWaistRight1, desktopWaistLeft4, conditionNotLeg3};
        PARTS[7][0] = new DeskPart[] {top, desktopWaistBoth1, conditionNotLeg3, conditionNotLeg4};
        PARTS[7][1] = new DeskPart[] {top, desktopWaistBoth1, conditionNotLeg3};
        PARTS[7][2] = new DeskPart[] {top, desktopWaistBoth1, conditionNotLeg4};
        PARTS[7][3] = new DeskPart[] {top, desktopWaistBoth1};
        PARTS[8][0] = new DeskPart[] {top, desktopWaistLeft2, leg3, desktopWaistNone3, leg4, desktopWaistRight4};
        PARTS[9][0] = new DeskPart[] {top, desktopWaistLeft2, leg3, desktopWaistRight3};
        PARTS[9][1] = new DeskPart[] {top, desktopWaistLeft2, leg3, desktopWaistRight3, conditionNotLeg1};
        PARTS[10][0] = new DeskPart[] {top, desktopWaistBoth2, desktopWaistBoth4};
        PARTS[11][0] = new DeskPart[] {top, desktopWaistBoth2, conditionNotLeg1, conditionNotLeg4};
        PARTS[11][1] = new DeskPart[] {top, desktopWaistBoth2, conditionNotLeg1};
        PARTS[11][2] = new DeskPart[] {top, desktopWaistBoth2, conditionNotLeg4};
        PARTS[11][3] = new DeskPart[] {top, desktopWaistBoth2};
        PARTS[12][0] = new DeskPart[] {top, desktopWaistLeft3, leg4, desktopWaistRight4};
        PARTS[12][1] = new DeskPart[] {top, desktopWaistLeft3, leg4, desktopWaistRight4, conditionNotLeg2};
        PARTS[13][0] = new DeskPart[] {top, desktopWaistBoth3, conditionNotLeg1, conditionNotLeg2};
        PARTS[13][1] = new DeskPart[] {top, desktopWaistBoth3, conditionNotLeg1};
        PARTS[13][2] = new DeskPart[] {top, desktopWaistBoth3, conditionNotLeg2};
        PARTS[13][3] = new DeskPart[] {top, desktopWaistBoth3};
        PARTS[14][0] = new DeskPart[] {top, desktopWaistBoth4, conditionNotLeg2, conditionNotLeg3};
        PARTS[14][1] = new DeskPart[] {top, desktopWaistBoth4, conditionNotLeg2};
        PARTS[14][2] = new DeskPart[] {top, desktopWaistBoth4, conditionNotLeg3};
        PARTS[14][3] = new DeskPart[] {top, desktopWaistBoth4};
        PARTS[15][0] = new DeskPart[] {top, conditionNotLeg1, conditionNotLeg2, conditionNotLeg3, conditionNotLeg4};
        PARTS[15][1] = new DeskPart[] {top, conditionNotLeg1, conditionNotLeg2, conditionNotLeg3};
        PARTS[15][2] = new DeskPart[] {top, conditionNotLeg1, conditionNotLeg2, conditionNotLeg4};
        PARTS[15][3] = new DeskPart[] {top, conditionNotLeg1, conditionNotLeg2};
        PARTS[15][4] = new DeskPart[] {top, conditionNotLeg1, conditionNotLeg3, conditionNotLeg4};
        PARTS[15][5] = new DeskPart[] {top, conditionNotLeg1, conditionNotLeg3};
        PARTS[15][6] = new DeskPart[] {top, conditionNotLeg1, conditionNotLeg4};
        PARTS[15][7] = new DeskPart[] {top, conditionNotLeg1};
        PARTS[15][8] = new DeskPart[] {top, conditionNotLeg2, conditionNotLeg3, conditionNotLeg4};
        PARTS[15][9] = new DeskPart[] {top, conditionNotLeg2, conditionNotLeg3};
        PARTS[15][10] = new DeskPart[] {top, conditionNotLeg2, conditionNotLeg4};
        PARTS[15][11] = new DeskPart[] {top, conditionNotLeg2};
        PARTS[15][12] = new DeskPart[] {top, conditionNotLeg3, conditionNotLeg4};
        PARTS[15][13] = new DeskPart[] {top, conditionNotLeg3};
        PARTS[15][14] = new DeskPart[] {top, conditionNotLeg4};
        PARTS[15][15] = new DeskPart[] {top};

        for (var i = 0; i < PARTS.length; i++) {
            for (var j = 0; j < PARTS[i].length; j++) {
                if (PARTS[i][j] != null) {
                    SHAPES[i][j] = VoxelShapeHelper.combine(Arrays.stream(PARTS[i][j])
                            .filter(Objects::nonNull)
                            .map(DeskPart::shape)
                            .toArray(VoxelShape[]::new));
                }
            }
        }
    }

    public record DeskPart(String name, int rotate, VoxelShape shape, @Nullable BooleanProperty condition, boolean conditionValue) {
        public DeskPart(String name, int rotate, VoxelShape shape) {
            this(name, rotate, shape, null);
        }

        public DeskPart(String name, int rotate, VoxelShape shape, @Nullable BooleanProperty condition) {
            this(name, rotate, shape, condition, true);
        }
    }

    private int compressBool(boolean b1, boolean b2, boolean b3, boolean b4) {
        var result = b1 ? 1 : 0;
        result <<= 1;
        result += b2 ? 1 : 0;
        result <<= 1;
        result += b3 ? 1 : 0;
        result <<= 1;
        result += b4 ? 1 : 0;
        return result;
    }

    private int compressBool(boolean b1, boolean b2) {
        var result = b1 ? 1 : 0;
        result <<= 1;
        result += b2 ? 1 : 0;
        return result;
    }

    private int countBool(boolean b1, boolean b2, boolean b3, boolean b4) {
        var result = b1 ? 1 : 0;
        result += b2 ? 1 : 0;
        result += b3 ? 1 : 0;
        result += b4 ? 1 : 0;
        return result;
    }

    @Override
    public @NotNull VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        boolean north = state.getValue(NORTH);
        boolean east = state.getValue(EAST);
        boolean south = state.getValue(SOUTH);
        boolean west = state.getValue(WEST);
        boolean northWest = state.getValue(NORTH_WEST);
        boolean northEast = state.getValue(NORTH_EAST);
        boolean southEast = state.getValue(SOUTH_EAST);
        boolean southWest = state.getValue(SOUTH_WEST);

        var neighbor = compressBool(north, east, south, west);
        var extra = 0;

        var neighborCount = countBool(north, east, south, west);
        if (neighborCount == 4) {
            extra = compressBool(northWest, northEast, southEast, southWest);
        } else if (neighborCount == 3) {
            if (!north) {
                extra = compressBool(southEast, southWest);
            } else if (!east) {
                extra = compressBool(northWest, southWest);
            } else if (!south) {
                extra = compressBool(northWest, northEast);
            } else if (!west) {
                extra = compressBool(northEast, southEast);
            }
        }

        return SHAPES[neighbor][extra];
    }

    /// </editor-fold>

    public WoodenTableBlock() {
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
