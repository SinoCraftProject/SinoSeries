package games.moegirl.sinocraft.sinodivination.blockentity;

import com.mojang.datafixers.util.Pair;
import games.moegirl.sinocraft.sinocore.blockentity.BaseTrappedChestBlockEntity;
import games.moegirl.sinocraft.sinocore.tree.Tree;
import games.moegirl.sinocraft.sinocore.tree.TreeBlockType;
import games.moegirl.sinocraft.sinodivination.blockentity.SophoraChestEntity.PlayerRecord;
import games.moegirl.sinocraft.sinodivination.blockentity.base.ISophoraEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Vec3i;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtOps;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.ExtraCodecs;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

import static games.moegirl.sinocraft.sinodivination.SinoDivination.MODID;


/**
 * 槐木匣
 */
public class SophoraTrappedChestEntity extends BaseTrappedChestBlockEntity implements ISophoraEntity {

    @Nullable
    private PlayerRecord entity;

    public SophoraTrappedChestEntity(Tree tree, BlockPos blockPos, BlockState state) {
        super(tree.getBlockEntityType(TreeBlockType.CHEST), blockPos, state);
    }

    @Override
    public void setEntity(@Nullable Entity entity) {
        if (entity instanceof Player player && !SophoraEntity.holdGhostGuardStick(entity)) {
            this.entity = new PlayerRecord(player);
        }
    }

    public Optional<PlayerRecord> getEntity() {
        return Optional.ofNullable(entity);
    }

    @Override
    public void load(CompoundTag tag) {
        super.load(tag);
        if (tag.contains(MODID + ".player")) {
            UUID uuid = tag.getUUID("player");
            entity = new PlayerRecord(uuid,
                    Optional.<Component>ofNullable(Component.Serializer.fromJson(tag.getString("name")))
                            .orElseGet(() -> Component.literal(uuid.toString())),
                    ExtraCodecs.INSTANT_ISO8601.decode(NbtOps.INSTANCE, tag.get("birthday")).result()
                            .orElseGet(() -> Pair.of(Instant.now(), null)).getFirst());
        } else {
            entity = null;
        }
    }

    @Override
    protected void saveAdditional(CompoundTag tag) {
        super.saveAdditional(tag);
        if (entity != null) {
            tag.putUUID(MODID + ".player", entity.uuid());
            tag.putString(MODID + ".name", Component.Serializer.toJson(entity.name()));
            tag.put(MODID + ".birthday", ExtraCodecs.INSTANT_ISO8601.encodeStart(NbtOps.INSTANCE, entity.birthday()).result().orElseThrow());
        }
    }

    @Override
    public boolean handleGameEvent(ServerLevel level, GameEvent event, GameEvent.Context context, Vec3 pos) {
        Entity entity = context.sourceEntity();
        if (worldPosition.equals(new Vec3i((int) pos.x, (int) pos.y, (int) pos.z))
                // 监听：箱子打开
                && event.equals(GameEvent.CONTAINER_OPEN) && !SophoraEntity.holdGhostGuardStick(entity)) {
            setEntity(entity);
        }
        return false;
    }
}
