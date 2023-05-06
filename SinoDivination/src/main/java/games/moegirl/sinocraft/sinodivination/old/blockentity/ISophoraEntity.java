package games.moegirl.sinocraft.sinodivination.old.blockentity;

import games.moegirl.sinocraft.sinocore.utility.Self;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.gameevent.BlockPositionSource;
import net.minecraft.world.level.gameevent.GameEventListener;
import net.minecraft.world.level.gameevent.PositionSource;

public interface ISophoraEntity extends GameEventListener, Self<BlockEntity> {

    void setEntity(Entity entity);

    @Override
    default PositionSource getListenerSource() {
        return new BlockPositionSource(self().getBlockPos());
    }

    @Override
    default int getListenerRadius() {
        return 1;
    }
}
