package games.moegirl.sinocraft.sinofoundation.block.entity.tree;

import games.moegirl.sinocraft.sinocore.utility.Self;
import games.moegirl.sinocraft.sinofoundation.utility.OwnerChecker;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.gameevent.BlockPositionSource;
import net.minecraft.world.level.gameevent.GameEventListener;
import net.minecraft.world.level.gameevent.PositionSource;

public interface ICotinusEntity extends GameEventListener, Self<BlockEntity> {

    OwnerChecker owner();

    @Override
    default PositionSource getListenerSource() {
        return new BlockPositionSource(self().getBlockPos());
    }

    @Override
    default int getListenerRadius() {
        return 1;
    }
}
