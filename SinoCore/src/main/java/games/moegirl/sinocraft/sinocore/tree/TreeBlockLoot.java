package games.moegirl.sinocraft.sinocore.tree;

import games.moegirl.sinocraft.sinocore.old.block.ILootableBlock;
import games.moegirl.sinocraft.sinocore.old.utility.BlockLootables;
import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.storage.loot.LootTable;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.function.Function;

/**
 * A loot table for tree blocks
 */
public class TreeBlockLoot extends BlockLootSubProvider {
    private static final float[] NORMAL_LEAVES_SAPLING_CHANCES = new float[]{0.05F, 0.0625F, 0.083333336F, 0.1F};

    private final Map<Block, Function<Block, LootTable.Builder>> loots = new HashMap<>();

    public TreeBlockLoot(Tree tree) {
        super(new HashSet<>(), FeatureFlags.DEFAULT_FLAGS);
        loots.put(tree.sapling(), BlockLootables.INSTANCE::createSingleItemTable);
        loots.put(tree.log(), BlockLootables.INSTANCE::createSingleItemTable);
        loots.put(tree.strippedLog(), BlockLootables.INSTANCE::createSingleItemTable);
        loots.put(tree.strippedLog(), BlockLootables.INSTANCE::createSingleItemTable);
        loots.put(tree.wood(), BlockLootables.INSTANCE::createSingleItemTable);
        loots.put(tree.strippedWoods(), BlockLootables.INSTANCE::createSingleItemTable);
        loots.put(tree.leaves(), b -> BlockLootables.INSTANCE.createLeavesDrops(b, tree.sapling(), NORMAL_LEAVES_SAPLING_CHANCES));
        loots.put(tree.pottedSapling(), b -> BlockLootables.INSTANCE.createPotFlowerItemTable(tree.sapling()));
    }

    @Override
    protected void generate() {
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
