package games.moegirl.sinocraft.sinodivination.old.blockentity;

import games.moegirl.sinocraft.sinodivination.SinoDivination;
import games.moegirl.sinocraft.sinodivination.old.entity.SDDamages;
import games.moegirl.sinocraft.sinodivination.old.item.SDItems;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.common.ForgeHooks;

import javax.annotation.Nullable;
import java.util.UUID;

public class SophoraEntity extends BlockEntity implements ISophoraEntity {

    @Nullable
    protected UUID entity = null;

    public SophoraEntity(BlockEntityType<?> pType, BlockPos pWorldPosition, BlockState pBlockState) {
        super(pType, pWorldPosition, pBlockState);
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
    public void load(CompoundTag pTag) {
        super.load(pTag);
        entity = pTag.hasUUID(SinoDivination.MODID + ".record") ? pTag.getUUID(SinoDivination.MODID + ".record") : null;
    }

    @Override
    protected void saveAdditional(CompoundTag pTag) {
        super.saveAdditional(pTag);
        if (entity != null) {
            pTag.putUUID(SinoDivination.MODID + ".record", entity);
        }
    }

    @Override
    public boolean handleGameEvent(ServerLevel level, GameEvent gameEvent, GameEvent.Context context, Vec3 pos) {
        if (!level.isClientSide && pos.equals(worldPosition)) {
            if (gameEvent.equals(GameEvent.BLOCK_OPEN) || gameEvent.equals(GameEvent.BLOCK_CLOSE)) {
                Entity pEntity = context.sourceEntity();
                if (entity != null && pEntity instanceof LivingEntity living && !entity.equals(pEntity.getUUID()) && !holdGhostGuardStick(pEntity)) {
                    actuallyHurt(SDDamages.bySophoraDoor(), 5, living);
                }
            }
        }
        return false;
    }

    public static void actuallyHurt(DamageSource pDamageSrc, float pDamageAmount, LivingEntity pEntity) {
        ForgeHooks.onLivingHurt(pEntity, pDamageSrc, pDamageAmount);
        ForgeHooks.onLivingDamage(pEntity, pDamageSrc, pDamageAmount);
        float currentHealth = pEntity.getHealth();
        pEntity.getCombatTracker().recordDamage(pDamageSrc, currentHealth, pDamageAmount);
        pEntity.setHealth(currentHealth - pDamageAmount);
        pEntity.gameEvent(GameEvent.ENTITY_DAMAGE, pDamageSrc.getEntity());
    }

    public static boolean holdGhostGuardStick(@Nullable Entity entity) {
        return entity instanceof LivingEntity living && (living.getMainHandItem().is(SDItems.STICK_SOPHORA.get()) || living.getOffhandItem().is(SDItems.STICK_SOPHORA.get()));
    }
}
