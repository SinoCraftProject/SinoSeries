package games.moegirl.sinocraft.sinocore.old.woodwork;

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
public class WoodworkBlockLoot extends BlockLootSubProvider {
    private final Map<Block, Function<Block, LootTable.Builder>> loots = new HashMap<>();

    public WoodworkBlockLoot(Woodwork woodwork) {
        super(new HashSet<>(), FeatureFlags.DEFAULT_FLAGS);
        loots.put(woodwork.planks(), BlockLootables.INSTANCE::createSingleItemTable);
        loots.put(woodwork.sign(), BlockLootables.INSTANCE::createSingleItemTable);
        loots.put(woodwork.wallSign(), b -> BlockLootables.INSTANCE.createSingleItemTable(woodwork.sign()));
        loots.put(woodwork.pressurePlate(), BlockLootables.INSTANCE::createSingleItemTable);
        loots.put(woodwork.trapdoor(), BlockLootables.INSTANCE::createSingleItemTable);
        loots.put(woodwork.stairs(), BlockLootables.INSTANCE::createSingleItemTable);
        loots.put(woodwork.button(), BlockLootables.INSTANCE::createSingleItemTable);
        loots.put(woodwork.slab(), BlockLootables.INSTANCE::createSlabItemTable);
        loots.put(woodwork.fenceGate(), BlockLootables.INSTANCE::createSingleItemTable);
        loots.put(woodwork.fence(), BlockLootables.INSTANCE::createSingleItemTable);
        loots.put(woodwork.door(), BlockLootables.INSTANCE::createDoorTable);
    }

    @Override
    protected void generate() {
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
