package games.moegirl.sinocraft.sinodivination.old.block;

import games.moegirl.sinocraft.sinodivination.old.block.base.DoubleCropBlock;
import games.moegirl.sinocraft.sinodivination.old.item.SDItems;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SimpleWaterloggedBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.DoubleBlockHalf;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

public class Rice extends DoubleCropBlock<Item> implements SimpleWaterloggedBlock {

    private static final VoxelShape[] SHAPES = new VoxelShape[]{
            Block.box(3, 0, 13, 3, 3, 13),
            Block.box(2, 0, 14, 2, 5, 14),
            Block.box(1, 0, 15, 1, 6, 15),
            Block.box(0, 0, 16, 0, 11, 16),
            Block.box(0, 0, 16, 0, 14, 16),
            Block.box(0, 0, 16, 0, 15, 16),
            Block.box(0, 0, 16, 0, 16, 16),
            Shapes.block()
    };

    public Rice() {
        super(Properties.of(Material.WATER_PLANT).noCollission().randomTicks().sound(SoundType.CROP),
                SDItems.RICE, 0, 1, 3, 4);
    }

    @Override
    public BlockState updateShape(BlockState state, Direction direction, BlockState neighborState, LevelAccessor level, BlockPos currentPos, BlockPos neighborPos) {
        if (!state.canSurvive(level, currentPos)) {
            return Blocks.AIR.defaultBlockState();
        }
        if (getHalf(state) == DoubleBlockHalf.LOWER) {
            level.scheduleTick(currentPos, Fluids.WATER, Fluids.WATER.getTickDelay(level));
        }
        return super.updateShape(state, direction, neighborState, level, currentPos, neighborPos);
    }

    @Override
    public VoxelShape getShape(BlockState pState, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext) {
        return getHalf(pState) == DoubleBlockHalf.LOWER ? SHAPES[7] : SHAPES[getAge(pState)];
    }

    @Override
    public boolean mayPlaceOn(BlockState pState, BlockGetter pLevel, BlockPos pPos) {
        return pState.is(Blocks.WATER) && pLevel.getBlockState(pPos.above()).isAir();
    }

    @Override
    public boolean canSurviveLower(BlockState pState, LevelReader pLevel, BlockPos pPos) {
        return true;
    }

    @Override
    public FluidState getFluidState(BlockState state) {
        return getHalf(state) == DoubleBlockHalf.LOWER ? Fluids.WATER.getSource(false) : super.getFluidState(state);
    }
}
