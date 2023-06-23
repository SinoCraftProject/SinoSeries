package games.moegirl.sinocraft.sinofoundation.utility;

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

/**
 * 所有者校验
 */
public abstract class OwnerChecker {
    
    /**
     * 用于 BlockEntity 的所有者校验
     *
     * @param entity 方块实体
     * @return 校验器
     */
    public static OwnerChecker forBlock(String key, BlockEntity entity) {
        return new OwnerChecker(key) {
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
    private UUID owner = null; // 所有者
    private final Set<UUID> allowed = new HashSet<>(); // 允许访问者

    private final String key; // 用于保存的key

    public OwnerChecker(String key) {
        this.key = key;
    }

    /**
     * 校验实体。若为 null 则总是允许
     *
     * @param entity 实体
     * @return 是否校验通过
     */
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

    /**
     * 设置允许实体
     *
     * @param uuid 实体 UUID
     */
    public void allow(UUID uuid) {
        if (allowed.add(uuid)) {
            setChanged();
        }
    }

    /**
     * 移除允许实体
     *
     * @param uuid 实体 UUID
     */
    public void removeAllow(UUID uuid) {
        if (allowed.remove(uuid)) {
            setChanged();
        }
    }

    /**
     * 设置所有者
     *
     * @param placer 所有者
     */
    public void setOwner(@Nullable Entity placer) {
        owner = getUUID(placer);
        setChanged();
    }

    /**
     * 设置所有者
     *
     * @param owner 所有者
     */
    public void setOwner(@Nullable UUID owner) {
        this.owner = owner;
        setChanged();
    }

    /**
     * 获取所有者
     *
     * @return 所有者
     */
    @Nullable
    public UUID getOwner() {
        return owner;
    }

    /**
     * 是否为所有者
     *
     * @param entity 待检查所有者
     * @return 是否为所有者
     */
    public boolean isOwner(@Nullable Entity entity) {
        return owner == null || owner.equals(getUUID(entity));
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

    /**
     * 当数据发生变化时调用
     */
    protected abstract void setChanged();

    /**
     * 保存数据到 nbt
     *
     * @param tag nbt
     */
    public void save(CompoundTag tag) {
        if (owner != null) {
            tag.putUUID(key + ".owner", owner);
        }
        if (!allowed.isEmpty()) {
            ListTag list = new ListTag();
            allowed.stream().map(NbtUtils::createUUID).forEach(list::add);
            tag.put(key + ".allowed", list);
        }
    }

    /**
     * 从 nbt 读取数据
     *
     * @param tag nbt
     */
    public void load(CompoundTag tag) {
        if (tag.hasUUID(key + ".owner")) {
            owner = tag.getUUID(key + ".owner");
        }
        if (tag.contains(key + ".allowed", Tag.TAG_LIST)) {
            allowed.clear();
            tag.getList(key + ".allowed", Tag.TAG_INT_ARRAY).stream()
                    .map(NbtUtils::loadUUID)
                    .forEach(allowed::add);
        }
    }
}
