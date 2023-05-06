package games.moegirl.sinocraft.sinodivination.old.blockentity;

import games.moegirl.sinocraft.sinodivination.old.block.SDBlocks;
import games.moegirl.sinocraft.sinodivination.old.capability.BirthdayData;
import games.moegirl.sinocraft.sinodivination.old.util.TagSerializers;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Vec3i;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Optional;
import java.util.UUID;

import static games.moegirl.sinocraft.sinodivination.SinoDivination.MODID;

public class SophoraChestEntity extends WoodenChestEntity implements ISophoraEntity {

    @Nullable
    private PlayerRecord entity;

    public SophoraChestEntity(BlockPos blockPos, BlockState state) {
        super(SDBlocks.SOPHORA_CHEST.get(), blockPos, state);
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
    public void load(CompoundTag pTag) {
        super.load(pTag);
        entity = pTag.contains(MODID + ".record", Tag.TAG_COMPOUND) ? new PlayerRecord(pTag.getCompound(MODID + ".record")) : null;
    }

    @Override
    protected void saveAdditional(CompoundTag pTag) {
        super.saveAdditional(pTag);
        if (entity != null) {
            pTag.put(MODID + ".record", entity.write());
        }
    }

    @Override
    public boolean handleGameEvent(ServerLevel level, GameEvent event, GameEvent.Context context, Vec3 pos) {
        Entity entity = context.sourceEntity();
        if (worldPosition.equals(new Vec3i((int) pos.x, (int) pos.y, (int) pos.z))
                && event.equals(GameEvent.CONTAINER_OPEN) && !SophoraEntity.holdGhostGuardStick(entity)) {
            setEntity(entity);
        }
        return false;
    }

    public record PlayerRecord(UUID uuid, Component name, LocalDateTime birthday) {

        public PlayerRecord(CompoundTag pTag) {
            this(pTag.getUUID("player"),
                    Optional.<Component>ofNullable(Component.Serializer.fromJson(pTag.getString("name")))
                            .orElseGet(() -> Component.literal(pTag.getUUID("player").toString())),
                    LocalDateTime.of(LocalDate.ofEpochDay(pTag.getLong("date")),
                            LocalTime.ofNanoOfDay(pTag.getLong("time"))));
        }

        public PlayerRecord(Player player) {
            this(player.getUUID(), player.getDisplayName(),
                    player.getCapability(BirthdayData.CAPABILITY).resolve().orElseThrow().getBirthday());
        }

        public CompoundTag write() {
            CompoundTag record = new CompoundTag();
            record.putUUID("player", uuid);
            record.putString("name", Component.Serializer.toJson(name));
            TagSerializers.writeDate(birthday, record);
            return record;
        }
    }
}
