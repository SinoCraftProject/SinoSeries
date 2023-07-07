package games.moegirl.sinocraft.sinocore.tree.event;

import com.mojang.datafixers.util.Function3;
import games.moegirl.sinocraft.sinocore.SinoCore;
import games.moegirl.sinocraft.sinocore.block.ILootableBlock;
import games.moegirl.sinocraft.sinocore.data.loottable.LootTableProviderBase;
import games.moegirl.sinocraft.sinocore.data.SimpleBlockLootTables;
import games.moegirl.sinocraft.sinocore.tree.Tree;
import games.moegirl.sinocraft.sinocore.tree.TreeBlockType;
import games.moegirl.sinocraft.sinocore.utility.BlockLootables;
import net.minecraft.data.PackOutput;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.storage.loot.LootTable;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.BiFunction;

class ProviderBlockLootTable extends LootTableProviderBase {

    static final Map<TreeBlockType, Function3<Block, Tree, SimpleBlockLootTables, LootTable.Builder>> DEFAULT_LOOT = new HashMap<>();

    static {
        DEFAULT_LOOT.put(TreeBlockType.SAPLING, ext(SimpleBlockLootTables::createSingleItemTable));
        DEFAULT_LOOT.put(TreeBlockType.LOG, ext(SimpleBlockLootTables::createSingleItemTable));
        DEFAULT_LOOT.put(TreeBlockType.STRIPPED_LOG, ext(SimpleBlockLootTables::createSingleItemTable));
        DEFAULT_LOOT.put(TreeBlockType.LOG_WOOD, ext(SimpleBlockLootTables::createSingleItemTable));
        DEFAULT_LOOT.put(TreeBlockType.STRIPPED_LOG_WOOD, ext(SimpleBlockLootTables::createSingleItemTable));
        DEFAULT_LOOT.put(TreeBlockType.LEAVES, (block, tree, p) -> p.createLeavesDrops(block, tree.getBlock(TreeBlockType.SAPLING), SimpleBlockLootTables.NORMAL_LEAVES_SAPLING_CHANCES));
        DEFAULT_LOOT.put(TreeBlockType.POTTED_SAPLING, (block, tree, p) -> p.createPotFlowerItemTable(tree.getBlock(TreeBlockType.SAPLING)));
        DEFAULT_LOOT.put(TreeBlockType.PLANKS, ext(SimpleBlockLootTables::createSingleItemTable));
        DEFAULT_LOOT.put(TreeBlockType.STAIRS, ext(SimpleBlockLootTables::createSingleItemTable));
        DEFAULT_LOOT.put(TreeBlockType.SLAB, ext(SimpleBlockLootTables::createSlabItemTable));
        DEFAULT_LOOT.put(TreeBlockType.BUTTON, ext(SimpleBlockLootTables::createSingleItemTable));
        DEFAULT_LOOT.put(TreeBlockType.PRESSURE_PLATE, ext(SimpleBlockLootTables::createSingleItemTable));
        DEFAULT_LOOT.put(TreeBlockType.FENCE, ext(SimpleBlockLootTables::createSingleItemTable));
        DEFAULT_LOOT.put(TreeBlockType.FENCE_GATE, ext(SimpleBlockLootTables::createSingleItemTable));
        DEFAULT_LOOT.put(TreeBlockType.DOOR, ext(SimpleBlockLootTables::createDoorTable));
        DEFAULT_LOOT.put(TreeBlockType.TRAPDOOR, ext(SimpleBlockLootTables::createSingleItemTable));
        DEFAULT_LOOT.put(TreeBlockType.SIGN, ext(SimpleBlockLootTables::createSingleItemTable));
        DEFAULT_LOOT.put(TreeBlockType.WALL_SIGN, ext(SimpleBlockLootTables::createSingleItemTable));
        DEFAULT_LOOT.put(TreeBlockType.HANGING_SIGN, (block, tree, p) -> p.createSingleItemTable(tree.getBlock(TreeBlockType.SIGN)));
        DEFAULT_LOOT.put(TreeBlockType.WALL_HANGING_SIGN, (block, tree, p) -> p.createSingleItemTable(tree.getBlock(TreeBlockType.SIGN)));
    }

    protected final List<Tree> treeTypes;

    public ProviderBlockLootTable(PackOutput output, List<Tree> treeTypes) {
        super(output, SinoCore.MODID);
        this.treeTypes = treeTypes;
    }

    @Override
    public void getTables(List<SubProviderEntry> tables) {
        for (Tree tree : treeTypes) {
            for (TreeBlockType type : TreeBlockType.values()) {
                if (type.hasBlock()) {
                    Block block = tree.getBlock(type);
                    if (block instanceof ILootableBlock loot) {
                        addBlock(block, loot.createLootBuilder(BlockLootables.INSTANCE));
                    } else if (ProviderBlockLootTable.DEFAULT_LOOT.containsKey(type)) {
                        addBlock(block, ProviderBlockLootTable.DEFAULT_LOOT.get(type).apply(block, tree, getBlocks()));
                    }
                }
            }
        }
    }

    @Override
    public String getProviderName() {
        return "Tree Loot Tables";
    }

    private static Function3<Block, Tree, SimpleBlockLootTables, LootTable.Builder> ext(BiFunction<SimpleBlockLootTables, Block, LootTable.Builder> function) {
        return (block, tree, provider) -> function.apply(provider, block);
    }
}
