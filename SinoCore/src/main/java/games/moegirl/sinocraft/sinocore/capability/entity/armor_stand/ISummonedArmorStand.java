package games.moegirl.sinocraft.sinocore.capability.entity.armor_stand;

import net.minecraft.nbt.CompoundTag;
import net.minecraftforge.common.util.INBTSerializable;

public interface ISummonedArmorStand extends INBTSerializable<CompoundTag> {
    boolean shouldDeleteAfterDismount();

    void setShouldDeleteAfterDismount(boolean value);
}
