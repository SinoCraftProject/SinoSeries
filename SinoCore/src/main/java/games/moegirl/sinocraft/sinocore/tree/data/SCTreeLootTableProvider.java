package games.moegirl.sinocraft.sinocore.tree.data;

import games.moegirl.sinocraft.sinocore.block.ILootableBlock;
import games.moegirl.sinocraft.sinocore.tree.Tree;
import games.moegirl.sinocraft.sinocore.tree.TreeBlockType;
import games.moegirl.sinocraft.sinocore.tree.TreeType;
import games.moegirl.sinocraft.sinocore.utility.BlockLootables;
import net.minecraft.data.PackOutput;
import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.data.loot.LootTableProvider;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import org.jetbrains.annotations.NotNull;

import java.util.*;
import java.util.function.Function;

public class SCTreeLootTableProvider extends LootTableProvider {

    protected final List<TreeType> treeTypes;

    public SCTreeLootTableProvider(PackOutput output, List<TreeType> treeTypes) {
        super(output, new HashSet<>(), new ArrayList<>());

        this.treeTypes = treeTypes;
    }

    @Override
    public List<SubProviderEntry> getTables() {
        var superTables = super.getTables();
        superTables.addAll(treeTypes.stream()
                .map(TreeBlockLoot::new)
                .map(loot -> new SubProviderEntry(() -> loot, LootContextParamSets.BLOCK))
                .toList());
        return superTables;
    }

    static class TreeBlockLoot extends BlockLootSubProvider {

        private final Map<Block, Function<Block, LootTable.Builder>> loots = new HashMap<>();

        public TreeBlockLoot(TreeType tree) {
            super(new HashSet<>(), FeatureFlags.DEFAULT_FLAGS);
            loots.put(tree.getBlock(TreeBlockType.SAPLING), this::createSingleItemTable);
            loots.put(tree.getBlock(TreeBlockType.LOG), this::createSingleItemTable);
            loots.put(tree.getBlock(TreeBlockType.STRIPPED_LOG), this::createSingleItemTable);
            loots.put(tree.getBlock(TreeBlockType.LOG_WOOD), this::createSingleItemTable);
            loots.put(tree.getBlock(TreeBlockType.STRIPPED_LOG_WOOD), this::createSingleItemTable);
            loots.put(tree.getBlock(TreeBlockType.LEAVES), b -> this.createLeavesDrops(b, tree.getBlock(TreeBlockType.SAPLING), NORMAL_LEAVES_SAPLING_CHANCES));
            loots.put(tree.getBlock(TreeBlockType.POTTED_SAPLING), b -> this.createPotFlowerItemTable(tree.getBlock(TreeBlockType.SAPLING)));

            loots.put(tree.getBlock(TreeBlockType.PLANKS), BlockLootables.INSTANCE::createSingleItemTable);
            loots.put(tree.getBlock(TreeBlockType.STAIRS), BlockLootables.INSTANCE::createSingleItemTable);
            loots.put(tree.getBlock(TreeBlockType.SLAB), BlockLootables.INSTANCE::createSlabItemTable);
            loots.put(tree.getBlock(TreeBlockType.BUTTON), BlockLootables.INSTANCE::createSingleItemTable);
            loots.put(tree.getBlock(TreeBlockType.PRESSURE_PLATE), BlockLootables.INSTANCE::createSingleItemTable);
            loots.put(tree.getBlock(TreeBlockType.FENCE), BlockLootables.INSTANCE::createSingleItemTable);
            loots.put(tree.getBlock(TreeBlockType.FENCE_GATE), BlockLootables.INSTANCE::createSingleItemTable);
            loots.put(tree.getBlock(TreeBlockType.DOOR), BlockLootables.INSTANCE::createDoorTable);
            loots.put(tree.getBlock(TreeBlockType.TRAPDOOR), BlockLootables.INSTANCE::createSingleItemTable);
            loots.put(tree.getBlock(TreeBlockType.SIGN), BlockLootables.INSTANCE::createSingleItemTable);
            loots.put(tree.getBlock(TreeBlockType.WALL_SIGN), b -> BlockLootables.INSTANCE.createSingleItemTable(tree.getBlock(TreeBlockType.SIGN)));
        }

        @Override
        protected void generate() {
            loots.forEach(this::addDrop);
        }

        @Override
        protected @NotNull Iterable<Block> getKnownBlocks() {
            return loots.keySet();
        }

        private void addDrop(Block block, Function<Block, LootTable.Builder> drop) {
            if (block instanceof ILootableBlock loot) {
                add(block, loot.createLootBuilder(BlockLootables.INSTANCE));
            } else {
                add(block, drop);
            }
        }
    }
}
