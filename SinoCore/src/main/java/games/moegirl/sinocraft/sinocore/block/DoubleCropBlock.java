package games.moegirl.sinocraft.sinocore.block;

import games.moegirl.sinocraft.sinocore.utility.BlockLootables;
import net.minecraft.advancements.critereon.StatePropertiesPredicate;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.DoublePlantBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.DoubleBlockHalf;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.predicates.LootItemBlockStatePropertyCondition;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.common.ForgeHooks;
import net.minecraftforge.registries.RegistryObject;

/**
 * 两格高的作物，带有生长属性的两格高植物
 *
 * @param <T> 作物成熟物品
 */
public abstract class DoubleCropBlock<T extends Item> extends DoublePlantBlock implements Crop<T> {

    private final RegistryObject<T> crop;
    private final int minSeedCount, maxSeedCount, minCropCount, maxCropCount;
    private final IntegerProperty ageProperty;
    private final int maxAge;

    protected final StateDefinition<Block, BlockState> stateDefinition;

    /**
     * @param properties   方块属性
     * @param crop         作物
     * @param age          最大生长阶段
     * @param minSeedCount 成熟后额外掉落的最大种子数
     * @param maxSeedCount 成熟后额外掉落的最小种子数
     * @param minCropCount 成熟后掉落的最大作物数
     * @param maxCropCount 成熟后掉落的最小作物数
     */
    public DoubleCropBlock(Properties properties, RegistryObject<T> crop, int age,
                           int minSeedCount, int maxSeedCount, int minCropCount, int maxCropCount) {
        super(properties.offsetType(OffsetType.NONE));
        this.crop = crop;
        this.minSeedCount = minSeedCount;
        this.maxSeedCount = maxSeedCount;
        this.minCropCount = minCropCount;
        this.maxCropCount = maxCropCount;
        this.maxAge = age;
        this.ageProperty = Crop.getAgeProperties(age);

        StateDefinition.Builder<Block, BlockState> builder = new StateDefinition.Builder<>(this);
        builder.add(HALF);
        builder.add(getAgeProperty());
        this.stateDefinition = builder.create(Block::defaultBlockState, BlockState::new);
        registerDefaultState(stateDefinition.any());

        registerDefaultState(defaultBlockState()
                .setValue(HALF, DoubleBlockHalf.LOWER)
                .setValue(ageProperty, 0));
    }

    @Override
    public T getCrop() {
        return crop.get();
    }

    @Override
    public int getMaxAge() {
        return maxAge;
    }

    @Override
    public IntegerProperty getAgeProperty() {
        return ageProperty;
    }

    @Override
    public StateDefinition<Block, BlockState> getStateDefinition() {
        return stateDefinition;
    }

    @Override
    public boolean canSurvive(BlockState state, LevelReader level, BlockPos pos) {
        if (state.getValue(HALF) == DoubleBlockHalf.LOWER) {
            return canSurviveLower(state, level, pos);
        } else {
            BlockState below = level.getBlockState(pos.below());
            return state.getBlock() == this
                    ? below.is(this) && state.getValue(HALF) == DoubleBlockHalf.LOWER
                    //Forge: This function is called during world gen and placement,
                    // before this block is set, so if we are not 'here' then assume it's the pre-check.
                    : super.canSurvive(state, level, pos);
        }
    }

    @Override
    public boolean isRandomlyTicking(BlockState state) {
        // 只有底部方块且没有到达最大值时更新
        return state.getValue(HALF) == DoubleBlockHalf.LOWER && !isMaxAge(state);
    }

    @Override
    @Deprecated
    @SuppressWarnings("deprecation")
    public void randomTick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
        if (state.getValue(HALF) != DoubleBlockHalf.LOWER || !level.isAreaLoaded(pos, 1))
            return; // Forge: prevent loading unloaded chunks when checking neighbor's light
        if (!isMaxAge(state) && level.getRawBrightness(pos, 0) >= 9) {
            float f = getGrowthSpeed(level, pos);
            if (ForgeHooks.onCropsGrowPre(level, pos, state, random.nextInt((int) (25.0F / f) + 1) == 0)) {
                growCrops(level, pos, state, false);
                ForgeHooks.onCropsGrowPost(level, pos, state);
            }
        }
    }

    @Override
    public void growCrops(Level level, BlockPos pos, BlockState state, int growAge) {
        int age = Math.min(getMaxAge(), getAge(state) + growAge);
        level.setBlock(pos, getStateForAge(state, age), 2);
        // 更新顶部方块
        BlockPos above = pos.above();
        level.setBlock(above, getStateForAge(level.getBlockState(above), age), 2);
    }

    @Override
    public LootTable.Builder createLootBuilder(BlockLootables helper) {
        return createLootBuilder(helper, minSeedCount, maxSeedCount, minCropCount, maxCropCount);
    }

    @Override
    public LootTable.Builder createLootBuilder(BlockLootables helper,
                                               int minSeedCount, int maxSeedCount, int minCropCount, int maxCropCount) {
        // HALF - LOWER
        var isLower = LootItemBlockStatePropertyCondition.hasBlockStateProperties(this)
                .setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(HALF, DoubleBlockHalf.LOWER));
        // HALF - LOWER
        // AGE - MAX
        var isGrown = LootItemBlockStatePropertyCondition.hasBlockStateProperties(this)
                .setProperties(StatePropertiesPredicate.Builder.properties()
                        .hasProperty(HALF, DoubleBlockHalf.LOWER)
                        .hasProperty(getAgeProperty(), getMaxAge()));
        var rangeSeeds = UniformGenerator.between(minSeedCount, maxSeedCount);
        var rangeCrops = UniformGenerator.between(minCropCount, maxCropCount);
        var seeds = LootItem.lootTableItem(getBaseSeedId()).apply(SetItemCountFunction.setCount(rangeSeeds));
        var crops = LootItem.lootTableItem(getCrop()).apply(SetItemCountFunction.setCount(rangeCrops));
        return helper.applyExplosionDecay(false, LootTable.lootTable()
                // 不管怎样，至少掉一个种子
                .withPool(LootPool.lootPool().when(isLower).add(LootItem.lootTableItem(getBaseSeedId())))
                // 当成熟时额外掉落物品和种子
                .withPool(LootPool.lootPool().when(isGrown).add(seeds).add(crops)));
    }

    @Override
    @SuppressWarnings("deprecation")
    public abstract VoxelShape getShape(BlockState pState, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext);

    @Override
    protected abstract boolean mayPlaceOn(BlockState pState, BlockGetter pLevel, BlockPos pPos);

    public abstract boolean canSurviveLower(BlockState pState, LevelReader pLevel, BlockPos pPos);
}
