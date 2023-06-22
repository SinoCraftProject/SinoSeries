package games.moegirl.sinocraft.sinofoundation.block.plant;

import games.moegirl.sinocraft.sinocore.utility.RoundChecker;
import games.moegirl.sinocraft.sinocore.utility.StreamUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;
import java.util.EnumMap;

/**
 * 灵芝
 */
@SuppressWarnings("deprecation")
public class LucidGanoderma extends Block {

    public static final DirectionProperty LOG_FACING = BlockStateProperties.FACING;
    public static final EnumMap<Direction, VoxelShape> SHAPES = new EnumMap<>(Direction.class);
    public static final int MAX_LIGHT = 5;

    static {
        SHAPES.put(Direction.UP, box(0, 4, 0, 16, 16, 16));
        SHAPES.put(Direction.DOWN, box(0, 0, 0, 16, 12, 16));
        SHAPES.put(Direction.EAST, box(4, 0, 0, 16, 16, 16));
        SHAPES.put(Direction.WEST, box(0, 0, 0, 12, 16, 16));
        SHAPES.put(Direction.SOUTH, box(0, 4, 0, 16, 16, 16));
        SHAPES.put(Direction.NORTH, box(0, 0, 0, 16, 12, 16));
    }

    public LucidGanoderma() {
        super(Properties.copy(Blocks.BROWN_MUSHROOM));
        registerDefaultState(getStateDefinition().any().setValue(LOG_FACING, Direction.UP));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder);
        builder.add(LOG_FACING);
    }

    @Override
    public VoxelShape getShape(BlockState pState, BlockGetter level, BlockPos pos, CollisionContext pContext) {
        return SHAPES.get(pState.getValue(LOG_FACING));
    }

    @Override
    public boolean propagatesSkylightDown(BlockState pState, BlockGetter level, BlockPos pos) {
        return true;
    }

    @Override
    public boolean canSurvive(BlockState blockState, LevelReader level, BlockPos pos) {
        // 位于原木侧面
        return level.getBlockState(pos.relative(blockState.getValue(LOG_FACING))).is(BlockTags.LOGS)
                // 亮度限制
                && level.getRawBrightness(pos, 0) <= MAX_LIGHT;
    }

    @Override
    public void neighborChanged(BlockState blockState, Level level, BlockPos pos, Block block, BlockPos fromPos, boolean isMoving) {
        super.neighborChanged(blockState, level, pos, block, fromPos, isMoving);
        if (!canSurvive(blockState, level, pos)) {
            // 掉落物品
            dropResources(blockState, level, pos);
            level.removeBlock(pos, false);
        }
    }

    @Override
    public void randomTick(BlockState blockState, ServerLevel level, BlockPos pos, RandomSource random) {
        // 10%
        if (level.random.nextInt(10) == 0
                // 区块已加载
                && level.isAreaLoaded(pos, 2)
                // 亮度限制
                && level.getRawBrightness(pos, 0) <= MAX_LIGHT) {
            RoundChecker checker = new RoundChecker(level, pos);
            Arrays.stream(Direction.values())
                    .filter(d -> checker.dark(d, MAX_LIGHT))
                    .filter(d -> checker.near(d, BlockTags.LOGS))
                    .filter(checker::isReplaceable)
                    .collect(StreamUtils.randomStream(random))
                    .peek(checker::move)
                    .findFirst()
                    .flatMap(__ -> Arrays.stream(Direction.values())
                            .filter(d -> checker.is(d, BlockTags.LOGS))
                            .collect(StreamUtils.random(random)))
                    .ifPresent(d -> level.setBlock(checker.here(), defaultBlockState().setValue(LOG_FACING, d), 3));
        }
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        RoundChecker checker = new RoundChecker(context.getLevel(), context.getClickedPos());
        return Arrays.stream(Direction.values())
                .filter(d -> checker.is(d, BlockTags.LOGS))
                .collect(StreamUtils.random(context.getLevel().random))
                .map(d -> defaultBlockState().setValue(LOG_FACING, d))
                .orElse(null);
    }
}
