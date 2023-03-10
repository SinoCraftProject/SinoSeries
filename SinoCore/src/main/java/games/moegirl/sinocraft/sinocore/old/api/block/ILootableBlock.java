package games.moegirl.sinocraft.sinocore.old.api.block;

import games.moegirl.sinocraft.sinocore.api.utility.BlockLootables;
import net.minecraft.world.level.storage.loot.LootTable;

/**
 * An interface for block
 * <p>A block implement this interface will have custom loot table in datagen base</p>
 * <p>This params use {@link net.minecraft.world.level.storage.loot.parameters.LootContextParamSets#BLOCK}</p>
 */
public interface ILootableBlock {

    /**
     * Get the custom loot table builder
     * @return builder
     */
    LootTable.Builder createLootBuilder(BlockLootables helper);
}
