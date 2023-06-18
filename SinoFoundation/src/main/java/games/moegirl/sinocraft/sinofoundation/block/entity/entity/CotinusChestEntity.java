package games.moegirl.sinocraft.sinofoundation.block.entity;

import games.moegirl.sinocraft.sinocore.blockentity.BaseChestBlockEntity;
import games.moegirl.sinocraft.sinofoundation.SinoFoundation;
import games.moegirl.sinocraft.sinofoundation.util.OwnerChecker;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.Vec3;

/**
 * 无患木匣
 */
public class CotinusChestEntity extends BaseChestBlockEntity implements ICotinusEntity {

    private final OwnerChecker checker = OwnerChecker.forBlock(SinoFoundation.MODID, this);

    public CotinusChestEntity(BlockPos pos, BlockState state) {
        super(SFDBlockEntities.COTINUS_CHEST.get(), pos, state);
    }

    @Override
    public OwnerChecker owner() {
        return checker;
    }

    @Override
    public CompoundTag getUpdateTag() {
        return saveWithoutMetadata();
    }

    @Override
    public Packet<ClientGamePacketListener> getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }

    @Override
    public void load(CompoundTag pTag) {
        super.load(pTag);
        checker.load(pTag);
    }

    @Override
    protected void saveAdditional(CompoundTag pTag) {
        super.saveAdditional(pTag);
        checker.save(pTag);
    }

    @Override
    public boolean handleGameEvent(ServerLevel level, GameEvent gameEvent, GameEvent.Context context, Vec3 pos) {
        return false;
    }
}
