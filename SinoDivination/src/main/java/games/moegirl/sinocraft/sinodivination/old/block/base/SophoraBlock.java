package games.moegirl.sinocraft.sinodivination.old.block.base;

import games.moegirl.sinocraft.sinodivination.old.blockentity.ISophoraEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.gameevent.GameEventListener;

import javax.annotation.Nullable;

public interface SophoraBlock extends EntityBlock {

    default void placedBy(Level pLevel, BlockPos pPos, @Nullable LivingEntity pPlacer) {
        if (pPlacer != null) {
            ISophoraEntity blockEntity = (ISophoraEntity) pLevel.getBlockEntity(pPos);
            assert blockEntity != null;
            blockEntity.setEntity(pPlacer);
        }
    }

    @Override
    default <T2 extends BlockEntity> GameEventListener getListener(ServerLevel pLevel, T2 pBlockEntity) {
        return (GameEventListener) pBlockEntity;
    }
}
