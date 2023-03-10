package games.moegirl.sinocraft.sinocore.old.api.woodwork;

import games.moegirl.sinocraft.sinocore.api.block.ILootableBlock;
import games.moegirl.sinocraft.sinocore.api.utility.BlockLootables;
import net.minecraft.data.loot.BlockLoot;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.storage.loot.LootTable;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

/**
 * A loot table for tree blocks
 */
public class WoodworkBlockLoot extends BlockLoot {
    private final Map<Block, Function<Block, LootTable.Builder>> loots = new HashMap<>();

    public WoodworkBlockLoot(Woodwork woodwork) {
        loots.put(woodwork.planks(), BlockLoot::createSingleItemTable);
        loots.put(woodwork.sign(), BlockLoot::createSingleItemTable);
        loots.put(woodwork.wallSign(), b -> BlockLoot.createSingleItemTable(woodwork.sign()));
        loots.put(woodwork.pressurePlate(), BlockLoot::createSingleItemTable);
        loots.put(woodwork.trapdoor(), BlockLoot::createSingleItemTable);
        loots.put(woodwork.stairs(), BlockLoot::createSingleItemTable);
        loots.put(woodwork.button(), BlockLoot::createSingleItemTable);
        loots.put(woodwork.slab(), BlockLoot::createSlabItemTable);
        loots.put(woodwork.fenceGate(), BlockLoot::createSingleItemTable);
        loots.put(woodwork.fence(), BlockLoot::createSingleItemTable);
        loots.put(woodwork.door(), BlockLoot::createDoorTable);
    }

    @Override
    protected void addTables() {
        loots.forEach(this::addDrop);
    }

    @Override
    public Iterable<Block> getKnownBlocks() {
        return loots.keySet();
    }

    private void addDrop(Block block, Function<Block, LootTable.Builder> drop) {
        if (block instanceof ILootableBlock lootable) {
            add(block, lootable.createLootBuilder(BlockLootables.INSTANCE));
        } else {
            add(block, drop);
        }
    }
}
