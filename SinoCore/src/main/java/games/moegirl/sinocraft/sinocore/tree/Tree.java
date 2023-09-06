package games.moegirl.sinocraft.sinocore.tree;

import games.moegirl.sinocraft.sinocore.tab.TabItemGenerator;
import games.moegirl.sinocraft.sinocore.tab.TabsRegistry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.item.AxeItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
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

    private final Block[] axeBlocks = {null, null, null, null};

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

    public static TreeBuilder builder(ResourceLocation name) {
        return new TreeBuilder(name);
    }

    public static TreeBuilder builder(String modid, String name) {
        return builder(new ResourceLocation(modid, name));
    }

    public void register(DeferredRegister<Block> blockRegister, DeferredRegister<BlockEntityType<?>> blockEntityRegister, DeferredRegister<Item> itemRegister) {
        makeDefaultBlocks(blockRegister);
        makeDefaultBlockEntities(blockEntityRegister);
        makeDefaultBlockItems(itemRegister);
        fillCreativeTabs();
    }

    private void makeDefaultBlocks(DeferredRegister<Block> blockRegister) {
        builder.getBlockFactories().entrySet().stream()
                .filter(p -> p.getKey().hasBlock())
                .map(p -> {
                    String key = p.getKey().makeRegistryName(getName());
                    var ro = p.getValue().blockBuilder.apply(blockRegister, key, this, ((type, block) -> {
                        switch (type) {
                            case LOG -> axeBlocks[0] = block;
                            case STRIPPED_LOG -> axeBlocks[1] = block;
                            case LOG_WOOD -> axeBlocks[2] = block;
                            case STRIPPED_LOG_WOOD -> axeBlocks[3] = block;
                        }

                        if (axeBlocks[0] != null && axeBlocks[1] != null) {
                            registerAxeInternal(axeBlocks[0], axeBlocks[1]);
                            axeBlocks[0] = null;
                            axeBlocks[1] = null;
                        } else if (axeBlocks[2] != null && axeBlocks[3] != null) {
                            registerAxeInternal(axeBlocks[2], axeBlocks[3]);
                            axeBlocks[2] = null;
                            axeBlocks[3] = null;
                        }
                    }));
                    return Map.entry(p.getKey(), ro);
                })
                .forEach(p -> blocks.put(p.getKey(), p.getValue()));
    }

    private void registerAxeInternal(Block in, Block out) {
        try {
            AxeItem.STRIPPABLES.put(in, out);
        } catch (Exception e) {
            AxeItem.STRIPPABLES = new HashMap<>(AxeItem.STRIPPABLES);
            AxeItem.STRIPPABLES.put(in, out);
        }
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
}
