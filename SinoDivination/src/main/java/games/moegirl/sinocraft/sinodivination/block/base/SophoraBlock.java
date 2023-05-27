package games.moegirl.sinocraft.sinodivination.block.base;

import games.moegirl.sinocraft.sinodivination.blockentity.base.ISophoraEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.gameevent.GameEventListener;

import javax.annotation.Nullable;

public interface SophoraBlock extends EntityBlock {

    default void placedBy(Level level, BlockPos pos, @Nullable LivingEntity placer) {
        if (placer != null) {
            ISophoraEntity blockEntity = (ISophoraEntity) level.getBlockEntity(pos);
            assert blockEntity != null;
            blockEntity.setEntity(placer);
        }
    }

    @Override
    default <T2 extends BlockEntity> GameEventListener getListener(ServerLevel level, T2 blockEntity) {
        return (GameEventListener) blockEntity;
    }
}
