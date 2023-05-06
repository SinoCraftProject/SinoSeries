package games.moegirl.sinocraft.sinodivination.old.util;

import games.moegirl.sinocraft.sinodivination.SinoDivination;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.NbtUtils;
import net.minecraft.nbt.Tag;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.OwnableEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

import javax.annotation.Nullable;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public abstract class OwnerChecker {

    public static OwnerChecker forBlock(BlockEntity entity) {
        return new OwnerChecker() {
            @Override
            protected void setChanged() {
                entity.setChanged();
                Level level = entity.getLevel();
                if (level != null && !level.isClientSide) {
                    BlockState bs = entity.getBlockState();
                    level.sendBlockUpdated(entity.getBlockPos(), bs, bs, 3);
                }
            }
        };
    }

    @Nullable
    private UUID owner = null;
    private final Set<UUID> allowed = new HashSet<>();

    public boolean isAllowed(@Nullable Entity entity) {
        if (owner == null) {
            return true;
        }
        if (entity instanceof Player player) {
            UUID id = player.getGameProfile().getId();
            return owner.equals(id) || allowed.contains(id);
        }
        return false;
    }

    public void allow(UUID uuid) {
        allowed.add(uuid);
        setChanged();
    }

    public void allow(Entity entity) {
        allowed.add(entity.getUUID());
        setChanged();
    }

    public void removeAllow(@Nullable Entity entity) {
        if (entity != null && allowed.remove(entity.getUUID())) {
            setChanged();
        }
    }

    public Set<UUID> getAllowed() {
        return Set.copyOf(allowed);
    }

    public void setOwner(@Nullable Entity placer) {
        owner = getUUID(placer);
        setChanged();
    }

    public void setOwner(@Nullable UUID owner) {
        this.owner = owner;
        setChanged();
    }

    @Nullable
    public UUID getOwner() {
        return owner;
    }

    public boolean isOwner(@Nullable Entity entity) {
        if (owner == null) {
            return true;
        }
        return owner.equals(getUUID(entity));
    }

    @Nullable
    private UUID getUUID(@Nullable Entity entity) {
        if (entity instanceof Player player) {
            return player.getGameProfile().getId();
        } else if (entity instanceof OwnableEntity ownable) {
            return ownable.getOwnerUUID();
        }
        return null;
    }

    protected abstract void setChanged();

    public void save(CompoundTag tag) {
        if (owner != null) {
            tag.putUUID(SinoDivination.MODID + ".owner", owner);
        }
        if (!allowed.isEmpty()) {
            ListTag list = new ListTag();
            allowed.stream().map(NbtUtils::createUUID).forEach(list::add);
            tag.put(SinoDivination.MODID + ".allowed", list);
        }
    }

    public void load(CompoundTag tag) {
        if (tag.hasUUID(SinoDivination.MODID + ".owner")) {
            owner = tag.getUUID(SinoDivination.MODID + ".owner");
        }
        if (tag.contains(SinoDivination.MODID + ".allowed", Tag.TAG_LIST)) {
            allowed.clear();
            tag.getList(SinoDivination.MODID + ".allowed", Tag.TAG_INT_ARRAY).stream()
                    .map(NbtUtils::loadUUID)
                    .forEach(allowed::add);
        }
    }
}
