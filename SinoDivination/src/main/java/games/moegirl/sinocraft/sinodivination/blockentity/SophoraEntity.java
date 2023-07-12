package games.moegirl.sinocraft.sinodivination.blockentity;

import games.moegirl.sinocraft.sinocore.tree.Tree;
import games.moegirl.sinocraft.sinocore.tree.TreeBlockType;
import games.moegirl.sinocraft.sinodivination.SinoDivination;
import games.moegirl.sinocraft.sinofoundation.data.SFDItemTags;
import games.moegirl.sinocraft.sinofoundation.SFDDamages;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Registry;
import net.minecraft.core.Vec3i;
import net.minecraft.core.registries.Registries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageType;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.common.ForgeHooks;

import javax.annotation.Nullable;
import java.util.UUID;

/**
 * 槐木方块
 */
public class SophoraEntity extends BlockEntity implements ISophoraEntity {

    public static SophoraEntity trapdoor(Tree tree, BlockPos worldPosition, BlockState blockState) {
        return new SophoraEntity(tree.getBlockEntityType(TreeBlockType.TRAPDOOR), worldPosition, blockState);
    }

    public static SophoraEntity fenceGate(Tree tree, BlockPos worldPosition, BlockState blockState) {
        return new SophoraEntity(tree.getBlockEntityType(TreeBlockType.FENCE_GATE), worldPosition, blockState);
    }

    @Nullable
    protected UUID entity = null;

    public SophoraEntity(BlockEntityType<?> type, BlockPos worldPosition, BlockState blockState) {
        super(type, worldPosition, blockState);
    }

    @Override
    public void setEntity(Entity entity) {
        this.entity = entity.getUUID();
        setChanged();
    }

    /**
     * @deprecated Only use for debug
     */
    @Deprecated
    public void setEntity(UUID entity) {
        this.entity = entity;
        setChanged();
    }

    @Nullable
    public UUID getEntity() {
        return entity;
    }

    @Override
    public void load(CompoundTag tag) {
        super.load(tag);
        entity = tag.hasUUID(SinoDivination.MODID + ".record") ? tag.getUUID(SinoDivination.MODID + ".record") : null;
    }

    @Override
    protected void saveAdditional(CompoundTag tag) {
        super.saveAdditional(tag);
        if (entity != null) {
            tag.putUUID(SinoDivination.MODID + ".record", entity);
        }
    }

    @Override
    public boolean handleGameEvent(ServerLevel level, GameEvent gameEvent, GameEvent.Context context, Vec3 pos) {
        if (!level.isClientSide && worldPosition.equals(new Vec3i((int) pos.x, (int) pos.y, (int) pos.z))) {
            if (gameEvent.equals(GameEvent.BLOCK_OPEN) || gameEvent.equals(GameEvent.BLOCK_CLOSE)) {
                Entity source = context.sourceEntity();
                if (entity != null
                        // 实体检查
                        && source instanceof LivingEntity living && !entity.equals(source.getUUID())
                        // 御鬼棒
                        && !holdGhostGuardStick(source)) {
                    Registry<DamageType> registry = level.registryAccess().registryOrThrow(Registries.DAMAGE_TYPE);
                    DamageSource damage = new DamageSource(registry.getHolderOrThrow(SFDDamages.SOPHORA_DOOR));
                    actuallyHurt(damage, 5, living);
                }
            }
        }
        return false;
    }

    /**
     * 造成真实伤害，严格减少生命值
     *
     * @param damageSource 伤害源
     * @param damageAmount 伤害值
     * @param entity       实体
     */
    public static void actuallyHurt(DamageSource damageSource, float damageAmount, LivingEntity entity) {
        ForgeHooks.onLivingHurt(entity, damageSource, damageAmount);
        ForgeHooks.onLivingDamage(entity, damageSource, damageAmount);
        float currentHealth = entity.getHealth();
        entity.getCombatTracker().recordDamage(damageSource, damageAmount);
        entity.setHealth(currentHealth - damageAmount);
        entity.gameEvent(GameEvent.ENTITY_DAMAGE, damageSource.getEntity());
    }

    /**
     * 检查是否持有御鬼棒
     *
     * @param entity 实体
     * @return 是否持有御鬼棒
     */
    public static boolean holdGhostGuardStick(@Nullable Entity entity) {
        return entity instanceof LivingEntity living && (living.getMainHandItem().is(SFDItemTags.STACK_SOPHORA) || living.getOffhandItem().is(SFDItemTags.STACK_SOPHORA));
    }
}
