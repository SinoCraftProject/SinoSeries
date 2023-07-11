package games.moegirl.sinocraft.sinocore.tree;

import games.moegirl.sinocraft.sinocore.SinoCore;
import games.moegirl.sinocraft.sinocore.handler.BlockStrippingHandler;
import games.moegirl.sinocraft.sinocore.tab.TabItemGenerator;
import games.moegirl.sinocraft.sinocore.tab.TabsRegistry;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.grower.AbstractTreeGrower;
import net.minecraft.world.level.block.state.properties.BlockSetType;
import net.minecraft.world.level.block.state.properties.WoodType;
import net.minecraft.world.level.levelgen.feature.configurations.TreeConfiguration;
import net.minecraftforge.common.util.Lazy;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;
import org.apache.commons.lang3.tuple.Triple;

import javax.annotation.Nullable;
import java.util.*;
import java.util.stream.Collectors;

/**
 * todo
 * <ul>
 *     <li>修复：猫无法坐在箱子上：{@link net.minecraft.world.entity.ai.goal.CatSitOnBlockGoal}</li>
 *     <li>修复：马：{@link net.minecraft.world.entity.animal.horse.AbstractChestedHorse}</li>
 * </ul>
 */
public class Tree {

    static Map<String, Triple<DeferredRegister<Block>, DeferredRegister<BlockEntityType<?>>, DeferredRegister<Item>>> REGISTRIES = new HashMap<>();
    public static void defaultRegistry(String modid,
                                       @Nullable DeferredRegister<Block> blockRegister,
                                       @Nullable DeferredRegister<BlockEntityType<?>> blockEntityRegister,
                                       @Nullable DeferredRegister<Item> itemRegister) {
        REGISTRIES.put(modid, Triple.of(blockRegister, blockEntityRegister, itemRegister));
    }

    public static final ResourceKey<Registry<Tree>> TREE_REGISTER = ResourceKey.createRegistryKey(new ResourceLocation(SinoCore.MODID, "tree"));

    public final ResourceLocation name;

    private final WoodType woodType;
    private final BlockSetType blockSetType;

    private final EnumMap<TreeBlockType, RegistryObject<? extends Block>> blocks = new EnumMap<>(TreeBlockType.class);
    private final EnumMap<TreeBlockType, RegistryObject<? extends Item>> items = new EnumMap<>(TreeBlockType.class);
    private final EnumMap<TreeBlockType, RegistryObject<BlockEntityType<?>>> blockEntities = new EnumMap<>(TreeBlockType.class);

    private final TreeLanguages translator;
    private final Lazy<AbstractTreeGrower> grower;
    private final Lazy<TreeConfiguration> configuration;

    private final TreeBuilder builder;

    private final ResourceLocation defaultLogTag;

    Tree(TreeBuilder builder) {
        this.name = builder.name;
        this.translator = builder.languages;
        this.grower = Lazy.of(() -> builder.grower.apply(this));
        this.configuration = Lazy.of(() -> builder.grower.apply(this).getConfiguration(this));

        // todo may be should set in builder ?
        blockSetType = new BlockSetType(name.toString(), true, SoundType.WOOD,
                SoundEvents.WOODEN_DOOR_CLOSE, SoundEvents.WOODEN_DOOR_OPEN,
                SoundEvents.WOODEN_TRAPDOOR_CLOSE, SoundEvents.WOODEN_TRAPDOOR_OPEN,
                SoundEvents.WOODEN_PRESSURE_PLATE_CLICK_OFF, SoundEvents.WOODEN_PRESSURE_PLATE_CLICK_ON,
                SoundEvents.WOODEN_BUTTON_CLICK_OFF, SoundEvents.WOODEN_BUTTON_CLICK_ON);
        synchronized (Tree.class) {
            BlockSetType.register(blockSetType);
        }
        woodType = new WoodType(name.toString(), blockSetType);
        WoodType.register(woodType);

        defaultLogTag = new ResourceLocation(name.getNamespace(), name.getPath() + "_logs");

        this.builder = builder;
        TreeRegistry.getRegistry().computeIfAbsent(name.getNamespace(), id -> new ArrayList<>()).add(this);
    }

    public void register(DeferredRegister<Block> blockRegister, DeferredRegister<BlockEntityType<?>> blockEntityRegister, DeferredRegister<Item> itemRegister) {
        makeDefaultBlocks(blockRegister);
        makeDefaultBlockEntities(blockEntityRegister);
        makeDefaultBlockItems(itemRegister);
        fillCreativeTabs();

        BlockStrippingHandler.registerStripping(TreeBlockType.LOG.makeResourceLoc(getName()), getBlockObj(TreeBlockType.LOG), getBlockObj(TreeBlockType.STRIPPED_LOG), () -> Items.AIR);
        BlockStrippingHandler.registerStripping(TreeBlockType.LOG_WOOD.makeResourceLoc(getName()), getBlockObj(TreeBlockType.LOG_WOOD), getBlockObj(TreeBlockType.STRIPPED_LOG_WOOD), () -> Items.AIR);
    }

    private void makeDefaultBlocks(DeferredRegister<Block> blockRegister) {
        builder.getBlockFactories().entrySet().stream()
                .filter(p -> p.getKey().hasBlock())
                .map(p -> {
                    String key = p.getKey().makeRegistryName(getName());
                    RegistryObject<? extends Block> ro = p.getValue().blockBuilder.apply(blockRegister, key, this);
                    return Map.entry(p.getKey(), ro);
                })
                .forEach(p -> blocks.put(p.getKey(), p.getValue()));
    }

    private void makeDefaultBlockEntities(DeferredRegister<BlockEntityType<?>> blockEntityRegister) {
        builder.getBlockFactories().entrySet().stream()
                .filter(p -> p.getValue().blockEntityBuilder != null)
                .map(p -> {
                    String key = p.getKey().makeRegistryName(getName());
                    RegistryObject<BlockEntityType<?>> ro = p.getValue().blockEntityBuilder.apply(blockEntityRegister, key, this);
                    return Map.entry(p.getKey(), ro);
                })
                .forEach(p -> blockEntities.put(p.getKey(), p.getValue()));
    }

    private void makeDefaultBlockItems(DeferredRegister<Item> itemRegister) {
        builder.getBlockFactories().entrySet().stream()
                .filter(p -> p.getKey().hasItem())
                .map(p -> {
                    String key = p.getKey().makeRegistryName(getName());
                    RegistryObject<? extends Item> ro = p.getValue().itemBuilder.apply(itemRegister, key, this);
                    return Map.entry(p.getKey(), ro);
                })
                .forEach(p -> items.put(p.getKey(), p.getValue()));
    }

    private void fillCreativeTabs() {
        builder.getBlockFactories().forEach((type, factory) -> {
            if (type.hasItem()) {
                RegistryObject<? extends Item> item = items.get(type);

                if (factory.fillDefaultTabs) {
                    for (ResourceKey<CreativeModeTab> tab : TreeUtilities.defaultTabs(type)) {
                        TabsRegistry.items(tab).addItem(item);
                    }
                }

                if (!factory.tabs.isEmpty()) {
                    for (TabItemGenerator tab : factory.tabs) {
                        tab.addItem(item);
                    }
                }
            }
        });
    }

    public WoodType getWoodType() {
        return woodType;
    }

    public BlockSetType getBlockSetType() {
        return blockSetType;
    }

    public ResourceLocation getName() {
        return name;
    }

    public <T extends Block> T getBlock(TreeBlockType treeBlockType) {
        //noinspection unchecked
        return (T) blocks.get(treeBlockType).get();
    }

    public <T extends Block> RegistryObject<T> getBlockObj(TreeBlockType treeBlockType) {
        //noinspection unchecked
        return (RegistryObject<T>) blocks.get(treeBlockType);
    }

    public List<Block> getBlocks() {
        return blocks.values().stream()
                .map(RegistryObject::get)
                .collect(Collectors.toList());
    }

    public boolean hasBlockEntity(TreeBlockType treeBlockType) {
        return blockEntities.containsKey(treeBlockType);
    }

    public <T extends BlockEntity> BlockEntityType<T> getBlockEntityType(TreeBlockType treeBlockType) {
        //noinspection unchecked
        return (BlockEntityType<T>) blockEntities.get(treeBlockType).get();
    }

    public <T extends BlockEntity> RegistryObject<BlockEntityType<T>> getBlockEntityTypeObj(TreeBlockType treeBlockType) {
        //noinspection unchecked
        return (RegistryObject<BlockEntityType<T>>) (RegistryObject<?>) blockEntities.get(treeBlockType);
    }

    public <T extends Item> T getItem(TreeBlockType treeBlockType) {
        //noinspection unchecked
        return (T) items.get(treeBlockType).get();
    }

    public <T extends Item> RegistryObject<T> getItemObj(TreeBlockType treeBlockType) {
        //noinspection unchecked
        return (RegistryObject<T>) items.get(treeBlockType);
    }

    public AbstractTreeGrower getGrower() {
        return grower.get();
    }

    public Optional<TreeConfiguration> getFeaturedConfiguration() {
        return Optional.ofNullable(configuration.get());
    }

    public TreeLanguages getTranslator() {
        return translator;
    }

    public ResourceLocation getDefaultLogTag() {
        return defaultLogTag;
    }

    public TreeBuilder getBuilder() {
        return builder;
    }

    public static TreeBuilder builder(ResourceLocation name) {
        return new TreeBuilder(name);
    }

    public static TreeBuilder builder(String modid, String name) {
        return builder(new ResourceLocation(modid, name));
    }
}
