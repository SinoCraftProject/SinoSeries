package games.moegirl.sinocraft.sinocore.tree.event;

import com.mojang.datafixers.util.Function3;
import games.moegirl.sinocraft.sinocore.block.ILootableBlock;
import games.moegirl.sinocraft.sinocore.tree.Tree;
import games.moegirl.sinocraft.sinocore.tree.TreeBlockType;
import games.moegirl.sinocraft.sinocore.utility.BlockLootables;
import net.minecraft.data.PackOutput;
import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.data.loot.LootTableProvider;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import org.jetbrains.annotations.NotNull;

import java.util.*;
import java.util.function.BiFunction;
import java.util.function.Function;

class ProviderBlockLootTable extends LootTableProvider {

    private static final Map<TreeBlockType, Function3<Block, Tree, BlockLoots, LootTable.Builder>> DEFAULT_LOOT = new HashMap<>();

    static {
        DEFAULT_LOOT.put(TreeBlockType.SAPLING, ext(BlockLoots::createSingleItemTable));
        DEFAULT_LOOT.put(TreeBlockType.LOG, ext(BlockLoots::createSingleItemTable));
        DEFAULT_LOOT.put(TreeBlockType.STRIPPED_LOG, ext(BlockLoots::createSingleItemTable));
        DEFAULT_LOOT.put(TreeBlockType.LOG_WOOD, ext(BlockLoots::createSingleItemTable));
        DEFAULT_LOOT.put(TreeBlockType.STRIPPED_LOG_WOOD, ext(BlockLoots::createSingleItemTable));
        DEFAULT_LOOT.put(TreeBlockType.LEAVES, (block, tree, p) -> p.createLeavesDrops(block, tree.getBlock(TreeBlockType.SAPLING), BlockLoots.LEAVES_CHANCES));
        DEFAULT_LOOT.put(TreeBlockType.POTTED_SAPLING, (block, tree, p) -> p.createPotFlowerItemTable(tree.getBlock(TreeBlockType.SAPLING)));
        DEFAULT_LOOT.put(TreeBlockType.PLANKS, ext(BlockLoots::createSingleItemTable));
        DEFAULT_LOOT.put(TreeBlockType.STAIRS, ext(BlockLoots::createSingleItemTable));
        DEFAULT_LOOT.put(TreeBlockType.SLAB, ext(BlockLoots::createSlabItemTable));
        DEFAULT_LOOT.put(TreeBlockType.BUTTON, ext(BlockLoots::createSingleItemTable));
        DEFAULT_LOOT.put(TreeBlockType.PRESSURE_PLATE, ext(BlockLoots::createSingleItemTable));
        DEFAULT_LOOT.put(TreeBlockType.FENCE, ext(BlockLoots::createSingleItemTable));
        DEFAULT_LOOT.put(TreeBlockType.FENCE_GATE, ext(BlockLoots::createSingleItemTable));
        DEFAULT_LOOT.put(TreeBlockType.DOOR, ext(BlockLoots::createDoorTable));
        DEFAULT_LOOT.put(TreeBlockType.TRAPDOOR, ext(BlockLoots::createSingleItemTable));
        DEFAULT_LOOT.put(TreeBlockType.SIGN, ext(BlockLoots::createSingleItemTable));
        DEFAULT_LOOT.put(TreeBlockType.WALL_SIGN, (block, tree, p) -> p.createSingleItemTable(tree.getBlock(TreeBlockType.SIGN)));
        DEFAULT_LOOT.put(TreeBlockType.CHEST, ext(BlockLoots::createNameableBlockEntityTable));
        DEFAULT_LOOT.put(TreeBlockType.TRAPPED_CHEST, ext(BlockLoots::createNameableBlockEntityTable));
    }

    protected final List<Tree> treeTypes;

    public ProviderBlockLootTable(PackOutput output, List<Tree> treeTypes) {
        super(output, new HashSet<>(), new ArrayList<>());

        this.treeTypes = treeTypes;
    }

    @Override
    public List<SubProviderEntry> getTables() {
        var superTables = super.getTables();
        superTables.addAll(treeTypes.stream()
                .map(BlockLoots::new)
                .map(loot -> new SubProviderEntry(() -> loot, LootContextParamSets.BLOCK))
                .toList());
        return superTables;
    }

    static class BlockLoots extends BlockLootSubProvider {

        public static float[] LEAVES_CHANCES = BlockLootSubProvider.NORMAL_LEAVES_SAPLING_CHANCES;

        private final Map<Block, Function<Block, LootTable.Builder>> loots = new HashMap<>();
        private final Tree tree;

        public BlockLoots(Tree tree) {
            super(new HashSet<>(), FeatureFlags.DEFAULT_FLAGS);
            this.tree = tree;
        }

        @Override
        protected void generate() {
            for (TreeBlockType type : TreeBlockType.values()) {
                if (type.hasBlock()) {
                    Block block = tree.getBlock(type);
                    if (block instanceof ILootableBlock loot) {
                        add(block, loot.createLootBuilder(BlockLootables.INSTANCE));
                    } else {
                        add(block, DEFAULT_LOOT.get(type).apply(block, tree, this));
                    }
                }
            }
        }

        @Override
        protected @NotNull Iterable<Block> getKnownBlocks() {
            return loots.keySet();
        }

        @Override
        public LootTable.Builder createLeavesDrops(Block leavesBlock, Block saplingBlock, float... chances) {
            return super.createLeavesDrops(leavesBlock, saplingBlock, chances);
        }

        @Override
        public LootTable.Builder createPotFlowerItemTable(ItemLike item) {
            return super.createPotFlowerItemTable(item);
        }

        @Override
        public LootTable.Builder createSlabItemTable(Block block) {
            return super.createSlabItemTable(block);
        }

        @Override
        public LootTable.Builder createDoorTable(Block doorBlock) {
            return super.createDoorTable(doorBlock);
        }

        @Override
        public LootTable.Builder createNameableBlockEntityTable(Block block) {
            return super.createNameableBlockEntityTable(block);
        }
    }
    
    private static Function3<Block, Tree, BlockLoots, LootTable.Builder> ext(BiFunction<BlockLoots, Block, LootTable.Builder> function) {
        return (block, tree, provider) -> function.apply(provider, block);
    }
}
