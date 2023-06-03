package games.moegirl.sinocraft.sinofoundation.block.plant;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

import java.util.function.Supplier;

public class DoublePlantBlock extends PlantBlock {
    public static final BooleanProperty TOP = BooleanProperty.create("top");

    protected static final VoxelShape FULL_SHAPE = Block.box(0, 0, 0, 16, 16, 16);

    public DoublePlantBlock(PlantType plantType, Supplier<? extends ItemLike> seed) {
        super(plantType, seed);
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        // Todo: qyl27: add bottom and have no top detect?
        return isTop(state) ? super.getShape(state, level, pos, context) : FULL_SHAPE;
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder);
        builder.add(TOP);
    }

    @Override
    protected boolean mayPlaceOn(BlockState state, BlockGetter level, BlockPos pos) {
        return super.mayPlaceOn(state, level, pos) || (state.getBlock() == this && !isTop(state));
    }

    public static boolean isTop(BlockState state) {
        return state.getValue(TOP);
    }

    // Todo: qyl27: double plant block.
}
