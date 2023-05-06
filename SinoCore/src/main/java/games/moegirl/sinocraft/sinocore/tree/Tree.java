package games.moegirl.sinocraft.sinocore.tree;

import games.moegirl.sinocraft.sinocore.item.tab.TabsRegistry;
import games.moegirl.sinocraft.sinocore.tree.event.SCTreeTabsBuildListener;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.grower.AbstractTreeGrower;
import net.minecraft.world.level.block.state.properties.BlockSetType;
import net.minecraft.world.level.block.state.properties.WoodType;
import net.minecraft.world.level.levelgen.feature.configurations.TreeConfiguration;
import net.minecraftforge.common.util.Lazy;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

import java.util.*;

public class Tree {

    public final ResourceLocation name;

    private final WoodType woodType;
    private final BlockSetType blockSetType;

    private final EnumMap<TreeBlockType, RegistryObject<? extends Block>> blocks = new EnumMap<>(TreeBlockType.class);
    private final EnumMap<TreeBlockType, RegistryObject<? extends Item>> items = new EnumMap<>(TreeBlockType.class);
    private final TreeLanguages translator;
    private final Lazy<AbstractTreeGrower> grower;
    private final Lazy<TreeConfiguration> configuration;

    private final TreeBuilder builder;

    private final ResourceLocation defaultLogTag;

    Tree(TreeBuilder builder) {
        this.name = builder.name;
        this.translator = builder.languages;
        this.grower = Lazy.of(() -> builder.grower.apply(this));
        this.configuration = Lazy.of(() -> builder.configuration.apply(this));

        // todo may be should set in builder ?
        blockSetType = new BlockSetType(name.toString(), SoundType.WOOD,
                SoundEvents.WOODEN_DOOR_CLOSE, SoundEvents.WOODEN_DOOR_OPEN,
                SoundEvents.WOODEN_TRAPDOOR_CLOSE, SoundEvents.WOODEN_TRAPDOOR_OPEN,
                SoundEvents.WOODEN_PRESSURE_PLATE_CLICK_OFF, SoundEvents.WOODEN_PRESSURE_PLATE_CLICK_ON,
                SoundEvents.WOODEN_BUTTON_CLICK_OFF, SoundEvents.WOODEN_BUTTON_CLICK_ON);
        BlockSetType.register(blockSetType);
        woodType = new WoodType(name.toString(), blockSetType);
        WoodType.register(woodType);

        defaultLogTag = new ResourceLocation(name.getNamespace(), name.getPath() + "_logs");

        this.builder = builder;
        TreeRegistry.getRegistry().computeIfAbsent(name.getNamespace(), id -> new ArrayList<>()).add(this);
    }

    public void register(DeferredRegister<Block> blockRegister, DeferredRegister<Item> itemRegister,
                         SCTreeTabsBuildListener tabsListener) {
        makeDefaultBlocks(blockRegister);
        makeDefaultBlockItems(itemRegister);
        fillCreativeTabs(tabsListener);
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

    private void fillCreativeTabs(SCTreeTabsBuildListener tabsListener) {
        builder.getBlockFactories().forEach((type, factory) -> {
            if (type.hasItem()) {
                RegistryObject<? extends Item> item = items.get(type);

                if (factory.fillDefaultTabs) {
                    tabsListener.addDefaultTabs(type, item);
                }

                // todo 使用 ResourceLocation 的版本只支持 SC 的 Tab 注册，，，这样好吗？
                if (!factory.tabs.isEmpty()) {
                    for (ResourceLocation tab : factory.tabs) {
                        TabsRegistry.get(tab).add(item);
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

    public Collection<RegistryObject<? extends Block>> getAllBlocks() {
        return Collections.unmodifiableCollection(blocks.values());
    }

    public Collection<RegistryObject<? extends Item>> getAllItems() {
        return Collections.unmodifiableCollection(items.values());
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
