package games.moegirl.sinocraft.sinodivination.old.block.base;

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

public class SimpleCropBlock<T extends Item> extends CropBlock implements Crop<T> {

    public static <T extends Item> SimpleCropBlock<T> withAge(Supplier<RegistryObject<T>> crop, int age, int minSeedCount, int maxSeedCount, int minCropCount, int maxCropCount) {
        if (age == 7) {
            return new SimpleCropBlock<>(crop, minSeedCount, maxSeedCount, minCropCount, maxCropCount);
        }
        final IntegerProperty ap = Crop.getAgeProperties(age);
        return new SimpleCropBlock<>(crop, minSeedCount, maxSeedCount, minCropCount, maxCropCount) {
            @Override
            public IntegerProperty getAgeProperty() {
                return ap;
            }

            @Override
            public int getMaxAge() {
                return age;
            }
        };
    }

    private final Supplier<T> crop;
    private final int maxSeedCount, minSeedCount;
    private final int maxCropCount, minCropCount;

    public SimpleCropBlock(Supplier<RegistryObject<T>> crop, int minSeedCount, int maxSeedCount, int minCropCount, int maxCropCount) {
        super(Properties.copy(Blocks.CARROTS));
        this.crop = Suppliers.memoize(() -> crop.get().get());
        this.minSeedCount = Math.min(minSeedCount, maxSeedCount);
        this.maxSeedCount = Math.max(minSeedCount, maxSeedCount);
        this.minCropCount = Math.min(maxCropCount, minCropCount);
        this.maxCropCount = Math.max(maxCropCount, minCropCount);
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder) {
        pBuilder.add(getAgeProperty());
    }

    @Override
    public ItemLike getBaseSeedId() {
        return asItem();
    }

    @Override
    public int getAge(BlockState pState) {
        return SimpleCropBlock.super.getAge(pState);
    }

    @Override
    public int getBonemealAgeIncrease(Level pLevel) {
        return SimpleCropBlock.super.getBonemealAgeIncrease(pLevel);
    }

    @Override
    public T getCrop() {
        return crop.get();
    }

    @Override
    public void growCrops(Level pLevel, BlockPos pPos, BlockState pState, int growAge) {
        int age = Math.min(getAge(pState) + growAge, getMaxAge());
        pLevel.setBlock(pPos, this.getStateForAge(age), 2);
    }

    @Override
    public LootTable.Builder createLootBuilder(BlockLootables helper) {
        return createLootBuilder(helper, minSeedCount, maxSeedCount, minCropCount, maxCropCount);
    }
}
