package games.moegirl.sinocraft.sinocore.block;

import com.google.common.base.Suppliers;
import games.moegirl.sinocraft.sinocore.utility.BlockLootables;
import net.minecraft.core.BlockPos;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.CropBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Supplier;

/**
 * 简单的作物方块，可自定义阶段数，种子和作物
 *
 * @param <T> 成熟后作物的物品
 */
public class SimpleCropBlock<T extends Item> extends CropBlock implements Crop<T> {

    private boolean isObjectCreated = false;
    private final Supplier<T> crop;
    private final IntegerProperty ageProp;
    private final int maxSeedCount, minSeedCount;
    private final int maxCropCount, minCropCount;

    /**
     * 创建作物方块
     *
     * @param crop         作物物品
     * @param age          成熟阶段数
     * @param minSeedCount 成熟后额外掉落的最大种子数
     * @param maxSeedCount 成熟后额外掉落的最小种子数
     * @param minCropCount 成熟后掉落的最大作物数
     * @param maxCropCount 成熟后掉落的最小作物数
     */
    public SimpleCropBlock(Supplier<RegistryObject<T>> crop, int age, int minSeedCount, int maxSeedCount, int minCropCount, int maxCropCount) {
        super(Properties.copy(Blocks.CARROTS));
        isObjectCreated = false;
        this.crop = Suppliers.memoize(() -> crop.get().get());
        this.minSeedCount = Math.min(minSeedCount, maxSeedCount);
        this.maxSeedCount = Math.max(minSeedCount, maxSeedCount);
        this.minCropCount = Math.min(maxCropCount, minCropCount);
        this.maxCropCount = Math.max(maxCropCount, minCropCount);
        this.ageProp = Crop.getAgeProperties(age);
        isObjectCreated = true;
        
        StateDefinition.Builder<Block, BlockState> builder = new StateDefinition.Builder<>(this);
        createBlockStateDefinition(builder);
        stateDefinition = builder.create(Block::defaultBlockState, BlockState::new);
        registerDefaultState(stateDefinition.any());
    }

    /**
     * 创建作物方块，适用于种子与作物物品重合的情况
     *
     * @param age     成熟阶段数
     * @param minCrop 最少掉落数量
     * @param maxCrop 最多掉落数量
     */
    public SimpleCropBlock(int age, int minCrop, int maxCrop) {
        super(Properties.copy(Blocks.CARROTS));
        isObjectCreated = false;
        this.crop = Suppliers.memoize(() -> (T) this.getBaseSeedId().asItem());
        this.minSeedCount = 0;
        this.maxSeedCount = 0;
        this.minCropCount = Math.min(minCrop, maxCrop);
        this.maxCropCount = Math.max(minCrop, maxCrop);
        this.ageProp = Crop.getAgeProperties(age);
        isObjectCreated = true;

        StateDefinition.Builder<Block, BlockState> builder = new StateDefinition.Builder<>(this);
        createBlockStateDefinition(builder);
        stateDefinition = builder.create(Block::defaultBlockState, BlockState::new);
        registerDefaultState(stateDefinition.any());
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder) {
        pBuilder.add(getAgeProperty());
    }

    @Override
    public ItemLike getBaseSeedId() {
        return Crop.super.getBaseSeedId();
    }

    @Override
    public int getAge(BlockState state) {
        return Crop.super.getAge(state);
    }

    @Override
    public int getMaxAge() {
        return isObjectCreated ? ageProp.max : 7;
    }

    @Override
    public int getBonemealAgeIncrease(Level level) {
        return Crop.super.getBonemealAgeIncrease(level);
    }

    @Override
    public T getCrop() {
        return crop.get();
    }

    @Override
    public IntegerProperty getAgeProperty() {
        return isObjectCreated ? ageProp : AGE;
    }

    @Override
    public void growCrops(Level level, BlockPos pos, BlockState state, int growAge) {
        int age = Math.min(getAge(state) + growAge, getMaxAge());
        level.setBlock(pos, this.getStateForAge(age), 2);
    }

    @Override
    public LootTable.Builder createLootBuilder(BlockLootables helper) {
        return createLootBuilder(helper, minSeedCount, maxSeedCount, minCropCount, maxCropCount);
    }
}
