package games.moegirl.sinocraft.sinodivination.old.blockentity;

import games.moegirl.sinocraft.sinodivination.SinoDivination;
import games.moegirl.sinocraft.sinodivination.old.util.OwnerChecker;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

public class CotinusDoorEntity extends CotinusEntity {
    private boolean isAbove = false;

    public CotinusDoorEntity(BlockEntityType<?> pType, BlockPos pWorldPosition, BlockState pBlockState) {
        super(pType, pWorldPosition, pBlockState);
    }

    public void above() {
        isAbove = true;
        setChanged();
    }

    @Override
    public boolean isOpen() {
        if (level != null && isAbove && level.getBlockEntity(worldPosition.below()) instanceof CotinusDoorEntity door) {
            return door.isOpen();
        } else {
            return super.isOpen();
        }
    }

    @Override
    public void setOpen(boolean isOpen) {
        if (level != null && isAbove && level.getBlockEntity(worldPosition.below()) instanceof CotinusDoorEntity door) {
            door.setOpen(isOpen);
        } else {
            super.setOpen(isOpen);
        }
    }

    @Override
    protected void saveAdditional(CompoundTag tag) {
        super.saveAdditional(tag);
        if (isAbove) {
            tag.putBoolean(SinoDivination.MODID + ".above", true);
        }
    }

    @Override
    public void load(CompoundTag tag) {
        super.load(tag);
        if (tag.getBoolean(SinoDivination.MODID + ".above")) {
            isAbove = true;
        }
    }

    @Override
    public OwnerChecker owner() {
        if (level != null && isAbove && level.getBlockEntity(worldPosition.below()) instanceof CotinusDoorEntity door) {
            return door.checker;
        } else {
            return checker;
        }
    }
}
