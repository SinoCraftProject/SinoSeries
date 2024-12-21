package games.moegirl.sinocraft.sinocore.data.gen.loot;

import net.minecraft.world.level.storage.loot.LootTable;

/**
 * 在自定义的 Block 类中实现，用于 DataProvider 自动注册其掉落物
 */
public interface ILootableBlock {

    LootTable.Builder createLootBuilder();
}
