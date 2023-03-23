package games.moegirl.sinocraft.sinocore.block;

import games.moegirl.sinocraft.sinocore.utility.BlockLootables;
import net.minecraft.world.level.storage.loot.LootTable;

/**
 * 在自定义的 Block 类中实现，用于 DataProvider 自动注册其掉落物
 *
 * @see BlockLootables
 * @see games.moegirl.sinocraft.sinocore.old.data.base.SimpleBlockLootTables
 */
public interface ILootableBlock {

    LootTable.Builder createLootBuilder(BlockLootables helper);
}
