package games.moegirl.sinocraft.sinocore.data.gen.loot;

import net.minecraft.data.loot.LootTableSubProvider;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.storage.loot.LootTable;

import java.util.HashMap;
import java.util.Map;
import java.util.function.BiConsumer;

public class SimpleLootTableSubProvider implements LootTableSubProvider {

    protected Map<ResourceKey<LootTable>, LootTable.Builder> tables = new HashMap<>();

    public void add(ResourceKey<LootTable> name, LootTable.Builder table) {
        tables.put(name, table);
    }

    public boolean isEmpty() {
        return tables.isEmpty();
    }

    @Override
    public void generate(BiConsumer<ResourceKey<LootTable>, LootTable.Builder> output) {
        tables.forEach(output);
    }
}
