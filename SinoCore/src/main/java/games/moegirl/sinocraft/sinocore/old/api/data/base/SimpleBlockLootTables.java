package games.moegirl.sinocraft.sinocore.old.api.data.base;

import games.moegirl.sinocraft.sinocore.api.block.ILootableBlock;
import games.moegirl.sinocraft.sinocore.api.utility.BlockLootables;
import net.minecraft.data.loot.BlockLoot;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraftforge.registries.DeferredRegister;

import javax.annotation.Nullable;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

/**
 * @author skyinr
 */
public class SimpleBlockLootTables extends BlockLoot {
    private final Map<Block, LootTable.Builder> blocks = new HashMap<>();

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

    public void addBlocks(DeferredRegister<Block> register) {
        register.getEntries().stream().map(Supplier::get).forEach(this::addBlock);
    }

    public void remove(Block block) {
        blocks.remove(block);
    }

    public void removeAll(Iterable<? extends Block> blocks) {
        blocks.forEach(this::remove);
    }

    @Override
    protected Iterable<Block> getKnownBlocks() {
        return Collections.unmodifiableSet(blocks.keySet());
    }

    @Override
    protected void addTables() {
        blocks.forEach((block, table) -> add(block, getTable(block, table)));
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
