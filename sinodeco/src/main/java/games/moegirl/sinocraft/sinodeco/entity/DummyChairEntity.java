package games.moegirl.sinocraft.sinodeco.entity;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.Level;

public class DummyChairEntity extends Entity {
    public DummyChairEntity(EntityType<?> entityType, Level level) {
        super(entityType, level);
    }

    @Override
    protected void defineSynchedData(SynchedEntityData.Builder builder) {
    }

    @Override
    protected void readAdditionalSaveData(CompoundTag compound) {
    }

    @Override
    protected void addAdditionalSaveData(CompoundTag compound) {
    }

    @Override
    public void onPassengerTurned(Entity entityToUpdate) {
        entityToUpdate.setYBodyRot(this.getYRot());
        var wrapped = Mth.wrapDegrees(entityToUpdate.getYRot() - this.getYRot());
        var clamped = Mth.clamp(wrapped, -105.0F, 105.0F);
        entityToUpdate.yRotO += clamped - wrapped;
        entityToUpdate.setYRot(entityToUpdate.getYRot() + clamped - wrapped);
        entityToUpdate.setYHeadRot(entityToUpdate.getYRot());
    }

    @Override
    public void tick() {
        super.tick();

        if (!isVehicle()) {
            this.discard();
        }
    }
}
