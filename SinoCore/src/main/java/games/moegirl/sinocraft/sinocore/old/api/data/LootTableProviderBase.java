package games.moegirl.sinocraft.sinocore.old.api.data;

import com.mojang.datafixers.util.Pair;
import games.moegirl.sinocraft.sinocore.api.data.base.SimpleBlockLootTables;
import games.moegirl.sinocraft.sinocore.api.data.base.SimpleEntityLootTables;
import games.moegirl.sinocraft.sinocore.api.data.base.SimpleLootTables;
import games.moegirl.sinocraft.sinocore.api.mixin.IBlockLoot;
import games.moegirl.sinocraft.sinocore.api.utility.LootBuilder;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.loot.BlockLoot;
import net.minecraft.data.loot.EntityLoot;
import net.minecraft.data.loot.LootTableProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.ValidationContext;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSet;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraftforge.registries.DeferredRegister;

import javax.annotation.Nonnull;
import java.util.*;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Supplier;

public abstract class LootTableProviderBase extends LootTableProvider {
    protected final String modID;

    private final SimpleBlockLootTables blocks = new SimpleBlockLootTables();
    private final SimpleEntityLootTables entities = new SimpleEntityLootTables();
    private final Map<LootContextParamSet, SimpleLootTables> simple = new HashMap<>();
    private final List<Pair<Supplier<Consumer<BiConsumer<ResourceLocation, LootTable.Builder>>>, LootContextParamSet>> other = new ArrayList<>();

    public LootTableProviderBase(DataGenerator pGenerator, String modID) {
        super(pGenerator);
        this.modID = modID;
    }

    public LootTableProviderBase(DataGenerator pGenerator, String modID, DeferredRegister<Block> blocks) {
        this(pGenerator, modID);
        addBlocks(blocks);
    }

    @Nonnull
    @Override
    public String getName() {
        return super.getName() + ":" + modID;
    }

    /**
     * Return the block loot table
     */
    public abstract void addLootTables();

    @Override
    protected List<Pair<Supplier<Consumer<BiConsumer<ResourceLocation, LootTable.Builder>>>, LootContextParamSet>> getTables() {
        List<Pair<Supplier<Consumer<BiConsumer<ResourceLocation, LootTable.Builder>>>, LootContextParamSet>> list = new ArrayList<>();
        addLootTables();
        list.add(Pair.of(() -> blocks, LootContextParamSets.BLOCK));
        list.add(Pair.of(() -> entities, LootContextParamSets.ENTITY));
        simple.forEach((set, table) -> list.add(Pair.of(() -> table, set)));
        list.addAll(other);
        return list;
    }

    @Override
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
        blocks.addBlocks(register);
    }

    public void addBlocks(Consumer<BiConsumer<ResourceLocation, LootTable.Builder>> loot, Iterable<? extends Block> knownBlocks) {
        addCustomLoots(loot, LootContextParamSets.BLOCK);
        blocks.removeAll(knownBlocks);
    }

    public void addBlockTable(BlockLoot loot) {
        addCustomLoots(loot, LootContextParamSets.BLOCK);
        blocks.removeAll(((IBlockLoot) loot).scGetKnownBlocks());
    }

    public void addEntity(EntityType<?> entity, LootTable.Builder table) {
        entities.addEntity(entity, table);
    }

    public void addEntities(Map<EntityType<?>, LootTable.Builder> tables) {
        entities.addEntities(tables);
    }

    public void addEntityTable(EntityLoot loot) {
        addCustomLoots(loot, LootContextParamSets.ENTITY);
    }

    public void addFishing(String name, LootTable.Builder table) {
        addLootTable(LootContextParamSets.FISHING, new ResourceLocation(modID, name), table);
    }

    public void addFishing(ResourceLocation name, LootTable.Builder table) {
        addLootTable(LootContextParamSets.FISHING, name, table);
    }

    public void addFishingTable(Consumer<BiConsumer<ResourceLocation, LootTable.Builder>> loot) {
        addCustomLoots(loot, LootContextParamSets.FISHING);
    }

    public void addGift(String name, LootTable.Builder table) {
        addLootTable(LootContextParamSets.GIFT, new ResourceLocation(modID, name), table);
    }

    public void addGift(ResourceLocation name, LootTable.Builder table) {
        addLootTable(LootContextParamSets.GIFT, name, table);
    }

    public void addGiftTable(Consumer<BiConsumer<ResourceLocation, LootTable.Builder>> loot) {
        addCustomLoots(loot, LootContextParamSets.GIFT);
    }

    public void addLootTable(LootContextParamSet param, ResourceLocation name, LootTable.Builder loot) {
        simple.computeIfAbsent(param, __ -> SimpleLootTables.create()).add(name, loot);
    }

    public void addCustomLoots(Consumer<BiConsumer<ResourceLocation, LootTable.Builder>> loot, LootContextParamSet param) {
        other.add(Pair.of(() -> loot, param));
    }

    /**
     * @deprecated waiting to implement
     */
    @Deprecated
    public LootBuilder createTable() {
        return new LootBuilder();
    }
}
