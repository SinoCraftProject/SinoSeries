package games.moegirl.sinocraft.sinodivination.old.block;

import games.moegirl.sinocraft.sinocore.tree.Tree;
import games.moegirl.sinocraft.sinodivination.old.block.base.SophoraBlock;
import games.moegirl.sinocraft.sinodivination.old.blockentity.SDBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.TrapDoorBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

import javax.annotation.Nullable;

public class SophoraTrapdoor extends TrapDoorBlock implements SophoraBlock {

    public SophoraTrapdoor(Tree tree, Properties properties) {
        super(properties, tree.getBlockSetType());
    }

    @Override
    public void setPlacedBy(Level pLevel, BlockPos pPos, BlockState pState, @Nullable LivingEntity pPlacer, ItemStack pStack) {
        super.setPlacedBy(pLevel, pPos, pState, pPlacer, pStack);
        placedBy(pLevel, pPos, pPlacer);
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pPos, BlockState pState) {
        return SDBlockEntities.SOPHORA_ENTITY.get().create(pPos, pState);
    }
}
