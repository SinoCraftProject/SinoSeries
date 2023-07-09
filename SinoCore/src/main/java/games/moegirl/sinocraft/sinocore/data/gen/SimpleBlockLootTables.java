package games.moegirl.sinocraft.sinocore.data.gen;

import games.moegirl.sinocraft.sinocore.block.ILootableBlock;
import games.moegirl.sinocraft.sinocore.utility.BlockLootables;
import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.world.flag.FeatureFlagSet;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.providers.number.NumberProvider;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

/**
 * @author skyinr
 */
public class SimpleBlockLootTables extends BlockLootSubProvider {

    private final List<Block> blocks = new ArrayList<>();

    public SimpleBlockLootTables() {
        super(new HashSet<>(), FeatureFlags.DEFAULT_FLAGS);
    }

    @Override
    public void add(Block block, LootTable.Builder table) {
        super.add(block, table);
        blocks.add(block);
    }

    public void add(Block block) {
        if (block instanceof ILootableBlock lb) {
            add(block, lb.createLootBuilder(BlockLootables.INSTANCE));
        } else {
            add(block, createSingleItemTable(block));
        }
    }

    public void remove(Block block) {
        blocks.remove(block);
        map.remove(block.getLootTable());
    }

    public void removeAll(Iterable<? extends Block> blocks) {
        blocks.forEach(this::remove);
    }

    @Override
    protected Iterable<Block> getKnownBlocks() {
        return blocks;
    }

    @Override
    protected void generate() {
    }

    // 暴露 protected 成员
    public static final float[] NORMAL_LEAVES_SAPLING_CHANCES = BlockLootSubProvider.NORMAL_LEAVES_SAPLING_CHANCES;

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

    @Override
    public LootTable.Builder createSingleItemTable(ItemLike item, NumberProvider count) {
        return super.createSingleItemTable(item, count);
    }
}
