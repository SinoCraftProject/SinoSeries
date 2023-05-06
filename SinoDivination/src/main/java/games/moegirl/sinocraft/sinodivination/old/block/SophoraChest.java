package games.moegirl.sinocraft.sinodivination.old.block;

import games.moegirl.sinocraft.sinodivination.old.block.base.SophoraBlock;
import games.moegirl.sinocraft.sinodivination.old.block.base.WoodenChest;
import games.moegirl.sinocraft.sinodivination.old.blockentity.SDBlockEntities;
import games.moegirl.sinocraft.sinodivination.old.item.SDItems;
import games.moegirl.sinocraft.sinodivination.old.tree.SDTrees;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

public class SophoraChest extends WoodenChest implements SophoraBlock {

    public SophoraChest() {
        super(SDTrees.SOPHORA, SDBlockEntities.SOPHORA_CHEST, SDItems.SOPHORA_CHEST);
    }

    @Override
    public void setPlacedBy(Level pLevel, BlockPos pPos, BlockState pState, LivingEntity pPlacer, ItemStack pStack) {
        super.setPlacedBy(pLevel, pPos, pState, pPlacer, pStack);
        placedBy(pLevel, pPos, pPlacer);
    }
}
