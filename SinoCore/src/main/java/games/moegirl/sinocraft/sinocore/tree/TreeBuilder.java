package games.moegirl.sinocraft.sinocore.tree;

import games.moegirl.sinocraft.sinocore.block.BlockTreeLeaves;
import games.moegirl.sinocraft.sinocore.block.BlockTreeLog;
import games.moegirl.sinocraft.sinocore.block.BlockTreeSapling;
import games.moegirl.sinocraft.sinocore.block.BlockTreeWood;
import games.moegirl.sinocraft.sinocore.utility.FloatModifier;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.grower.AbstractTreeGrower;
import net.minecraft.world.level.block.grower.OakTreeGrower;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.level.material.MaterialColor;
import net.minecraftforge.registries.DeferredRegister;

import java.util.List;
import java.util.function.Function;

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

    Function<Tree, SaplingBlock> sapling = BlockTreeSapling::new;
    Function<Tree, RotatedPillarBlock> log = tree -> new BlockTreeLog(tree, false);
    Function<Tree, RotatedPillarBlock> strippedLog = tree -> new BlockTreeLog(tree, true);
    Function<Tree, RotatedPillarBlock> wood = tree -> new BlockTreeWood(tree, false);
    Function<Tree, RotatedPillarBlock> strippedWood = tree -> new BlockTreeWood(tree, true);
    Function<Tree, LeavesBlock> leaves = BlockTreeLeaves::new;
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

    /**
     * 对所有木方块进行的硬度调整
     */
    public TreeBuilder blockStrengthModifier(FloatModifier modifier) {
        strengthModifier = modifier;
        return this;
    }

    public Tree build(DeferredRegister<Block> blocks, DeferredRegister<Item> items) {
        return new Tree(this, blocks, items);
    }
}
