package games.moegirl.sinocraft.sinocore.block;

import games.moegirl.sinocraft.sinocore.utility.BlockLootables;
import games.moegirl.sinocraft.sinocore.utility.Self;
import it.unimi.dsi.fastutil.ints.Int2ObjectAVLTreeMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import net.minecraft.advancements.critereon.StatePropertiesPredicate;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.BonemealableBlock;
import net.minecraft.world.level.block.BushBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.predicates.LootItemBlockStatePropertyCondition;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;

/**
 * 作物方块的通用接口，带有部分 CropBlock 类的方法
 *
 * <ul>
 *     <li>{@link SimpleCropBlock} 继承自 {@link net.minecraft.world.level.block.CropBlock}</li>
 *     <li>{@link DoubleCropBlock} 继承自 {@link net.minecraft.world.level.block.DoublePlantBlock}</li>
 * </ul>
 *
 * @param <T> 作物物品
 */
public interface Crop<T extends Item> extends BonemealableBlock, ILootableBlock, Self<BushBlock> {

    Int2ObjectMap<IntegerProperty> AGES = new Int2ObjectAVLTreeMap<>(new int[]{1, 2, 3, 4, 5, 7, 15, 25},
            new IntegerProperty[]{BlockStateProperties.AGE_1, BlockStateProperties.AGE_2,
                    BlockStateProperties.AGE_3, BlockStateProperties.AGE_4,
                    BlockStateProperties.AGE_5, BlockStateProperties.AGE_7,
                    BlockStateProperties.AGE_15, BlockStateProperties.AGE_25});

    static IntegerProperty getAgeProperties(int age) {
        return AGES.computeIfAbsent(age, i -> IntegerProperty.create("age", 0, i));
    }

    /**
     * 获取种子
     *
     * @return 种子
     */
    default ItemLike getBaseSeedId() {
        return self().asItem();
    }

    /**
     * 获取作物
     *
     * @return 作物
     */
    T getCrop();

    // 作物生长阶段相关 ===================================================================================================

    /**
     * 获取用于判断作物成长阶段的属性
     *
     * @return 属性
     */
    IntegerProperty getAgeProperty();

    /**
     * 获取作物生长阶段
     *
     * @param state 作物方块
     * @return 生长阶段
     */
    default int getAge(BlockState state) {
        return state.getValue(getAgeProperty());
    }

    /**
     * 获取成熟时作物生长阶段
     *
     * @return 作物最大阶段
     */
    default int getMaxAge() {
        return getAgeProperty().max;
    }

    /**
     * 判断给定作物是否已经成熟
     *
     * @param state 作物
     * @return 是否成熟
     */
    default boolean isMaxAge(BlockState state) {
        return getAge(state) >= getMaxAge();
    }

    /**
     * 更新作物生长状态
     *
     * @param state 旧状态
     * @param age   新阶段
     * @return 新状态
     */
    default BlockState getStateForAge(BlockState state, int age) {
        return state.setValue(getAgeProperty(), age);
    }

    // 作物生长相关 ======================================================================================================

    /**
     * 给定具体成长值的作物成长
     *
     * @param level   世界
     * @param pos     位置
     * @param state   作物
     * @param growAge 阶段数
     */
    void growCrops(Level level, BlockPos pos, BlockState state, int growAge);

    /**
     * 普通的作物成长，包括自然成长和骨粉催化
     *
     * @param level       世界
     * @param pos         位置
     * @param state       作物
     * @param useBonemeal 是否使用骨粉
     */
    default void growCrops(Level level, BlockPos pos, BlockState state, boolean useBonemeal) {
        growCrops(level, pos, state, useBonemeal ? getBonemealAgeIncrease(level) : 1);
    }

    /**
     * 获取作物成长速度，与地下土地和周围作物都有关系
     *
     * @param level 世界
     * @param pos   作物位置
     * @return 成长速度，范围为 [0, 6]
     */
    default float getGrowthSpeed(BlockGetter level, BlockPos pos) {
        float speed = 1.0F;
        BlockPos farm = pos.below();

        // 计算基础速度
        for (int i = -1; i <= 1; ++i) {
            for (int j = -1; j <= 1; ++j) {
                float base = 0.0F;
                BlockState state = level.getBlockState(farm.offset(i, 0, j));

                // 基础速度：普通 1.0，沃土 3.0
                if (state.canSustainPlant(level, farm.offset(i, 0, j), Direction.UP, self())) {
                    base = 1.0F;
                    if (state.isFertile(level, pos.offset(i, 0, j))) {
                        base = 3.0F;
                    }
                }

                // 若位置为临近四个位置，临近四个位置都可以提供 1/4 的速度加成
                if (i != 0 || j != 0) {
                    base /= 4.0F;
                }

                speed += base;
            }
        }

        // 速度衰减
        BlockPos north = pos.north();
        BlockPos south = pos.south();
        BlockPos west = pos.west();
        BlockPos east = pos.east();
        boolean xIs = level.getBlockState(west).is(self()) || level.getBlockState(east).is(self());
        boolean zIs = level.getBlockState(north).is(self()) || level.getBlockState(south).is(self());
        if (xIs && zIs) {
            // 左右、前后各有至少一个临近作物 - 速度减半
            speed /= 2.0F;
        } else {
            // 对角线四个至少有一个作物 - 速度减半
            boolean near = level.getBlockState(west.north()).is(self())
                    || level.getBlockState(east.north()).is(self())
                    || level.getBlockState(east.south()).is(self())
                    || level.getBlockState(west.south()).is(self());
            if (near) {
                speed /= 2.0F;
            }
        }

        return speed;
    }

    // BonemealableBlock 骨粉 ===========================================================================================

    @Override
    default boolean isValidBonemealTarget(LevelReader pLevel, BlockPos pPos, BlockState pState, boolean pIsClient) {
        return !isMaxAge(pState);
    }

    @Override
    default boolean isBonemealSuccess(Level pLevel, RandomSource pRandom, BlockPos pPos, BlockState pState) {
        return true;
    }

    @Override
    default void performBonemeal(ServerLevel pLevel, RandomSource pRandom, BlockPos pPos, BlockState pState) {
        growCrops(pLevel, pPos, pState, true);
    }

    /**
     * 使用骨粉时，每次骨粉生效时可升级的成长阶段数
     *
     * @param level 世界
     * @return 阶段数
     */
    default int getBonemealAgeIncrease(Level level) {
        return getMaxAge() == 3 // age=3: [0, 2]
                ? Mth.nextInt(level.random, 0, 2)
                : Mth.nextInt(level.random, 2, 5);
    }

    // ILootableBlock 掉落物 ============================================================================================

    default LootTable.Builder createLootBuilder(BlockLootables helper,
                                                int minSeedCount, int maxSeedCount,
                                                int minCropCount, int maxCropCount) {
        var isGrown = LootItemBlockStatePropertyCondition.hasBlockStateProperties(self())
                .setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(getAgeProperty(), getMaxAge()));
        var rangeSeeds = UniformGenerator.between(minSeedCount, maxSeedCount);
        var rangeCrops = UniformGenerator.between(minCropCount, maxCropCount);
        var seeds = LootItem.lootTableItem(getBaseSeedId()).apply(SetItemCountFunction.setCount(rangeSeeds));
        var crops = LootItem.lootTableItem(getCrop()).apply(SetItemCountFunction.setCount(rangeCrops));
        return helper.applyExplosionDecay(false, LootTable.lootTable()
                // 不管怎样，至少掉一个种子
                .withPool(LootPool.lootPool().add(LootItem.lootTableItem(getBaseSeedId())))
                // 当成熟时额外掉落物品和种子
                .withPool(LootPool.lootPool().when(isGrown).add(seeds).add(crops)));
    }
}
