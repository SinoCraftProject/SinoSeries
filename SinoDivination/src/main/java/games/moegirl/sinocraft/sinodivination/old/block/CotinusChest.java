package games.moegirl.sinocraft.sinodivination.old.block;

import games.moegirl.sinocraft.sinodivination.old.block.base.CotinusBlock;
import games.moegirl.sinocraft.sinodivination.old.block.base.WoodenChest;
import games.moegirl.sinocraft.sinodivination.old.blockentity.SDBlockEntities;
import games.moegirl.sinocraft.sinodivination.old.item.SDItems;
import games.moegirl.sinocraft.sinodivination.old.tree.SDTrees;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;

public class CotinusChest extends WoodenChest implements CotinusBlock {

    public CotinusChest() {
        super(SDTrees.COTINUS, SDBlockEntities.COTINUS_CHEST, SDItems.COTINUS_CHEST);
    }

    @Override
    public void setPlacedBy(Level pLevel, BlockPos pPos, BlockState pState, LivingEntity pPlacer, ItemStack pStack) {
        super.setPlacedBy(pLevel, pPos, pState, pPlacer, pStack);
        placedBy(pLevel, pPos, pPlacer);
    }

    @Override
    public InteractionResult use(BlockState pState, Level pLevel, BlockPos pPos, Player pPlayer, InteractionHand pHand, BlockHitResult pHit) {
        if (!isAllowed(pPos, pPlayer)) {
            return InteractionResult.FAIL;
        }
        return super.use(pState, pLevel, pPos, pPlayer, pHand, pHit);
    }
}
