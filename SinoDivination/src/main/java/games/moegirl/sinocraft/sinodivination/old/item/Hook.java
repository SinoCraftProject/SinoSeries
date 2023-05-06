package games.moegirl.sinocraft.sinodivination.old.item;

import net.minecraft.core.BlockPos;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Vanishable;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

public class Hook extends Item implements Vanishable {

    public Hook() {
        super(new Properties().durability(500));
    }

    @Override
    public boolean mineBlock(ItemStack pStack, Level pLevel, BlockState pState, BlockPos pPos, LivingEntity pMiningEntity) {
        if (!pLevel.isClientSide && !pState.is(BlockTags.FIRE)) {
            pStack.hurtAndBreak(1, pMiningEntity, (entity) -> entity.broadcastBreakEvent(EquipmentSlot.MAINHAND));
        }
        if (pState.is(BlockTags.LEAVES)) {
            // 25%
            if (pLevel.random.nextInt(4) == 0) {
                pMiningEntity.spawnAtLocation(SDItems.SILKWORM_BABY.get());
            }
            return true;
        }
        return super.mineBlock(pStack, pLevel, pState, pPos, pMiningEntity);
    }
}
