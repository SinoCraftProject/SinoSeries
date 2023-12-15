package games.moegirl.sinocraft.sinodivination.block;

import games.moegirl.sinocraft.sinocore.block.AbstractEntityBlock;
import games.moegirl.sinocraft.sinodivination.blockentity.AltarEntity;
import games.moegirl.sinocraft.sinodivination.blockentity.SDBlockEntities;
import games.moegirl.sinocraft.sinodivination.blockentity.TripodEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;

public class Tripod extends AbstractEntityBlock<TripodEntity> {

    public Tripod() {
        super(Properties.of(), SDBlockEntities.TRIPOD);
    }

    @Override
    public InteractionResult use(BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit) {
        if (!level.isClientSide) {
            TripodEntity tripod = getBlockEntity(level, pos);
            ItemStack item = player.getItemInHand(hand);
            if (item.isEmpty() || player.isShiftKeyDown()) {
                // 取出物品
                ItemStack itemStack = tripod.takeItem();
                if (!player.addItem(itemStack)) {
                    player.drop(itemStack, false);
                }
            } else {
                // 放入物品
                player.setItemInHand(hand, tripod.putItem(item));
            }
        }
        return InteractionResult.sidedSuccess(level.isClientSide);
    }

    @Override
    public void onRemove(BlockState state, Level level, BlockPos pos, BlockState newState, boolean isMoving) {
        getBlockEntity(level, pos).detachStructure();
        super.onRemove(state, level, pos, newState, isMoving);
    }
}
