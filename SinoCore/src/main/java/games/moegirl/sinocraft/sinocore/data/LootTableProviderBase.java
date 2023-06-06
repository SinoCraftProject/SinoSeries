package games.moegirl.sinocraft.sinocore.data;

import com.google.common.collect.Maps;
import com.google.common.collect.Multimap;
import com.mojang.logging.LogUtils;
import games.moegirl.sinocraft.sinocore.mixin.IBlockLootSubProvider;
import net.minecraft.advancements.critereon.StatePropertiesPredicate;
import net.minecraft.data.CachedOutput;
import net.minecraft.data.DataProvider;
import net.minecraft.data.PackOutput;
import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.data.loot.LootTableProvider;
import net.minecraft.data.loot.LootTableSubProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.LootTables;
import net.minecraft.world.level.storage.loot.ValidationContext;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSet;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraft.world.level.storage.loot.predicates.InvertedLootItemCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemBlockStatePropertyCondition;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;
import org.slf4j.Logger;

import java.nio.file.Path;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.function.Supplier;

public abstract class LootTableProviderBase implements DataProvider {
    protected final String modid;

    private final SimpleBlockLootTables blocks = new SimpleBlockLootTables();
    private final SimpleEntityLootTables entities = new SimpleEntityLootTables();
    private final Map<LootContextParamSet, SimpleLootTables> simple = new HashMap<>();
    private final List<LootTableProvider.SubProviderEntry> other = new ArrayList<>();

    private static final Logger LOGGER = LogUtils.getLogger();
    private final PackOutput.PathProvider pathProvider;
    private final Set<ResourceLocation> requiredTables;
    private final List<LootTableProvider.SubProviderEntry> subProviders;

    public LootTableProviderBase(PackOutput output, String modid) {
        this.modid = modid;
        this.pathProvider = output.createPathProvider(PackOutput.Target.DATA_PACK, "loot_tables");
        this.subProviders = new ArrayList<>();
        this.requiredTables = new HashSet<>();
    }

    public LootTableProviderBase(PackOutput output, String modid, DeferredRegister<Block> blocks) {
        this(output, modid);
        addBlocks(blocks);
    }

    @Override
    public CompletableFuture<?> run(CachedOutput output) {
        HashMap<ResourceLocation, LootTable> map = Maps.newHashMap();
        this.getTables().forEach(arg -> arg.provider().get().generate((arg2, arg3) -> {
            map.put(arg2, arg3.setParamSet(arg.paramSet()).build());
        }));
        ValidationContext validationcontext = new ValidationContext(LootContextParamSets.ALL_PARAMS, arg -> null, map::get);
        this.validate(map, validationcontext);
        Multimap<String, String> multimap = validationcontext.getProblems();
        if (!multimap.isEmpty()) {
            multimap.forEach((string, string2) -> LOGGER.warn("Found validation problem in {}: {}", string, string2));
            throw new IllegalStateException("Failed to validate loot tables, see logs");
        }
        return CompletableFuture.allOf(map.entrySet().stream().map(entry -> {
            ResourceLocation rl = entry.getKey();
            LootTable loottable = entry.getValue();
            Path path = this.pathProvider.json(rl);
            return DataProvider.saveStable(output, LootTables.serialize(loottable), path);
        }).toArray(CompletableFuture[]::new));
    }

    @Override
    public String getName() {
        return "Loot Tables: " + modid;
    }

    /**
     * Return the block loot table
     */
    public abstract void addLootTables();

    public List<LootTableProvider.SubProviderEntry> getTables() {
        var list = subProviders;
        addLootTables();
        list.add(new LootTableProvider.SubProviderEntry(() -> blocks, LootContextParamSets.BLOCK));
        list.add(new LootTableProvider.SubProviderEntry(() -> entities, LootContextParamSets.ENTITY));
        simple.forEach((set, table) -> list.add(new LootTableProvider.SubProviderEntry(() -> table::accept, set)));
        list.addAll(other);
        return list;
    }

    protected void validate(Map<ResourceLocation, LootTable> map, ValidationContext validationTracker) {
    }

    public void addBlock(Block block, LootTable.Builder table) {
        blocks.addBlock(block, table);
    }

    public void addBlock(Supplier<? extends Block> block, LootTable.Builder table) {
        blocks.addBlock(block.get(), table);
    }

    public void addBlock(Block block) {
        blocks.addBlock(block);
    }

    public void addBlock(Supplier<? extends Block> block) {
        blocks.addBlock(block.get());
    }

    public void addBlocks(Map<Block, LootTable.Builder> blocks) {
        blocks.forEach(this::addBlock);
    }

    public void addBlocks(Collection<? extends Block> blocks) {
        blocks.forEach(this::addBlock);
    }

    public void addBlocks(DeferredRegister<Block> register) {
        for (RegistryObject<Block> entry : register.getEntries()) {
            blocks.addBlock(entry.get());
        }
    }

    public void addEntity(EntityType<?> entity, LootTable.Builder table) {
        entities.addEntity(entity, table);
    }

    public void addEntities(Map<EntityType<?>, LootTable.Builder> tables) {
        entities.addEntities(tables);
    }

    public void addFishing(String name, LootTable.Builder table) {
        addLootTable(LootContextParamSets.FISHING, new ResourceLocation(modid, name), table);
    }

    public void addFishing(ResourceLocation name, LootTable.Builder table) {
        addLootTable(LootContextParamSets.FISHING, name, table);
    }

    public void addGift(String name, LootTable.Builder table) {
        addLootTable(LootContextParamSets.GIFT, new ResourceLocation(modid, name), table);
    }

    public void addGift(ResourceLocation name, LootTable.Builder table) {
        addLootTable(LootContextParamSets.GIFT, name, table);
    }

    public void addLootTable(LootContextParamSet param, ResourceLocation name, LootTable.Builder loot) {
        simple.computeIfAbsent(param, __ -> SimpleLootTables.create()).add(name, loot);
    }

    public void add(LootTableProvider.SubProviderEntry loot) {
        other.add(loot);
    }

    public void add(LootTableSubProvider provider, LootContextParamSet set) {
        other.add(new LootTableProvider.SubProviderEntry(() -> provider, set));
        if (provider instanceof BlockLootSubProvider) {
            blocks.removeAll(((IBlockLootSubProvider) provider).sinocore$getKnownBlocks());
        }
    }

    /// <editor-fold desc="Utility methods.">

    protected LootTable.Builder table(LootPool.Builder... pools) {
        LootTable.Builder builder = LootTable.lootTable();
        for (LootPool.Builder pool : pools) {
            builder.withPool(pool);
        }
        return builder;
    }

    protected LootPool.Builder dropSeeds(ItemLike seeds) {
        return dropSeeds("seeds", seeds);
    }

    protected LootPool.Builder dropSeeds(String name, ItemLike seeds) {
        return LootPool.lootPool()
                .name(name)
                .setRolls(ConstantValue.exactly(1))
                .add(LootItem.lootTableItem(seeds)
                        .apply(SetItemCountFunction.setCount(UniformGenerator.between(1, 2))));
    }

    protected LootPool.Builder dropWhenIntPropertyByChance(String name, Block block, ItemLike item,
                                                           IntegerProperty property, int value,
                                                           int minDropCount, int maxDropCount) {
        return LootPool.lootPool()
                .name(name)
                .setRolls(ConstantValue.exactly(1))
                .when(LootItemBlockStatePropertyCondition.hasBlockStateProperties(block).setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(property, value)))
                .add(LootItem.lootTableItem(item)
                        .apply(SetItemCountFunction.setCount(UniformGenerator.between(minDropCount, maxDropCount))));
    }

    protected LootPool.Builder dropWhenNotIntPropertyByChance(String name, Block block, ItemLike item,
                                                           IntegerProperty property, int value,
                                                           int minDropCount, int maxDropCount) {
        return LootPool.lootPool()
                .name(name)
                .setRolls(ConstantValue.exactly(1))
                .when(InvertedLootItemCondition.invert(LootItemBlockStatePropertyCondition.hasBlockStateProperties(block)
                        .setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(property, value))))
                .add(LootItem.lootTableItem(item)
                        .apply(SetItemCountFunction.setCount(UniformGenerator.between(minDropCount, maxDropCount))));
    }

    protected LootPool.Builder dropIngotByChance(ItemLike ingot, int min, int max) {
        return LootPool.lootPool()
                .name("ingot")
                .setRolls(ConstantValue.exactly(1))
                .add(LootItem.lootTableItem(ingot)
                        .apply(SetItemCountFunction.setCount(UniformGenerator.between(min, max))));
    }

    /// </editor-fold>
}
