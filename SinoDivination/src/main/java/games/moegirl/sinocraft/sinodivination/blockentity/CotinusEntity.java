package games.moegirl.sinocraft.sinodivination.blockentity;

import games.moegirl.sinocraft.sinocore.tree.Tree;
import games.moegirl.sinocraft.sinocore.tree.TreeBlockType;
import games.moegirl.sinocraft.sinofoundation.SinoFoundation;
import games.moegirl.sinocraft.sinofoundation.utility.OwnerChecker;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.Vec3;

public class CotinusEntity extends BlockEntity implements ICotinusEntity {

    public static CotinusEntity trapdoor(Tree tree, BlockPos worldPosition, BlockState blockState) {
        return new CotinusEntity(tree.getBlockEntityType(TreeBlockType.TRAPDOOR), worldPosition, blockState);
    }

    public static CotinusEntity fenceGate(Tree tree, BlockPos worldPosition, BlockState blockState) {
        return new CotinusEntity(tree.getBlockEntityType(TreeBlockType.FENCE_GATE), worldPosition, blockState);
    }

    protected boolean isOpen = false;
    protected final OwnerChecker checker = OwnerChecker.forBlock(SinoFoundation.MODID, this);

    public CotinusEntity(BlockEntityType<?> entityType, BlockPos worldPosition, BlockState blockState) {
        super(entityType, worldPosition, blockState);
    }

    public boolean isOpen() {
        return isOpen;
    }

    public void setOpen(boolean isOpen) {
        this.isOpen = isOpen;
        setChanged();
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
        isOpen = pTag.getBoolean(SinoFoundation.MODID + ".doorOpen");
        checker.load(pTag);
    }

    @Override
    protected void saveAdditional(CompoundTag pTag) {
        super.saveAdditional(pTag);
        pTag.putBoolean(SinoFoundation.MODID + ".doorOpen", isOpen);
        checker.save(pTag);
    }

    @Override
    public boolean handleGameEvent(ServerLevel level, GameEvent gameEvent, GameEvent.Context context, Vec3 pos) {
        if (!level.isClientSide && pos.equals(worldPosition)) {
            if (gameEvent == GameEvent.BLOCK_OPEN && !isOpen()) {
                if (!owner().isAllowed(context.sourceEntity())) {
                    BlockPos bp =  new BlockPos((int) pos.x, (int) pos.y, (int) pos.z);
                    level.setBlock(bp, getBlockState().setValue(BlockStateProperties.OPEN, false), 2);
                    return true;
                } else {
                    setOpen(true);
                }
            } else if (gameEvent == GameEvent.BLOCK_CLOSE && isOpen()) {
                if (!owner().isAllowed(context.sourceEntity())) {
                    BlockPos bp =  new BlockPos((int) pos.x, (int) pos.y, (int) pos.z);
                    level.setBlock(bp, getBlockState().setValue(BlockStateProperties.OPEN, true), 2);
                    return true;
                } else {
                    setOpen(false);
                }
            }
        }
        return false;
    }
}
