package games.moegirl.sinocraft.sinocore.tree;

import games.moegirl.sinocraft.sinocore.block.ModLeavesBlock;
import games.moegirl.sinocraft.sinocore.block.ModLogBlock;
import games.moegirl.sinocraft.sinocore.block.ModSaplingBlock;
import games.moegirl.sinocraft.sinocore.block.ModWoodBlock;
import games.moegirl.sinocraft.sinocore.tree.event.TreeDataHandler;
import games.moegirl.sinocraft.sinocore.utility.FloatModifier;
import games.moegirl.sinocraft.sinocore.utility.RegType;
import net.minecraft.data.worldgen.features.FeatureUtils;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.grower.AbstractTreeGrower;
import net.minecraft.world.level.block.grower.OakTreeGrower;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.level.material.MaterialColor;
import net.minecraftforge.registries.DeferredRegister;

import java.util.*;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * 如何构造一棵树
 */
public class TreeBuilder {

    final ResourceLocation name;

    List<CreativeModeTab> saplingTabs = List.of(CreativeModeTabs.NATURAL_BLOCKS);
    List<CreativeModeTab> leavesTabs = List.of(CreativeModeTabs.NATURAL_BLOCKS);
    List<CreativeModeTab> logTabs = List.of(CreativeModeTabs.BUILDING_BLOCKS, CreativeModeTabs.NATURAL_BLOCKS);
    List<CreativeModeTab> strippedLogTabs = List.of(CreativeModeTabs.BUILDING_BLOCKS);
    List<CreativeModeTab> woodTabs = List.of(CreativeModeTabs.BUILDING_BLOCKS);
    List<CreativeModeTab> strippedWoodTabs = List.of(CreativeModeTabs.BUILDING_BLOCKS);

    SoundType saplingSound = SoundType.GRASS;
    SoundType leavesSound = SoundType.GRASS;

    MaterialColor topLogColor = MaterialColor.WOOD;
    MaterialColor barkLogColor = MaterialColor.WOOD;
    MaterialColor topStrippedLogColor = MaterialColor.WOOD;
    MaterialColor barkStrippedLogColor = MaterialColor.WOOD;
    MaterialColor woodColor = MaterialColor.WOOD;
    MaterialColor strippedWoodColor = MaterialColor.WOOD;

    Function<Tree, SaplingBlock> sapling = ModSaplingBlock::new;
    Function<Tree, RotatedPillarBlock> log = tree -> new ModLogBlock(tree, false);
    Function<Tree, RotatedPillarBlock> strippedLog = tree -> new ModLogBlock(tree, true);
    Function<Tree, RotatedPillarBlock> wood = tree -> new ModWoodBlock(tree, false);
    Function<Tree, RotatedPillarBlock> strippedWood = tree -> new ModWoodBlock(tree, true);
    Function<Tree, LeavesBlock> leaves = ModLeavesBlock::new;
    Function<Tree, FlowerPotBlock> pottedSapling = tree -> new FlowerPotBlock(
            () -> (FlowerPotBlock) Blocks.FLOWER_POT, tree.sapling,
            BlockBehaviour.Properties.of(Material.DECORATION).instabreak().noOcclusion());

    Function<Tree, BlockItem> saplingItem = tree -> new BlockItem(tree.sapling(), new Item.Properties());
    Function<Tree, BlockItem> logItem = tree -> new BlockItem(tree.log(), new Item.Properties());
    Function<Tree, BlockItem> strippedLogItem = tree -> new BlockItem(tree.strippedLog(), new Item.Properties());
    Function<Tree, BlockItem> woodItem = tree -> new BlockItem(tree.wood(), new Item.Properties());
    Function<Tree, BlockItem> strippedWoodItem = tree -> new BlockItem(tree.strippedWood(), new Item.Properties());
    Function<Tree, BlockItem> leavesItem = tree -> new BlockItem(tree.leaves(), new Item.Properties());

    AbstractTreeGrower grower = new OakTreeGrower();
    FloatModifier strengthModifier = new FloatModifier();
    EnumSet<RegType> regTypes = EnumSet.allOf(RegType.class);
    Map<Locale, String> languages = new HashMap<>();

    public TreeBuilder(ResourceLocation name) {
        this.name = name;
    }

    public TreeBuilder saplingTabs(CreativeModeTab... tabs) {
        saplingTabs = List.of(tabs);
        return this;
    }

    public TreeBuilder leavesTabs(CreativeModeTab... tabs) {
        leavesTabs = List.of(tabs);
        return this;
    }

    public TreeBuilder logTabs(CreativeModeTab... tabs) {
        logTabs = List.of(tabs);
        return this;
    }

    public TreeBuilder strippedLogTabs(CreativeModeTab... tabs) {
        strippedLogTabs = List.of(tabs);
        return this;
    }

    public TreeBuilder woodTabs(CreativeModeTab... tabs) {
        woodTabs = List.of(tabs);
        return this;
    }

    public TreeBuilder strippedWoodTabs(CreativeModeTab... tabs) {
        strippedWoodTabs = List.of(tabs);
        return this;
    }

    public TreeBuilder tab(CreativeModeTab... tabs) {
        return tab(List.of(tabs));
    }

    public TreeBuilder tab(List<CreativeModeTab> tabs) {
        saplingTabs = tabs;
        leavesTabs = tabs;
        logTabs = tabs;
        strippedLogTabs = tabs;
        woodTabs = tabs;
        strippedWoodTabs = tabs;
        return this;
    }

    public TreeBuilder saplingSound(SoundType sound) {
        this.saplingSound = sound;
        return this;
    }

    public TreeBuilder leavesSound(SoundType sound) {
        this.leavesSound = sound;
        return this;
    }

    public TreeBuilder logColor(MaterialColor top, MaterialColor bark) {
        this.topLogColor = top;
        this.barkLogColor = bark;
        return this;
    }

    public TreeBuilder strippedLogColor(MaterialColor top, MaterialColor bark) {
        this.topStrippedLogColor = top;
        this.barkStrippedLogColor = bark;
        return this;
    }

    public TreeBuilder woodColor(MaterialColor color) {
        this.woodColor = color;
        return this;
    }

    public TreeBuilder strippedWoodColor(MaterialColor color) {
        this.strippedWoodColor = color;
        return this;
    }

    public TreeBuilder sapling(Function<Tree, SaplingBlock> factory) {
        this.sapling = factory;
        return this;
    }

    public TreeBuilder log(Function<Tree, RotatedPillarBlock> factory) {
        this.log = factory;
        return this;
    }

    public TreeBuilder strippedLog(Function<Tree, RotatedPillarBlock> factory) {
        this.strippedLog = factory;
        return this;
    }

    public TreeBuilder wood(Function<Tree, RotatedPillarBlock> factory) {
        this.wood = factory;
        return this;
    }

    public TreeBuilder strippedWood(Function<Tree, RotatedPillarBlock> factory) {
        this.strippedWood = factory;
        return this;
    }

    public TreeBuilder leaves(Function<Tree, LeavesBlock> factory) {
        this.leaves = factory;
        return this;
    }

    public TreeBuilder pottedSapling(Function<Tree, FlowerPotBlock> factory) {
        this.pottedSapling = factory;
        return this;
    }

    public TreeBuilder saplingItem(Function<Tree, BlockItem> factory) {
        this.saplingItem = factory;
        return this;
    }

    public TreeBuilder logItem(Function<Tree, BlockItem> factory) {
        this.logItem = factory;
        return this;
    }

    public TreeBuilder strippedLogItem(Function<Tree, BlockItem> factory) {
        this.strippedLogItem = factory;
        return this;
    }

    public TreeBuilder woodItem(Function<Tree, BlockItem> factory) {
        this.woodItem = factory;
        return this;
    }

    public TreeBuilder strippedWoodItem(Function<Tree, BlockItem> factory) {
        this.strippedWoodItem = factory;
        return this;
    }

    public TreeBuilder leavesItem(Function<Tree, BlockItem> factory) {
        this.leavesItem = factory;
        return this;
    }

    public TreeBuilder grower(AbstractTreeGrower grower) {
        this.grower = grower;
        return this;
    }

    public TreeBuilder grower(ResourceLocation name) {
        this.grower = new TreeSaplingGrower() {
            @Override
            protected ResourceKey<ConfiguredFeature<?, ?>> getConfiguredFeature(RandomSource random, boolean hasFlowers) {
                return FeatureUtils.createKey(name.toString());
            }
        };
        return this;
    }

    public TreeBuilder grower(Supplier<ConfiguredFeature<?, ?>> builder) {
        TreeDataHandler.obtain(name.getNamespace()).features.put(name, builder);
        return grower(name);
    }

    public TreeBuilder lang(Locale locale, String name) {
        languages.put(locale, name);
        return this;
    }

    /**
     * 对所有木方块进行的硬度调整
     */
    public TreeBuilder blockStrengthModifier(FloatModifier modifier) {
        strengthModifier = modifier;
        return this;
    }

    public TreeBuilder disableRegister(RegType type) {
        regTypes.remove(type);
        switch (type) {
            case ALL_MODELS -> regTypes.removeIf(t -> t.model);
            case ALL_TAGS -> regTypes.removeIf(t -> t.tag);
            case ALL_PROVIDERS -> regTypes.removeIf(t -> t.provider);
            case ALL_EVENTS -> regTypes.removeIf(t -> t.event);
            case ALL_DATA -> regTypes.removeIf(t -> t.data);
            case ALL_RES -> regTypes.removeIf(t -> t.res);
            case ALL -> regTypes.clear();
        }
        return this;
    }

    public Tree build(DeferredRegister<Block> blocks, DeferredRegister<Item> items) {
        return new Tree(this, blocks, items);
    }
}
