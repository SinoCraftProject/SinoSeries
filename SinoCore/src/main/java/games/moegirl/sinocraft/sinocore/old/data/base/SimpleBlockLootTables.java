package games.moegirl.sinocraft.sinocore.old.data.base;

import games.moegirl.sinocraft.sinocore.block.ILootableBlock;
import games.moegirl.sinocraft.sinocore.utility.BlockLootables;
import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.storage.loot.LootTable;

import javax.annotation.Nullable;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

/**
 * @author skyinr
 */
public class SimpleBlockLootTables extends BlockLootSubProvider {

    private Map<Block, LootTable.Builder> blocks = new HashMap<>();

    public SimpleBlockLootTables() {
        super(new HashSet<>(), FeatureFlags.DEFAULT_FLAGS);
    }

    @Override
    protected void add(Block block, LootTable.Builder table) {
        super.add(block, table);
    }

    public void addBlock(Block block, LootTable.Builder table) {
        blocks.put(block, table);
    }

    public void addBlock(Block block) {
        blocks.put(block, null);
    }

    public void remove(Block block) {
        blocks.remove(block);
    }

    public void removeAll(Iterable<? extends Block> blocks) {
        blocks.forEach(this::remove);
    }

    @Override
    protected void generate() {
        blocks.forEach((block, builder) -> add(block, getTable(block, builder)));
    }

    @Override
    protected Iterable<Block> getKnownBlocks() {
        return Collections.unmodifiableSet(blocks.keySet());
    }

    private LootTable.Builder getTable(Block block, @Nullable LootTable.Builder table) {
        if (table == null) {
            if (block instanceof ILootableBlock lb) {
                return lb.createLootBuilder(BlockLootables.INSTANCE);
            } else {
                return createSingleItemTable(block);
            }
        }
        return table;
    }
}
