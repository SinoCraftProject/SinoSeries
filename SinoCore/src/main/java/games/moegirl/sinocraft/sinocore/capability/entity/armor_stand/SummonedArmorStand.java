package games.moegirl.sinocraft.sinocore.capability.entity.armor_stand;

import net.minecraft.nbt.CompoundTag;

public class SummonedArmorStand implements ISummonedArmorStand {
    public static final String TAG_SHOULD_DELETE_AFTER_DISMOUNT_NAME = "shouldDeleteAfterDismount";

    private boolean deleteAfterDismount = false;

    @Override
    public boolean shouldDeleteAfterDismount() {
        return deleteAfterDismount;
    }

    @Override
    public void setShouldDeleteAfterDismount(boolean value) {
        this.deleteAfterDismount = value;
    }

    @Override
    public CompoundTag serializeNBT() {
        var tag = new CompoundTag();
        tag.putBoolean(TAG_SHOULD_DELETE_AFTER_DISMOUNT_NAME, deleteAfterDismount);
        return tag;
    }

    @Override
    public void deserializeNBT(CompoundTag tag) {
        deleteAfterDismount = tag.getBoolean(TAG_SHOULD_DELETE_AFTER_DISMOUNT_NAME);
    }
}
