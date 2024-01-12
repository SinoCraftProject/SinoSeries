package games.moegirl.sinocraft.sinocore.nbt;

import net.minecraft.nbt.CompoundTag;

/**
 * Interface for data class which can serialize/deserialize to/from CompoundTag.
 */
public interface IDataCompoundTagSerializable {
    void readFromCompound(CompoundTag tag);

    CompoundTag writeToCompound();
}
