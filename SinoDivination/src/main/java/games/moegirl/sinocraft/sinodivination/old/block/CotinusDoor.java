package games.moegirl.sinocraft.sinodivination.old.block;

import games.moegirl.sinocraft.sinocore.tree.Tree;
import games.moegirl.sinocraft.sinodivination.old.block.base.CotinusBlock;
import games.moegirl.sinocraft.sinodivination.old.blockentity.CotinusDoorEntity;
import games.moegirl.sinocraft.sinodivination.old.blockentity.SDBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.DoorBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import org.jetbrains.annotations.Nullable;

public class CotinusDoor extends DoorBlock implements CotinusBlock {

    public CotinusDoor(Tree tree, Properties properties) {
        super(properties, tree.getBlockSetType());
    }

    @Override
    public void setPlacedBy(Level pLevel, BlockPos pPos, BlockState pState, LivingEntity pPlacer, ItemStack pStack) {
        super.setPlacedBy(pLevel, pPos, pState, pPlacer, pStack);
        CotinusDoorEntity blockEntity = (CotinusDoorEntity) pLevel.getBlockEntity(pPos);
        assert blockEntity != null;
        blockEntity.owner().setOwner(pPlacer);
        CotinusDoorEntity blockEntity2 = (CotinusDoorEntity) pLevel.getBlockEntity(pPos.above());
        assert blockEntity2 != null;
        blockEntity2.above();
    }

    @Override
    public InteractionResult use(BlockState pState, Level pLevel, BlockPos pPos, Player pPlayer, InteractionHand pHand, BlockHitResult pHit) {
        if (!isAllowed(pPos, pPlayer)) {
            return InteractionResult.FAIL;
        }
        return super.use(pState, pLevel, pPos, pPlayer, pHand, pHit);
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pPos, BlockState pState) {
        return SDBlockEntities.COTINUS_DOOR.get().create(pPos, pState);
    }
}
