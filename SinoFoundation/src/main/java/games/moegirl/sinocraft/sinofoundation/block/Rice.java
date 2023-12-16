package games.moegirl.sinocraft.sinofoundation.block;

import games.moegirl.sinocraft.sinocore.block.DoubleCropBlock;
import games.moegirl.sinocraft.sinofoundation.item.SFDItems;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.tags.BlockTags;
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
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

/**
 * 稻米
 */
public class Rice extends DoubleCropBlock<Item> implements SimpleWaterloggedBlock {

    private static final VoxelShape[] SHAPES = new VoxelShape[]{
            // 顶部
            Block.box(3, 0, 13, 3, 3, 13),
            Block.box(2, 0, 14, 2, 5, 14),
            Block.box(1, 0, 15, 1, 6, 15),
            Block.box(0, 0, 16, 0, 11, 16),
            Block.box(0, 0, 16, 0, 14, 16),
            Block.box(0, 0, 16, 0, 15, 16),
            Block.box(0, 0, 16, 0, 16, 16),
            // 底部
            Shapes.block()
    };

    public Rice() {
        super(Properties.copy(Blocks.SEAGRASS).noCollission().randomTicks().sound(SoundType.CROP),
                SFDItems.RICE, 7, 0, 1, 3, 4);
    }

    @Override
    public BlockState updateShape(BlockState state, Direction direction, BlockState neighborState, LevelAccessor level, BlockPos currentPos, BlockPos neighborPos) {
        if (!state.canSurvive(level, currentPos)) {
            return Blocks.AIR.defaultBlockState();
        }
        if (state.getValue(HALF) == DoubleBlockHalf.LOWER) {
            // 底部在水中
            level.scheduleTick(currentPos, Fluids.WATER, Fluids.WATER.getTickDelay(level));
        }
        return super.updateShape(state, direction, neighborState, level, currentPos, neighborPos);
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return state.getValue(HALF) == DoubleBlockHalf.LOWER ? SHAPES[7] : SHAPES[getAge(state)];
    }

    @Override
    public boolean mayPlaceOn(BlockState state, BlockGetter level, BlockPos pos) {
        // 只能在完整的水方块上
        return state.is(Blocks.WATER) && level.getBlockState(pos.above()).isAir();
    }

    @Override
    public boolean canSurviveLower(BlockState state, LevelReader level, BlockPos pos) {
        return state.is(BlockTags.DIRT);
    }

    @Override
    @SuppressWarnings("deprecation")
    public FluidState getFluidState(BlockState state) {
        return state.getValue(HALF) == DoubleBlockHalf.LOWER
                ? Fluids.WATER.getSource(false)
                : super.getFluidState(state);
    }
}
