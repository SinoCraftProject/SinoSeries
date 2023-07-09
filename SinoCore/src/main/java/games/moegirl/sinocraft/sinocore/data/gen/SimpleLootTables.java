package games.moegirl.sinocraft.sinocore.data.gen;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.storage.loot.LootTable;

import java.util.HashMap;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

public interface SimpleLootTables extends Consumer<BiConsumer<ResourceLocation, LootTable.Builder>> {

    static SimpleLootTables create() {
        return new Impl();
    }

    void add(ResourceLocation name, LootTable.Builder table);

    @Override
    void accept(BiConsumer<ResourceLocation, LootTable.Builder> consumer);

    boolean isEmpty();

    class Impl implements SimpleLootTables {
        private final Map<ResourceLocation, LootTable.Builder> tables = new HashMap<>();

        @Override
        public void add(ResourceLocation name, LootTable.Builder table) {
            tables.put(name, table);
        }

        @Override
        public void accept(BiConsumer<ResourceLocation, LootTable.Builder> consumer) {
            tables.forEach(consumer);
        }

        @Override
        public boolean isEmpty() {
            return tables.isEmpty();
        }
    }
}
