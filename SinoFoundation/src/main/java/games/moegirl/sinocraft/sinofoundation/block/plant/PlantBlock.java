package games.moegirl.sinocraft.sinofoundation.block.plant;

import games.moegirl.sinocraft.sinocore.block.Crop;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.CropBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;

import java.util.function.Supplier;

public class PlantBlock extends CropBlock {
    protected static final VoxelShape[] SHAPES = new VoxelShape[]{
            Block.box(0, 0, 0, 16, 2, 16),
            Block.box(0, 0, 0, 16, 5, 16),
            Block.box(0, 0, 0, 16, 7, 16),
            Block.box(0, 0, 0, 16, 9, 16),
    };

    private final PlantType plantType;
    private final Supplier<? extends ItemLike> seed;
    private final IntegerProperty property;

    protected final StateDefinition<Block, BlockState> stateDefinition;

    public PlantBlock(PlantType plantType, Supplier<? extends ItemLike> seed) {
        super(Properties.copy(Blocks.POTATOES));

        this.plantType = plantType;
        this.seed = seed;
        this.property = Crop.getAgeProperties(plantType.getMaxAge());

        StateDefinition.Builder<Block, BlockState> builder = new StateDefinition.Builder<>(this);
        builder.add(getStageProperty());
        this.stateDefinition = builder.create(Block::defaultBlockState, BlockState::new);
        registerDefaultState(stateDefinition.any());
    }

    public PlantType getPlantType() {
        return plantType;
    }

    public @NotNull IntegerProperty getStageProperty() {
        return property;
    }

    @Override
    public IntegerProperty getAgeProperty() {
        return plantType == null ? AGE : property;
    }

    @Override
    protected @NotNull ItemLike getBaseSeedId() {
        return seed.get();
    }

    @Override
    public int getAge(BlockState state) {
        return state.getValue(getStageProperty());
    }

    @Override
    public int getMaxAge() {
        return plantType.getMaxAge();
    }

    @Override
    public BlockState getStateForAge(int age) {
        return defaultBlockState().setValue(getStageProperty(), age);
    }

    @Override
    public StateDefinition<Block, BlockState> getStateDefinition() {
        return stateDefinition;
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        int age = state.getValue(getStageProperty());
        int stage = age / ((getMaxAge() + 1) / 4);
        return SHAPES[stage];
    }

    @Override
    protected int getBonemealAgeIncrease(Level level) {
        return getPlantType().randomGrowBonus(level.getRandom());
    }
}
