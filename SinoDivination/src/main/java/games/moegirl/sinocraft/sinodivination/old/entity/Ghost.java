package games.moegirl.sinocraft.sinodivination.old.entity;

import games.moegirl.sinocraft.sinodivination.old.data.SDTags;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

public class Ghost extends Entity {

    public Ghost(EntityType<?> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }

    @Override
    protected void defineSynchedData() {

    }

    @Override
    protected void readAdditionalSaveData(CompoundTag pCompound) {

    }

    @Override
    protected void addAdditionalSaveData(CompoundTag pCompound) {

    }

    public boolean canThrough(BlockState block) {
        return block.is(SDTags.COTINUS_BLOCK);
    }
}
