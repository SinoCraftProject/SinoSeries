package games.moegirl.sinocraft.sinocore.old.data.base;

import games.moegirl.sinocraft.sinocore.mixin_inter.IBlockLoot;
import net.minecraft.data.PackOutput;
import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.data.loot.LootTableProvider;
import net.minecraft.data.loot.LootTableSubProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.ValidationContext;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSet;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

import java.util.*;
import java.util.function.Supplier;

public abstract class LootTableProviderBase extends LootTableProvider {
    protected final String modID;

    private final SimpleBlockLootTables blocks = new SimpleBlockLootTables();
    private final SimpleEntityLootTables entities = new SimpleEntityLootTables();
    private final Map<LootContextParamSet, SimpleLootTables> simple = new HashMap<>();
    private final List<SubProviderEntry> other = new ArrayList<>();

    public LootTableProviderBase(PackOutput output, String modID) {
        super(output, new HashSet<>(), new ArrayList<>());
        this.modID = modID;
    }

    public LootTableProviderBase(PackOutput output, String modID, DeferredRegister<Block> blocks) {
        this(output, modID);
        addBlocks(blocks);
    }

    /**
     * Return the block loot table
     */
    public abstract void addLootTables();

    @Override
    public List<SubProviderEntry> getTables() {
        var list = super.getTables();
        addLootTables();
        list.add(new SubProviderEntry(() -> blocks, LootContextParamSets.BLOCK));
        list.add(new SubProviderEntry(() -> entities, LootContextParamSets.ENTITY));
        simple.forEach((set, table) -> list.add(new SubProviderEntry(() -> table::accept, set)));
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
        addLootTable(LootContextParamSets.FISHING, new ResourceLocation(modID, name), table);
    }

    public void addFishing(ResourceLocation name, LootTable.Builder table) {
        addLootTable(LootContextParamSets.FISHING, name, table);
    }

    public void addGift(String name, LootTable.Builder table) {
        addLootTable(LootContextParamSets.GIFT, new ResourceLocation(modID, name), table);
    }

    public void addGift(ResourceLocation name, LootTable.Builder table) {
        addLootTable(LootContextParamSets.GIFT, name, table);
    }

    public void addLootTable(LootContextParamSet param, ResourceLocation name, LootTable.Builder loot) {
        simple.computeIfAbsent(param, __ -> SimpleLootTables.create()).add(name, loot);
    }

    public void add(SubProviderEntry loot) {
        other.add(loot);
    }

    public void add(LootTableSubProvider provider, LootContextParamSet set) {
        other.add(new SubProviderEntry(() -> provider, set));
        if (provider instanceof BlockLootSubProvider) {
            blocks.removeAll(((IBlockLoot) provider).sinocoreGetKnownBlocks());
        }
    }
}
