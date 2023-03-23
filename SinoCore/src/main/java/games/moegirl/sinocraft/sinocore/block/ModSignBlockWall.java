package games.moegirl.sinocraft.sinocore.block;

import com.google.common.collect.Maps;
import games.moegirl.sinocraft.sinocore.blockentity.ModSignBlockEntity;
import games.moegirl.sinocraft.sinocore.woodwork.Woodwork;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

import javax.annotation.Nullable;
import java.util.Map;

import static net.minecraft.world.level.block.HorizontalDirectionalBlock.FACING;

/**
 * @author luqin2007
 */
public class ModSignBlockWall extends ModSignBlock {

    private static final Map<Direction, VoxelShape> AABBS = Maps.newEnumMap(Map.of(
            Direction.NORTH, box(0.0D, 4.5D, 14.0D, 16.0D, 12.5D, 16.0D),
            Direction.SOUTH, box(0.0D, 4.5D, 0.0D, 16.0D, 12.5D, 2.0D),
            Direction.EAST, box(0.0D, 4.5D, 0.0D, 2.0D, 12.5D, 16.0D),
            Direction.WEST, box(14.0D, 4.5D, 0.0D, 16.0D, 12.5D, 16.0D)));

    public ModSignBlockWall(Properties properties, Woodwork woodwork) {
        super(woodwork, properties);
        this.registerDefaultState(this.stateDefinition.any().setValue(FACING, Direction.NORTH).setValue(WATERLOGGED, Boolean.FALSE));
    }

    @Override
    public BlockEntity newBlockEntity(BlockPos pPos, BlockState pState) {
        return new ModSignBlockEntity(getWoodwork().wallSignEntity(), pPos, pState);
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder) {
        pBuilder.add(FACING, WATERLOGGED);
    }

    @Override
    public String getDescriptionId() {
        return this.asItem().getDescriptionId();
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext pContext) {
        BlockState state = this.defaultBlockState();
        BlockPos pos = pContext.getClickedPos();
        FluidState fluid = pContext.getLevel().getFluidState(pos);
        Direction[] directions = pContext.getNearestLookingDirections();

        for (Direction direction : directions) {
            if (direction.getAxis().isHorizontal()) {
                state = state.setValue(FACING, direction.getOpposite());
                if (state.canSurvive(pContext.getLevel(), pos)) {
                    return state.setValue(WATERLOGGED, fluid.getType() == Fluids.WATER);
                }
            }
        }

        return null;
    }

    @Override
    public boolean canSurvive(BlockState pState, LevelReader pLevel, BlockPos pPos) {
        return pLevel.getBlockState(pPos.relative(pState.getValue(FACING).getOpposite())).getMaterial().isSolid();
    }

    @Override
    public VoxelShape getShape(BlockState pState, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext) {
        return AABBS.get(pState.getValue(FACING));
    }

    @Override
    public BlockState updateShape(BlockState pState, Direction pFacing, BlockState pFacingState, LevelAccessor pLevel, BlockPos pCurrentPos, BlockPos pFacingPos) {
        return pFacing.getOpposite() == pState.getValue(FACING) && !pState.canSurvive(pLevel, pCurrentPos)
                ? Blocks.AIR.defaultBlockState()
                : super.updateShape(pState, pFacing, pFacingState, pLevel, pCurrentPos, pFacingPos);
    }

    @Override
    public BlockState rotate(BlockState pState, Rotation pRotation) {
        return pState.setValue(FACING, pRotation.rotate(pState.getValue(FACING)));
    }

    @Override
    public BlockState mirror(BlockState pState, Mirror pMirror) {
        return pState.rotate(pMirror.getRotation(pState.getValue(FACING)));
    }
}
