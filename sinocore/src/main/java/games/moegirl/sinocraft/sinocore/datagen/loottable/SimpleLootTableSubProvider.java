package games.moegirl.sinocraft.sinocore.datagen.loottable;

import net.minecraft.data.loot.LootTableSubProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.storage.loot.LootTable;

import java.util.HashMap;
import java.util.Map;
import java.util.function.BiConsumer;

public class SimpleLootTableSubProvider implements LootTableSubProvider {

    protected Map<ResourceLocation, LootTable.Builder> tables = new HashMap<>();

    public void add(ResourceLocation name, LootTable.Builder table) {
        tables.put(name, table);
    }

    public boolean isEmpty() {
        return tables.isEmpty();
    }

    @Override
    public void generate(BiConsumer<ResourceLocation, LootTable.Builder> output) {
        tables.forEach(output);
    }
}
