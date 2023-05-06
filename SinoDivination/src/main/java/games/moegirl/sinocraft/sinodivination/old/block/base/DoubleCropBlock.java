package games.moegirl.sinocraft.sinodivination.old.block.base;

import games.moegirl.sinocraft.sinocore.utility.BlockLootables;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.DoublePlantBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.DoubleBlockHalf;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.common.ForgeHooks;
import net.minecraftforge.registries.RegistryObject;

import javax.annotation.Nullable;

public abstract class DoubleCropBlock<T extends Item> extends DoublePlantBlock implements Crop<T> {

    private final RegistryObject<T> crop;
    private final int minSeedCount, maxSeedCount, minCropCount, maxCropCount;
    @Nullable
    private IntegerProperty ageProperty = null;

    public DoubleCropBlock(Properties properties, RegistryObject<T> crop, int minSeedCount, int maxSeedCount, int minCropCount, int maxCropCount) {
        super(properties.offsetType(OffsetType.NONE));
        this.crop = crop;
        this.minSeedCount = minSeedCount;
        this.maxSeedCount = maxSeedCount;
        this.minCropCount = minCropCount;
        this.maxCropCount = maxCropCount;
        registerDefaultState(defaultBlockState()
                .setValue(HALF, DoubleBlockHalf.LOWER)
                .setValue(getAgeProperty(), 0));
    }

    public DoubleCropBlock(RegistryObject<T> crop, int minSeedCount, int maxSeedCount, int minCropCount, int maxCropCount) {
        this(Properties.copy(Blocks.CARROTS), crop, minSeedCount, maxSeedCount, minCropCount, maxCropCount);
    }

    public T getCrop() {
        return crop.get();
    }

    @Override
    public int getMaxAge() {
        return 7;
    }

    @Override
    public IntegerProperty getAgeProperty() {
        if (ageProperty == null) {
            ageProperty = Crop.getAgeProperties(getMaxAge());
        }
        return ageProperty;
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder) {
        super.createBlockStateDefinition(pBuilder);
        pBuilder.add(getAgeProperty());
    }

    @Override
    public boolean canSurvive(BlockState pState, LevelReader pLevel, BlockPos pPos) {
        if (pState.getValue(HALF) != DoubleBlockHalf.UPPER) {
            return canSurviveLower(pState, pLevel, pPos);
        } else {
            BlockState below = pLevel.getBlockState(pPos.below());
            //Forge: This function is called during world gen and placement, before this block is set, so if we are not 'here' then assume it's the pre-check.
            return pState.getBlock() != this
                    ? super.canSurvive(pState, pLevel, pPos)
                    : below.is(this) && getHalf(below) == DoubleBlockHalf.LOWER;
        }
    }

    @Override
    public boolean isRandomlyTicking(BlockState pState) {
        return getHalf(pState) == DoubleBlockHalf.LOWER && !isMaxAge(pState);
    }

    @Override
    public void randomTick(BlockState pState, ServerLevel pLevel, BlockPos pPos, RandomSource pRandom) {
        if (!pLevel.isAreaLoaded(pPos, 1))
            return; // Forge: prevent loading unloaded chunks when checking neighbor's light
        if (pLevel.getRawBrightness(pPos, 0) >= 9) {
            if (getAge(pState) < getMaxAge()) {
                float f = getGrowthSpeed(pLevel, pPos);
                if (ForgeHooks.onCropsGrowPre(pLevel, pPos, pState, pRandom.nextInt((int) (25.0F / f) + 1) == 0)) {
                    growCrops(pLevel, pPos, pState, false);
                    ForgeHooks.onCropsGrowPost(pLevel, pPos, pState);
                }
            }
        }
    }

    @Override
    public void growCrops(Level pLevel, BlockPos pPos, BlockState pState, int growAge) {
        int age = Math.min(getMaxAge(), getAge(pState) + growAge);
        pLevel.setBlock(pPos, getStateForAge(pState, age), 2);
        BlockPos above = pPos.above();
        pLevel.setBlock(above, getStateForAge(pLevel.getBlockState(above), age), 2);
    }

    @Override
    public LootTable.Builder createLootBuilder(BlockLootables helper) {
        return createLootBuilder(helper, minSeedCount, maxSeedCount, minCropCount, maxCropCount);
    }

    @Override
    public abstract VoxelShape getShape(BlockState pState, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext);

    @Override
    protected abstract boolean mayPlaceOn(BlockState pState, BlockGetter pLevel, BlockPos pPos);

    public abstract boolean canSurviveLower(BlockState pState, LevelReader pLevel, BlockPos pPos);

    public DoubleBlockHalf getHalf(BlockState state) {
        return state.getValue(HALF);
    }
}
