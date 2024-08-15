package games.moegirl.sinocraft.sinocore.data.serializable;

import net.minecraft.nbt.CompoundTag;

/**
 * Interface for data class which can serialize/deserialize to/from CompoundTag.
 */
public interface ICompoundTagSerializable {
    void readFromCompound(CompoundTag tag);

    CompoundTag writeToCompound();
}
