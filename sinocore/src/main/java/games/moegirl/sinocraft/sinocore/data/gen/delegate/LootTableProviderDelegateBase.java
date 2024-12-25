package games.moegirl.sinocraft.sinocore.data.gen.delegate;

import games.moegirl.sinocraft.sinocore.data.gen.loot.IBlockLootTableSubProvider;
import games.moegirl.sinocraft.sinocore.data.gen.loot.IEntityLootTableSubProvider;
import games.moegirl.sinocraft.sinocore.data.gen.loot.SimpleLootTableSubProvider;
import games.moegirl.sinocraft.sinocore.data.gen.loot.AbstractLootTableProvider;
import games.moegirl.sinocraft.sinocore.registry.IRegRef;
import games.moegirl.sinocraft.sinocore.registry.IRegistry;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.DataProvider;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSet;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.function.Supplier;

public class LootTableProviderDelegateBase extends ProviderDelegateBase<LootTableProviderDelegateBase> {

    protected final IBlockLootTableSubProvider blocks;
    protected final IEntityLootTableSubProvider entities;
    // todo
    // LootContextParamSet 只是包含了一系列对象标记的集合，语义上不能表示 SubProvider 类型
    // 更换成 List<Pair<LootContextParamSet, SimpleLootTableSubProvider>> 是否更好一些
    protected final Map<LootContextParamSet, SimpleLootTableSubProvider> simple = new HashMap<>();
    protected final String modId;

    protected LootTableProviderDelegateBase(DataProvider provider, String modId, CompletableFuture<HolderLookup.Provider> registries) {
        super(provider);
        this.blocks = AbstractLootTableProvider.createBlockSubProvider(registries.getNow(null));
        this.entities = AbstractLootTableProvider.createEntitySubProvider(registries.getNow(null));
        this.modId = modId;
    }

    public void addBlock(Block block, LootTable.Builder table) {
        blocks.add(block, table);
    }

    public void addBlock(Supplier<? extends Block> block, LootTable.Builder table) {
        blocks.add(block.get(), table);
    }

    public void addBlock(Block block) {
        blocks.add(block);
    }

    public void addBlock(Supplier<? extends Block> block) {
        blocks.add(block.get());
    }

    public void addBlocks(Map<Block, LootTable.Builder> blocks) {
        blocks.forEach(this::addBlock);
    }

    public void addBlocks(Collection<? extends Block> blocks) {
        blocks.forEach(this::addBlock);
    }

    public void addBlocks(IRegistry<Block> register) {
        for (IRegRef<Block> entry : register.getEntries()) {
            blocks.add(entry.get());
        }
    }

    public void addEntity(EntityType<?> entity, LootTable.Builder table) {
        entities.add(entity, table);
    }

    public void addEntities(Map<EntityType<?>, LootTable.Builder> tables) {
        tables.forEach(entities::add);
    }

    public void addFishing(String name, LootTable.Builder table) {
        addLootTable(LootContextParamSets.FISHING, ResourceLocation.fromNamespaceAndPath(modId, name), table);
    }

    public void addFishing(ResourceLocation name, LootTable.Builder table) {
        addLootTable(LootContextParamSets.FISHING, name, table);
    }

    public void addGift(String name, LootTable.Builder table) {
        addLootTable(LootContextParamSets.GIFT, ResourceLocation.fromNamespaceAndPath(modId, name), table);
    }

    public void addGift(ResourceLocation name, LootTable.Builder table) {
        addLootTable(LootContextParamSets.GIFT, name, table);
    }

    public void addLootTable(LootContextParamSet param, ResourceLocation name, LootTable.Builder loot) {
        this.addLootTable(param, ResourceKey.create(Registries.LOOT_TABLE, name), loot);
    }

    public void addLootTable(LootContextParamSet param, ResourceKey<LootTable> key, LootTable.Builder loot) {
        simple.computeIfAbsent(param, __ -> new SimpleLootTableSubProvider()).add(key, loot);
    }

    public IBlockLootTableSubProvider getBlocks() {
        return blocks;
    }

    // <editor-fold desc="Utility methods.">
    // todo 是否需要把这些移动到特定 SubProvider 中 ?

    public LootTable.Builder table(LootPool.Builder... pools) {
        LootTable.Builder builder = LootTable.lootTable();
        for (LootPool.Builder pool : pools) {
            builder.withPool(pool);
        }
        return builder;
    }

    public LootPool.Builder dropSeeds(ItemLike seeds) {
        return LootPool.lootPool()
                .setRolls(ConstantValue.exactly(1))
                .add(LootItem.lootTableItem(seeds).apply(SetItemCountFunction.setCount(UniformGenerator.between(1, 2))));
    }

    public LootPool.Builder dropWhenIntPropertyByChance(Block block, ItemLike item,
                                                        IntegerProperty property, int value, int min, int max) {
        return LootPool.lootPool()
                .setRolls(ConstantValue.exactly(1))
                .when(blocks.ifBlockIs(block, property, value))
                .add(LootItem.lootTableItem(item).apply(SetItemCountFunction.setCount(UniformGenerator.between(min, max))));
    }

    public LootPool.Builder dropWhenNotIntPropertyByChance(Block block, ItemLike item,
                                                           IntegerProperty property, int value, int min, int max) {
        return LootPool.lootPool()
                .setRolls(ConstantValue.exactly(1))
                .when(blocks.ifNot(blocks.ifBlockIs(block, property, value)))
                .add(LootItem.lootTableItem(item).apply(SetItemCountFunction.setCount(UniformGenerator.between(min, max))));
    }

    public LootPool.Builder dropIngotByChance(ItemLike ingot, int min, int max) {
        return LootPool.lootPool()
                .setRolls(ConstantValue.exactly(1))
                .add(LootItem.lootTableItem(ingot).apply(SetItemCountFunction.setCount(UniformGenerator.between(min, max))));
    }

    // </editor-fold>
}
