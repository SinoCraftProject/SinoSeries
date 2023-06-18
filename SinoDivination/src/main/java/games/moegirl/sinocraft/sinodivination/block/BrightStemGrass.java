package games.moegirl.sinocraft.sinodivination.block;

import games.moegirl.sinocraft.sinocore.block.ILootableBlock;
import games.moegirl.sinocraft.sinocore.utility.BlockLootables;
import games.moegirl.sinocraft.sinocore.utility.RoundChecker;
import games.moegirl.sinocraft.sinocore.utility.StreamUtils;
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

/**
 * 明茎草
 */
@SuppressWarnings("deprecation")
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
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder);
        builder.add(STONE);
    }

    @Override
    public VoxelShape getShape(BlockState blockState, BlockGetter level, BlockPos pos, CollisionContext context) {
        return SHAPES.get(blockState.getValue(STONE));
    }

    @Override
    public boolean propagatesSkylightDown(BlockState blockState, BlockGetter level, BlockPos pos) {
        return true;
    }

    @Override
    public boolean canSurvive(BlockState blockState, LevelReader level, BlockPos pos) {
        return level.getBlockState(pos.relative(blockState.getValue(STONE))).is(Tags.Blocks.STONE);
    }

    @Override
    public void neighborChanged(BlockState blockState, Level level, BlockPos pos, Block block, BlockPos fromPos, boolean isMoving) {
        super.neighborChanged(blockState, level, pos, block, fromPos, isMoving);
        if (!level.getBlockState(pos.relative(blockState.getValue(STONE))).is(Tags.Blocks.STONE)) {
            level.removeBlock(pos, false);
        }
    }

    @Override
    public void randomTick(BlockState blockState, ServerLevel level, BlockPos pos, RandomSource random) {
        if (level.random.nextInt(8) == 0 && level.isAreaLoaded(pos, 2)) {
            RoundChecker checker = new RoundChecker(level, pos);
            if (checker.isReplaceable(Direction.DOWN)) {
                checker.move(Direction.DOWN);
                Direction d = blockState.getValue(STONE);
                if (checker.is(d, Tags.Blocks.STONE)) {
                    level.setBlock(checker.here(), defaultBlockState().setValue(STONE, d), 3);
                }
            }
        }
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        RoundChecker checker = new RoundChecker(context.getLevel(), context.getClickedPos());
        return Arrays.stream(Direction.values())
                .filter(d -> checker.is(d, Tags.Blocks.STONE))
                .collect(StreamUtils.random(context.getLevel().random))
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
                // 剪刀 or 精准采集
                .when(BlockLootables.HAS_SHEARS_OR_SILK_TOUCH)
                .add(LootUtils.item(this)));
    }
}
