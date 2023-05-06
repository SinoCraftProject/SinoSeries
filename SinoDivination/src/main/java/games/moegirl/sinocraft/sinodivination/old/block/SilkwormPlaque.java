package games.moegirl.sinocraft.sinodivination.old.block;

import games.moegirl.sinocraft.sinocore.block.AbstractEntityBlock;
import games.moegirl.sinocraft.sinodivination.old.blockentity.SDBlockEntities;
import games.moegirl.sinocraft.sinodivination.old.blockentity.SilkwormPlaqueEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraftforge.network.NetworkHooks;

public class SilkwormPlaque extends AbstractEntityBlock<SilkwormPlaqueEntity> {

    public SilkwormPlaque() {
        super(Properties.copy(Blocks.STONE), SDBlockEntities.SILKWORM_PLAQUE);
    }

    @Override
    public InteractionResult use(BlockState pState, Level pLevel, BlockPos pPos, Player pPlayer, InteractionHand pHand, BlockHitResult pHit) {
        if (!pLevel.isClientSide) {
            pLevel.getBlockEntity(pPos, entityType.get())
                    .ifPresent(entity -> NetworkHooks.openScreen((ServerPlayer) pPlayer, entity, pPos));
            return InteractionResult.SUCCESS;
        }
        return super.use(pState, pLevel, pPos, pPlayer, pHand, pHit);
    }
}
