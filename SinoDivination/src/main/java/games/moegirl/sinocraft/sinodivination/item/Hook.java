package games.moegirl.sinocraft.sinodivination.item;

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
    public boolean mineBlock(ItemStack itemStack, Level level, BlockState blockState, BlockPos pos, LivingEntity miningEntity) {
        if (!level.isClientSide && !blockState.is(BlockTags.FIRE)) {
            itemStack.hurtAndBreak(1, miningEntity, (entity) -> entity.broadcastBreakEvent(EquipmentSlot.MAINHAND));
        }
        if (blockState.is(BlockTags.LEAVES)) {
            // 25%
            if (level.random.nextInt(4) == 0) {
                miningEntity.spawnAtLocation(SDItems.SILKWORM_BABY.get());
            }
            return true;
        }
        return super.mineBlock(itemStack, level, blockState, pos, miningEntity);
    }
}
