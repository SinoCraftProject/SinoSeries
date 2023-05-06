package games.moegirl.sinocraft.sinodivination.old.block;

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

public class LucidGanoderma extends Block {

    public static final DirectionProperty LOG_FACING = BlockStateProperties.FACING;
    public static final EnumMap<Direction, VoxelShape> SHAPES = new EnumMap<>(Direction.class);
    public static final int MAX_LIGHT = 10;

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
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder) {
        super.createBlockStateDefinition(pBuilder);
        pBuilder.add(LOG_FACING);
    }

    @Override
    public VoxelShape getShape(BlockState pState, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext) {
        return SHAPES.get(pState.getValue(LOG_FACING));
    }

    @Override
    public boolean propagatesSkylightDown(BlockState pState, BlockGetter pLevel, BlockPos pPos) {
        return true;
    }

    @Override
    public boolean canSurvive(BlockState pState, LevelReader pLevel, BlockPos pPos) {
        return pLevel.getBlockState(pPos.relative(pState.getValue(LOG_FACING))).is(BlockTags.LOGS)
                && pLevel.getRawBrightness(pPos, 0) < MAX_LIGHT;
    }

    @Override
    public void neighborChanged(BlockState pState, Level pLevel, BlockPos pPos, Block pBlock, BlockPos pFromPos, boolean pIsMoving) {
        super.neighborChanged(pState, pLevel, pPos, pBlock, pFromPos, pIsMoving);
        if (pLevel.getRawBrightness(pPos, 0) > 10 || !pLevel.getBlockState(pPos.relative(pState.getValue(LOG_FACING))).is(BlockTags.LOGS)) {
            dropResources(pState, pLevel, pPos);
            pLevel.removeBlock(pPos, false);
        }
    }

    @Override
    public void randomTick(BlockState pState, ServerLevel pLevel, BlockPos pPos, RandomSource pRandom) {
        if (pLevel.random.nextInt(10) == 0 && pLevel.isAreaLoaded(pPos, 2) && pLevel.getRawBrightness(pPos, 0) <= MAX_LIGHT) {
            RoundChecker checker = new RoundChecker(pLevel, pPos);
            Arrays.stream(Direction.values())
                    .filter(d -> checker.dark(d, MAX_LIGHT))
                    .filter(d -> checker.near(d, BlockTags.LOGS))
                    .filter(checker::isReplaceable)
                    .collect(StreamUtils.randomStream(pRandom))
                    .peek(checker::move)
                    .findFirst()
                    .flatMap(__ -> Arrays.stream(Direction.values())
                            .filter(d -> checker.is(d, BlockTags.LOGS))
                            .collect(StreamUtils.random(pRandom)))
                    .ifPresent(d -> pLevel.setBlock(checker.here(), defaultBlockState().setValue(LOG_FACING, d), 3));
        }
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext pContext) {
        RoundChecker checker = new RoundChecker(pContext.getLevel(), pContext.getClickedPos());
        return Arrays.stream(Direction.values())
                .filter(d -> checker.is(d, BlockTags.LOGS))
                .collect(StreamUtils.random(pContext.getLevel().random))
                .map(d -> defaultBlockState().setValue(LOG_FACING, d))
                .orElse(null);
    }
}
