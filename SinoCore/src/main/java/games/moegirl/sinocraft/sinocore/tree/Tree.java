package games.moegirl.sinocraft.sinocore.tree;

import games.moegirl.sinocraft.sinocore.utility.FloatModifier;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.grower.AbstractTreeGrower;
import net.minecraft.world.level.material.MaterialColor;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

import java.util.*;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * 树，主要包括：<br>
 * 1. 树苗及其世界生成；<br>
 * 2. 原木（Log）及其去皮方块，木（Wood）及其去皮方块，树叶等附属方块及其 Tag；<br>
 * 3. 盆栽的对应树苗版本；<br>
 *
 * <p>使用 {@link Tree#builder(ResourceLocation)} 创建，同时需要 {@link TreeRegister} 注册相关内容</p>
 *
 * @see TreeBuilder
 * @see games.moegirl.sinocraft.sinocore.old.woodwork.Woodwork
 */
public class Tree {

    private static final Map<ResourceLocation, Tree> TREE_BY_NAME = new HashMap<>();

    /**
     * 检查该 key 是否存在
     */
    public static boolean exist(ResourceLocation name) {
        return TREE_BY_NAME.containsKey(name);
    }

    /**
     * 获取对应 Tree
     */
    public static Tree get(ResourceLocation name) {
        return Objects.requireNonNull(TREE_BY_NAME.get(name));
    }

    /**
     * 获取对应 Tree
     */
    public static Tree get(String name) {
        return get(new ResourceLocation(name));
    }

    /**
     * 创建 Tree
     */
    public static TreeBuilder builder(ResourceLocation name) {
        return new TreeBuilder(name);
    }

    /**
     * 创建 Tree
     */
    public static TreeBuilder builder(String modid, String name) {
        return builder(new ResourceLocation(modid, name));
    }

    /**
     * 创建 Tree
     * <p>modid 使用当前正在加载的 mod</p>
     *
     * @see ModLoadingContext#get()
     */
    public static TreeBuilder builder(String name) {
        return builder(new ResourceLocation(ModLoadingContext.get().getActiveNamespace(), name));
    }

    public final RegistryObject<SaplingBlock> sapling;
    public final RegistryObject<RotatedPillarBlock> log;
    public final RegistryObject<RotatedPillarBlock> strippedLog;
    public final RegistryObject<RotatedPillarBlock> wood;
    public final RegistryObject<RotatedPillarBlock> strippedWood;
    public final RegistryObject<LeavesBlock> leaves;
    public final RegistryObject<FlowerPotBlock> pottedSapling;
    private final Set<Block> allBlocks = new HashSet<>();
    private final Set<Item> allItems = new HashSet<>();

    TagKey<Block> tagLogs;
    TagKey<Item> tagItemLogs;
    TreeRegister register = new TreeRegister(this);
    private final BuilderProperties properties;

    Tree(TreeBuilder builder, DeferredRegister<Block> blocks, DeferredRegister<Item> items) {
        properties = new BuilderProperties(builder.name, builder.grower, builder.strengthModifier,
                builder.saplingTabs, builder.leavesTabs,
                builder.logTabs, builder.strippedLogTabs,
                builder.woodTabs, builder.strippedWoodTabs,
                builder.saplingSound, builder.leavesSound,
                builder.topLogColor, builder.barkLogColor,
                builder.topStrippedLogColor, builder.barkStrippedLogColor,
                builder.woodColor, builder.strippedWoodColor);

        if (builder.grower instanceof TreeSaplingGrower tsg) {
            tsg.setTree(this);
        }

        sapling = register(blocks, "sapling", asSupplier(builder.sapling, allBlocks));
        pottedSapling = register(blocks, "potted", "sapling", asSupplier(builder.pottedSapling, allBlocks));
        log = register(blocks, "log", asSupplier(builder.log, allBlocks));
        strippedLog = register(blocks, "stripped", "log", asSupplier(builder.strippedLog, allBlocks));
        wood = register(blocks, "wood", asSupplier(builder.wood, allBlocks));
        strippedWood = register(blocks, "stripped", "wood", asSupplier(builder.strippedWood, allBlocks));
        leaves = register(blocks, "leaves", asSupplier(builder.leaves, allBlocks));

        register(items, sapling, asSupplier(builder.saplingItem, allItems));
        register(items, log, asSupplier(builder.logItem, allItems));
        register(items, strippedLog, asSupplier(builder.strippedLogItem, allItems));
        register(items, wood, asSupplier(builder.woodItem, allItems));
        register(items, strippedWood, asSupplier(builder.strippedWoodItem, allItems));
        register(items, leaves, asSupplier(builder.leavesItem, allItems));

        tagLogs = BlockTags.create(new ResourceLocation(properties.name.getNamespace(),
                (properties.name.getPath() + "_logs").toLowerCase(Locale.ROOT)));
        tagItemLogs = ItemTags.create(tagLogs().location());

        TREE_BY_NAME.put(properties.name, this);
    }

    private <T> Supplier<T> asSupplier(Function<Tree, T> factory, Set<? super T> collector) {
        return () -> {
            T value = factory.apply(this);
            collector.add(value);
            return value;
        };
    }

    private <T, V extends T> RegistryObject<V> register(DeferredRegister<T> register, String prefix, String postfix, Supplier<V> supplier) {
        return register.register(prefix + "_" + properties.name.getPath() + "_" + postfix, supplier);
    }

    private <T, V extends T> RegistryObject<V> register(DeferredRegister<T> register, String postfix, Supplier<V> supplier) {
        return register.register(properties.name.getPath() + "_" + postfix, supplier);
    }

    private void register(DeferredRegister<Item> item, RegistryObject<? extends Block> block, Supplier<? extends Item> supplier) {
        item.register(block.getId().getPath(), supplier);
    }

    public SaplingBlock sapling() {
        return sapling.get();
    }

    public RotatedPillarBlock log() {
        return log.get();
    }

    public RotatedPillarBlock strippedLog() {
        return strippedLog.get();
    }

    public RotatedPillarBlock wood() {
        return wood.get();
    }

    public RotatedPillarBlock strippedWood() {
        return strippedWood.get();
    }

    public FlowerPotBlock pottedSapling() {
        return pottedSapling.get();
    }

    public LeavesBlock leaves() {
        return leaves.get();
    }

    public Set<Block> allBlocks() {
        return Set.copyOf(allBlocks);
    }

    public Set<Item> allItems() {
        return Set.copyOf(allItems);
    }

    public ResourceLocation name() {
        return properties.name;
    }

    public TagKey<Block> tagLogs() {
        return Objects.requireNonNull(tagLogs);
    }

    public TagKey<Item> tagItemLogs() {
        return Objects.requireNonNull(tagItemLogs);
    }

    public TreeRegister register() {
        return register;
    }

    public void registerAll(IEventBus bus) {
        register().registerEvents(bus);
        new TreeDataProvider().register(bus, register());
    }

    public BuilderProperties properties() {
        return properties;
    }

    public record BuilderProperties(ResourceLocation name, AbstractTreeGrower grower, FloatModifier strengthModifier,
                                    List<CreativeModeTab> saplingTabs, List<CreativeModeTab> leavesTabs,
                                    List<CreativeModeTab> logTabs, List<CreativeModeTab> strippedLogTabs,
                                    List<CreativeModeTab> woodTabs, List<CreativeModeTab> strippedWoodTabs,
                                    SoundType saplingSound, SoundType leavesSound,
                                    MaterialColor topLogColor, MaterialColor barkLogColor,
                                    MaterialColor topStrippedLogColor, MaterialColor barkStrippedLogColor,
                                    MaterialColor woodColor, MaterialColor strippedWoodColor) {
    }
}
