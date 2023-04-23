package games.moegirl.sinocraft.sinocore.data;

import net.minecraft.data.loot.EntityLootSubProvider;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.level.storage.loot.LootTable;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

public class SimpleEntityLootTables extends EntityLootSubProvider {

    private final Map<EntityType<?>, LootTable.Builder> tables = new HashMap<>();

    public SimpleEntityLootTables() {
        super(FeatureFlags.DEFAULT_FLAGS);
    }

    public void addEntity(EntityType<?> entity, LootTable.Builder table) {
        tables.put(entity, table);
    }

    public void addEntities(Map<EntityType<?>, LootTable.Builder> tables) {
        tables.forEach(this::addEntity);
    }

    @Override
    public void generate() {
        tables.forEach(this::addEntity);
    }

    @Override
    protected Stream<EntityType<?>> getKnownEntityTypes() {
        return tables.keySet().stream();
    }
}
