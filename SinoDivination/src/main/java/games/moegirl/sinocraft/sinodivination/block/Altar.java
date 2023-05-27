package games.moegirl.sinocraft.sinodivination.block;

import games.moegirl.sinocraft.sinocore.block.AbstractEntityBlock;
import games.moegirl.sinocraft.sinodivination.blockentity.AltarEntity;
import games.moegirl.sinocraft.sinodivination.blockentity.SDBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;

/**
 * 祭坛
 */
public class Altar extends AbstractEntityBlock<AltarEntity> {

    public Altar() {
        super(SDBlockEntities.ALTAR);
    }

    @Override
    public InteractionResult use(BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit) {
        if (!level.isClientSide) {
            AltarEntity altar = getBlockEntity(level, pos);
            ItemStack item = player.getItemInHand(hand);
            if (item.isEmpty() || player.isShiftKeyDown()) {
                // 取出物品
                ItemStack itemStack = altar.takeItem();
                if (!player.addItem(itemStack)) {
                    player.drop(itemStack, false);
                }
            } else {
                // 放入物品
                player.setItemInHand(hand, altar.putItem(item));
            }
        }
        return InteractionResult.sidedSuccess(level.isClientSide);
    }
}
