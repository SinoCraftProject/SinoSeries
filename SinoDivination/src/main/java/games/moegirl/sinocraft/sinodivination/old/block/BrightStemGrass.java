package games.moegirl.sinocraft.sinodivination.old.block;

import games.moegirl.sinocraft.sinocore.block.ILootableBlock;
import games.moegirl.sinocraft.sinocore.utility.BlockLootables;
import games.moegirl.sinocraft.sinocore.utility.RoundChecker;
import games.moegirl.sinocraft.sinocore.utility.StreamUtils;
import games.moegirl.sinocraft.sinodivination.old.util.Lootables;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
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
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.common.IForgeShearable;
import net.minecraftforge.common.Tags;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;
import java.util.EnumMap;
import java.util.List;
import java.util.Random;

public class BrightStemGrass extends Block implements IForgeShearable, ILootableBlock {

    public static final DirectionProperty STONE = BlockStateProperties.FACING;
    public static final EnumMap<Direction, VoxelShape> SHAPES = new EnumMap<>(Direction.class);

    static {
        // todo by lq2007: shape VINE: box(1.0D, 0.0D, 1.0D, 15.0D, 16.0D, 15.0D);
        SHAPES.put(Direction.UP, Shapes.block());
        SHAPES.put(Direction.DOWN, Shapes.block());
        SHAPES.put(Direction.EAST, Shapes.block());
        SHAPES.put(Direction.WEST, Shapes.block());
        SHAPES.put(Direction.NORTH, Shapes.block());
        SHAPES.put(Direction.SOUTH, Shapes.block());
    }

    public BrightStemGrass() {
        super(Properties.copy(Blocks.CAVE_VINES).lightLevel(bs -> 11));
        registerDefaultState(getStateDefinition().any().setValue(STONE, Direction.UP));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder) {
        super.createBlockStateDefinition(pBuilder);
        pBuilder.add(STONE);
    }

    @Override
    public VoxelShape getShape(BlockState pState, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext) {
        return SHAPES.get(pState.getValue(STONE));
    }

    @Override
    public boolean propagatesSkylightDown(BlockState pState, BlockGetter pLevel, BlockPos pPos) {
        return true;
    }

    @Override
    public boolean canSurvive(BlockState pState, LevelReader pLevel, BlockPos pPos) {
        return pLevel.getBlockState(pPos.relative(pState.getValue(STONE))).is(Tags.Blocks.STONE);
    }

    @Override
    public void neighborChanged(BlockState pState, Level pLevel, BlockPos pPos, Block pBlock, BlockPos pFromPos, boolean pIsMoving) {
        super.neighborChanged(pState, pLevel, pPos, pBlock, pFromPos, pIsMoving);
        if (!pLevel.getBlockState(pPos.relative(pState.getValue(STONE))).is(Tags.Blocks.STONE)) {
            pLevel.removeBlock(pPos, false);
        }
    }

    @Override
    public void randomTick(BlockState pState, ServerLevel pLevel, BlockPos pPos, RandomSource pRandom) {
        if (pLevel.random.nextInt(8) == 0 && pLevel.isAreaLoaded(pPos, 2)) {
            RoundChecker checker = new RoundChecker(pLevel, pPos);
            if (checker.isReplaceable(Direction.DOWN)) {
                checker.move(Direction.DOWN);
                Direction d = pState.getValue(STONE);
                if (checker.is(d, Tags.Blocks.STONE)) {
                    pLevel.setBlock(checker.here(), defaultBlockState().setValue(STONE, d), 3);
                }
            }
        }
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext pContext) {
        RoundChecker checker = new RoundChecker(pContext.getLevel(), pContext.getClickedPos());
        return Arrays.stream(Direction.values())
                .filter(d -> checker.is(d, Tags.Blocks.STONE))
                .collect(StreamUtils.random(pContext.getLevel().random))
                .map(d -> defaultBlockState().setValue(STONE, d))
                .orElse(null);
    }

    @NotNull
    @Override
    public List<ItemStack> onSheared(@Nullable Player player, @NotNull ItemStack item, Level level, BlockPos pos, int fortune) {
        return List.of(new ItemStack(this));
    }

    @Override
    public LootTable.Builder createLootBuilder(BlockLootables blockLootables) {
        return LootTable.lootTable().withPool(LootPool.lootPool()
                .when(BlockLootables.HAS_SHEARS_OR_SILK_TOUCH)
                .add(Lootables.item(this)));
    }
}
