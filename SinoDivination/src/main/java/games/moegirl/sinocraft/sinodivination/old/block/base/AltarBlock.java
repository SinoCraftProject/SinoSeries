package games.moegirl.sinocraft.sinodivination.old.block.base;

import games.moegirl.sinocraft.sinocore.block.AbstractEntityBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;

import java.util.function.Supplier;

public abstract class AltarBlock<T extends BlockEntity> extends AbstractEntityBlock<T> {

    public AltarBlock(Supplier<BlockEntityType<T>> entityType) {
        super(entityType);
    }

    @Override
    public InteractionResult use(BlockState pState, Level pLevel, BlockPos pPos, Player pPlayer, InteractionHand pHand, BlockHitResult pHit) {
        if (!pLevel.isClientSide) {
            ItemStack itemInHand = pPlayer.getItemInHand(pHand);
            pLevel.getBlockEntity(pPos, entityType.get()).ifPresent(entity -> {
                if (itemInHand.isEmpty() || pPlayer.isShiftKeyDown()) {
                    ItemStack item = takeItem(entity);
                    if (!item.isEmpty()) {
                        if (!pPlayer.getInventory().add(item)) {
                            pPlayer.drop(item, false);
                        }
                    }
                } else {
                    ItemStack stack = putItem(entity, itemInHand);
                    pPlayer.setItemInHand(pHand, stack);
                }
            });
        }
        return InteractionResult.sidedSuccess(pLevel.isClientSide);
    }

    public abstract ItemStack takeItem(T be);

    public abstract ItemStack putItem(T be, ItemStack stack);
}
