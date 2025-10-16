package games.moegirl.sinocraft.sinocore.data.serializable;

import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;

/**
 * Interface for data class which can serialize/deserialize to/from CompoundTag.
 */
public interface ICompoundTagSerializable {
    void readFromCompound(CompoundTag tag, HolderLookup.Provider registries);

    CompoundTag writeToCompound(HolderLookup.Provider registries);
}
