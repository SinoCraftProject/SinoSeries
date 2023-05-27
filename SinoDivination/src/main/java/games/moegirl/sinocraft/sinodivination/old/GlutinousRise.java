//package games.moegirl.sinocraft.sinodivination.old;
//
//import games.moegirl.sinocraft.sinocore.utility.RoundChecker;
//import games.moegirl.sinocraft.sinocore.block.SimpleCropBlock;
//import games.moegirl.sinocraft.sinodivination.item.SDItems;
//import net.minecraft.core.BlockPos;
//import net.minecraft.core.Direction;
//import net.minecraft.tags.BlockTags;
//import net.minecraft.world.item.Item;
//import net.minecraft.world.level.BlockGetter;
//import net.minecraft.world.level.block.Blocks;
//import net.minecraft.world.level.block.state.BlockState;
//import net.minecraft.world.level.material.FluidState;
//import net.minecraft.world.level.material.Fluids;
//import net.minecraft.world.phys.shapes.CollisionContext;
//import net.minecraft.world.phys.shapes.VoxelShape;
//
//public class GlutinousRise extends SimpleCropBlock<Item> {
//
//    public static final VoxelShape SHAPE = box(0, 0, 0, 16, 32, 16);
//
//    public GlutinousRise() {
//        super(() -> SDItems.RICE, 1, 1, 3, 4);
//    }
//
//    @Override
//    public VoxelShape getShape(BlockState pState, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext) {
//        return SHAPE;
//    }
//
//    @Override
//    public boolean mayPlaceOn(BlockState pState, BlockGetter pLevel, BlockPos pPos) {
//        RoundChecker blocks = new RoundChecker(pLevel, pPos.above());
//        return pState.is(BlockTags.DIRT)
//                && blocks.is(Direction.UP, Blocks.WATER)
//                && blocks.isSource(Direction.UP)
//                && blocks.isAir(Direction.UP, 2);
//    }
//
//    @Override
//    public FluidState getFluidState(BlockState pState) {
//        return Fluids.WATER.getSource(false);
//    }
//}
