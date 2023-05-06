package games.moegirl.sinocraft.sinodivination.old.block.base;

import games.moegirl.sinocraft.sinocore.block.ILootableBlock;
import games.moegirl.sinocraft.sinocore.utility.BlockLootables;
import games.moegirl.sinocraft.sinocore.utility.Self;
import games.moegirl.sinocraft.sinodivination.old.util.Lootables;
import it.unimi.dsi.fastutil.ints.Int2ObjectAVLTreeMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.BonemealableBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraftforge.common.IPlantable;

import java.util.Random;

public interface Crop<T extends Item> extends BonemealableBlock, ILootableBlock, IPlantable, Self<Block> {

    Int2ObjectMap<IntegerProperty> AGES = new Int2ObjectAVLTreeMap<>(new int[]{1, 2, 3, 5, 15, 25},
            new IntegerProperty[]{BlockStateProperties.AGE_1, BlockStateProperties.AGE_2,
                    BlockStateProperties.AGE_3, BlockStateProperties.AGE_5,
                    BlockStateProperties.AGE_15, BlockStateProperties.AGE_25});

    static IntegerProperty getAgeProperties(int age) {
        return AGES.computeIfAbsent(age, i -> IntegerProperty.create("age", 0, i));
    }

    default ItemLike getBaseSeedId() {
        return self();
    }

    T getCrop();

    default ItemStack getCloneItemStack(BlockGetter pLevel, BlockPos pPos, BlockState pState) {
        return new ItemStack(getBaseSeedId());
    }

    IntegerProperty getAgeProperty();

    int getMaxAge();

    default int getAge(BlockState pState) {
        return pState.getValue(getAgeProperty());
    }

    default BlockState getStateForAge(BlockState pState, int pAge) {
        return pState.setValue(getAgeProperty(), pAge);
    }

    default BlockState getStateForAge(int pAge) {
        return getStateForAge(self().defaultBlockState(), pAge);
    }

    default boolean isMaxAge(BlockState pState) {
        return getAge(pState) >= getMaxAge();
    }

    void growCrops(Level pLevel, BlockPos pPos, BlockState pState, int growAge);

    default void growCrops(Level pLevel, BlockPos pPos, BlockState pState, boolean useBonemeal) {
        growCrops(pLevel, pPos, pState, useBonemeal ? getBonemealAgeIncrease(pLevel) : 1);
    }

    default int getBonemealAgeIncrease(Level pLevel) {
        return Mth.nextInt(pLevel.random, 2, 5);
    }

    default float getGrowthSpeed(BlockGetter pLevel, BlockPos pPos) {
        float f = 1.0F;
        BlockPos blockpos = pPos.below();

        for (int i = -1; i <= 1; ++i) {
            for (int j = -1; j <= 1; ++j) {
                float f1 = 0.0F;
                BlockState state = pLevel.getBlockState(blockpos.offset(i, 0, j));
                if (state.canSustainPlant(pLevel, blockpos.offset(i, 0, j), Direction.UP, this)) {
                    f1 = 1.0F;
                    if (state.isFertile(pLevel, pPos.offset(i, 0, j))) {
                        f1 = 3.0F;
                    }
                }

                if (i != 0 || j != 0) {
                    f1 /= 4.0F;
                }

                f += f1;
            }
        }

        BlockPos north = pPos.north();
        BlockPos south = pPos.south();
        BlockPos west = pPos.west();
        BlockPos east = pPos.east();
        boolean xIs = pLevel.getBlockState(west).is(self()) || pLevel.getBlockState(east).is(self());
        boolean zIs = pLevel.getBlockState(north).is(self()) || pLevel.getBlockState(south).is(self());
        if (xIs && zIs) {
            f /= 2.0F;
        } else {
            boolean near = pLevel.getBlockState(west.north()).is(self())
                    || pLevel.getBlockState(east.north()).is(self())
                    || pLevel.getBlockState(east.south()).is(self())
                    || pLevel.getBlockState(west.south()).is(self());
            if (near) {
                f /= 2.0F;
            }
        }

        return f;
    }

    @Override
    default boolean isValidBonemealTarget(LevelReader pLevel, BlockPos pPos, BlockState pState, boolean pIsClient) {
        return isMaxAge(pState);
    }

    @Override
    default boolean isBonemealSuccess(Level pLevel, RandomSource pRandom, BlockPos pPos, BlockState pState) {
        return true;
    }

    @Override
    default void performBonemeal(ServerLevel pLevel, RandomSource pRandom, BlockPos pPos, BlockState pState) {
        growCrops(pLevel, pPos, pState, true);
    }

    default LootTable.Builder createLootBuilder(BlockLootables helper, int minSeedCount, int maxSeedCount, int minCropCount, int maxCropCount) {
        return helper.applyExplosionDecay(false, LootTable.lootTable()
                .withPool(LootPool.lootPool().add(Lootables.item(getBaseSeedId())))
                .withPool(LootPool.lootPool().when(Lootables.isGrown(this))
                        .add(Lootables.item(getBaseSeedId(), minSeedCount, maxSeedCount))
                        .add(Lootables.item(getCrop(), minCropCount, maxCropCount))));
    }
}
