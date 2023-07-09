package games.moegirl.sinocraft.sinocore.data.gen.loottable;

import games.moegirl.sinocraft.sinocore.data.gen.SimpleBlockLootTables;
import games.moegirl.sinocraft.sinocore.data.gen.SimpleEntityLootTables;
import games.moegirl.sinocraft.sinocore.data.gen.SimpleLootTables;
import games.moegirl.sinocraft.sinocore.tree.Tree;
import games.moegirl.sinocraft.sinocore.tree.TreeRegistry;
import net.minecraft.advancements.critereon.StatePropertiesPredicate;
import net.minecraft.data.PackOutput;
import net.minecraft.data.loot.LootTableProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.storage.loot.*;
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

import java.util.*;
import java.util.function.Supplier;

/**
 * @author luqin2007
 */
public abstract class LootTableProviderBase extends LootTableProvider {

    protected final String modid;

    private final SimpleBlockLootTables blocks = new SimpleBlockLootTables();
    private final SimpleEntityLootTables entities = new SimpleEntityLootTables();
    private final Map<LootContextParamSet, SimpleLootTables> simple = new HashMap<>();

    public LootTableProviderBase(PackOutput output, String modid) {
        super(output, Collections.emptySet(), Collections.emptyList());
        this.modid = modid;
    }

    public abstract void getTables(List<SubProviderEntry> tables);

    @Override
    public List<SubProviderEntry> getTables() {
        ArrayList<SubProviderEntry> list = new ArrayList<>();
        list.add(new SubProviderEntry(() -> blocks, LootContextParamSets.BLOCK));
        list.add(new SubProviderEntry(() -> entities, LootContextParamSets.ENTITY));
        for (Tree tree : TreeRegistry.getTrees(modid)) {
            blocks.removeAll(tree.getBlocks());
        }
        simple.forEach((set, tables) -> list.add(new SubProviderEntry(() -> tables::accept, set)));
        getTables(list);
        return list;
    }

    public String getProviderName() {
        return modid + ": Loot Tables";
    }

    @Override
    protected void validate(Map<ResourceLocation, LootTable> map, ValidationContext validationcontext) {
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

    public void addBlocks(DeferredRegister<Block> register) {
        for (RegistryObject<Block> entry : register.getEntries()) {
            blocks.add(entry.get());
        }
    }

    public SimpleBlockLootTables getBlocks() {
        return blocks;
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

    /// <editor-fold desc="Utility methods.">

    protected LootTable.Builder table(LootPool.Builder... pools) {
        LootTable.Builder builder = LootTable.lootTable();
        for (LootPool.Builder pool : pools) {
            builder.withPool(pool);
        }
        return builder;
    }

    protected LootPool.Builder dropSeeds(ItemLike seeds) {
        return LootPool.lootPool()
                .setRolls(ConstantValue.exactly(1))
                .add(LootItem.lootTableItem(seeds)
                        .apply(SetItemCountFunction.setCount(UniformGenerator.between(1, 2))));
    }

    protected LootPool.Builder dropWhenIntPropertyByChance(Block block, ItemLike item,
                                                           IntegerProperty property, int value,
                                                           int minDropCount, int maxDropCount) {
        return LootPool.lootPool()
                .setRolls(ConstantValue.exactly(1))
                .when(LootItemBlockStatePropertyCondition.hasBlockStateProperties(block).setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(property, value)))
                .add(LootItem.lootTableItem(item)
                        .apply(SetItemCountFunction.setCount(UniformGenerator.between(minDropCount, maxDropCount))));
    }

    protected LootPool.Builder dropWhenNotIntPropertyByChance(Block block, ItemLike item,
                                                              IntegerProperty property, int value,
                                                              int minDropCount, int maxDropCount) {
        return LootPool.lootPool()
                .setRolls(ConstantValue.exactly(1))
                .when(InvertedLootItemCondition.invert(LootItemBlockStatePropertyCondition.hasBlockStateProperties(block)
                        .setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(property, value))))
                .add(LootItem.lootTableItem(item)
                        .apply(SetItemCountFunction.setCount(UniformGenerator.between(minDropCount, maxDropCount))));
    }

    protected LootPool.Builder dropIngotByChance(ItemLike ingot, int min, int max) {
        return LootPool.lootPool()
                .setRolls(ConstantValue.exactly(1))
                .add(LootItem.lootTableItem(ingot)
                        .apply(SetItemCountFunction.setCount(UniformGenerator.between(min, max))));
    }

    /// </editor-fold>
}
