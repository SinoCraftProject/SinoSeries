package games.moegirl.sinocraft.sinocore.old.api.tree;

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
public class TreeBlockLoot extends BlockLoot {
    private static final float[] NORMAL_LEAVES_SAPLING_CHANCES = new float[]{0.05F, 0.0625F, 0.083333336F, 0.1F};

    private final Map<Block, Function<Block, LootTable.Builder>> loots = new HashMap<>();

    public TreeBlockLoot(Tree tree) {
        loots.put(tree.sapling(), BlockLoot::createSingleItemTable);
        loots.put(tree.log(), BlockLoot::createSingleItemTable);
        loots.put(tree.strippedLog(), BlockLoot::createSingleItemTable);
        loots.put(tree.strippedLog(), BlockLoot::createSingleItemTable);
        loots.put(tree.wood(), BlockLoot::createSingleItemTable);
        loots.put(tree.strippedWoods(), BlockLoot::createSingleItemTable);
        loots.put(tree.leaves(), b -> BlockLoot.createLeavesDrops(b, tree.sapling(), NORMAL_LEAVES_SAPLING_CHANCES));
        loots.put(tree.pottedSapling(), b -> BlockLoot.createPotFlowerItemTable(tree.sapling()));
    }

    @Override
    protected void addTables() {
        loots.forEach(this::addDrop);
    }

    @Override
    protected Iterable<Block> getKnownBlocks() {
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
