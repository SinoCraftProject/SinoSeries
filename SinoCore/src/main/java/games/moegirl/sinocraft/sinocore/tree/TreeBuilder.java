package games.moegirl.sinocraft.sinocore.tree;

import games.moegirl.sinocraft.sinocore.old.block.BlockTreeLeaves;
import games.moegirl.sinocraft.sinocore.old.block.BlockTreeLog;
import games.moegirl.sinocraft.sinocore.old.block.BlockTreeSapling;
import games.moegirl.sinocraft.sinocore.old.block.BlockTreeWood;
import games.moegirl.sinocraft.sinocore.old.utility.FloatModifier;
import net.minecraft.core.Holder;
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
import org.jetbrains.annotations.Nullable;

import java.util.function.Function;
import java.util.function.Supplier;

@SuppressWarnings("ConstantConditions")
public class TreeBuilder {

    final ResourceLocation name;
    CreativeModeTab tab = CreativeModeTabs.BUILDING_BLOCKS;
    SoundType sound = SoundType.GRASS;
    MaterialColor topLogColor = MaterialColor.WOOD;
    MaterialColor barkLogColor = MaterialColor.WOOD;
    MaterialColor topStrippedLogColor = MaterialColor.WOOD;
    MaterialColor barkStrippedLogColor = MaterialColor.WOOD;
    MaterialColor woodColor = MaterialColor.WOOD;
    MaterialColor strippedWoodColor = MaterialColor.WOOD;
    AbstractTreeGrower grower = new OakTreeGrower();
    FloatModifier strengthModifier = new FloatModifier();

    Function<Tree, SaplingBlock> sapling = BlockTreeSapling::new;
    Function<Tree, BlockItem> saplingItem = tree -> new BlockItem(tree.sapling(), new Item.Properties()/*todo fix: .tab(tree.properties().tab())*/);

    Function<Tree, RotatedPillarBlock> log = tree -> new BlockTreeLog(tree, false);
    Function<Tree, BlockItem> logItem = tree -> new BlockItem(tree.log(), new Item.Properties()/*todo fix: .tab(tree.properties().tab())*/);

    Function<Tree, RotatedPillarBlock> strippedLog = tree -> new BlockTreeLog(tree, true);
    Function<Tree, BlockItem> strippedLogItem = tree -> new BlockItem(tree.strippedLog(), new Item.Properties()/*todo fix: .tab(tree.properties().tab())*/);

    Function<Tree, RotatedPillarBlock> wood = tree -> new BlockTreeWood(tree, false);
    Function<Tree, BlockItem> woodItem = tree -> new BlockItem(tree.wood(), new Item.Properties()/*todo fix: .tab(tree.properties().tab())*/);

    Function<Tree, RotatedPillarBlock> strippedWoods = tree -> new BlockTreeWood(tree, true);
    Function<Tree, BlockItem> strippedWoodsItem = tree -> new BlockItem(tree.strippedWoods(), new Item.Properties()/*todo fix: .tab(tree.properties().tab())*/);

    Function<Tree, LeavesBlock> leaves = BlockTreeLeaves::new;
    Function<Tree, BlockItem> leavesItem = tree -> new BlockItem(tree.leaves(), new Item.Properties()/*todo fix: .tab(tree.properties().tab())*/);

    Function<Tree, FlowerPotBlock> pottedSapling = tree -> new FlowerPotBlock(
            () -> (FlowerPotBlock) Blocks.FLOWER_POT, tree.sapling,
            BlockBehaviour.Properties.of(Material.DECORATION).instabreak().noOcclusion());

    public TreeBuilder(ResourceLocation name) {
        this.name = name;
    }

    public TreeBuilder tab(CreativeModeTab tab) {
        this.tab = tab;
        return this;
    }

    /**
     * Leaves: set leaves sound, all vanilla leaves is {@link SoundType#GRASS},
     * except azalea is {@link  SoundType#AZALEA_LEAVES}
     *
     * @param sound leave sound, default is {@link SoundType#GRASS}
     * @return this builder
     */
    public TreeBuilder leavesSound(SoundType sound) {
        this.sound = sound;
        return this;
    }

    /**
     * Log: set log color in map
     *
     * @param top  top color, default is {@link MaterialColor#WOOD}
     * @param bark bark color, default is {@link MaterialColor#WOOD}
     * @return this builder
     */
    public TreeBuilder logColor(MaterialColor top, MaterialColor bark) {
        this.topLogColor = top;
        this.barkLogColor = bark;
        return this;
    }

    /**
     * Log: set stripped log color in map
     *
     * @param top  top color, default is {@link MaterialColor#WOOD}
     * @param bark bark color, default is {@link MaterialColor#WOOD}
     * @return this builder
     */
    public TreeBuilder strippedLogColor(MaterialColor top, MaterialColor bark) {
        this.topStrippedLogColor = top;
        this.barkStrippedLogColor = bark;
        return this;
    }

    /**
     * Wood: set wood color in map
     *
     * @param color wood color, default is {@link MaterialColor#WOOD}
     * @return this builder
     */
    public TreeBuilder woodColor(MaterialColor color) {
        this.woodColor = color;
        return this;
    }

    /**
     * Plank: set stripped wood color in map
     *
     * @param color wood color, default is {@link MaterialColor#WOOD}
     * @return this builder
     */
    public TreeBuilder strippedWoodColor(MaterialColor color) {
        this.strippedWoodColor = color;
        return this;
    }

    /**
     * Plank: set wood color in map
     *
     * @param normal   wood color, default is {@link MaterialColor#WOOD}
     * @param stripped stripped plank color, default is {@link MaterialColor#WOOD}
     * @return this builder
     */
    public TreeBuilder woodColor(MaterialColor normal, MaterialColor stripped) {
        woodColor(normal);
        strippedWoodColor(stripped);
        return this;
    }

    /**
     * Set all log color in map
     *
     * @param topColor      top log color
     * @param woodColor     wood color
     * @param barkColor     bark log color
     * @param strippedColor stripped log color
     * @return this builder
     */
    public TreeBuilder color(MaterialColor topColor, MaterialColor woodColor, MaterialColor barkColor, MaterialColor strippedColor) {
        logColor(topColor, barkColor);
        strippedLogColor(topColor, strippedColor);
        woodColor(woodColor, strippedColor);
        return this;
    }

    /**
     * Set all log color in map
     *
     * @param woodColor     top log color, plank color and wood color
     * @param barkColor     bark log color
     * @param strippedColor stripped log color
     * @return this builder
     */
    public TreeBuilder color(MaterialColor woodColor, MaterialColor barkColor, MaterialColor strippedColor) {
        return color(woodColor, woodColor, barkColor, strippedColor);
    }

    /**
     * Sapling: set tree grower, use to grow the tree
     *
     * @param grower tree grower
     * @return this builder
     */
    public TreeBuilder grower(AbstractTreeGrower grower) {
        this.grower = grower;
        return this;
    }

    /**
     * Sapling: set tree grower, use to grow the tree
     *
     * @param feature feature selector
     * @return this builder
     */
    public TreeBuilder grower(Function<RandomSource, ConfiguredFeature<?, ?>> feature) {
        this.grower = new AbstractTreeGrower() {

            private Holder<ConfiguredFeature<?, ?>> configuredFeature = null;
            private boolean initialized = false;

            @Nullable
            @Override
            protected ResourceKey<ConfiguredFeature<?, ?>> getConfiguredFeature(RandomSource random, boolean hasFlowers) {
                if (!initialized) {
                    configuredFeature = Holder.direct(feature.apply(random));
                    initialized = true;
                }
                // todo fix: configuredFeature
                /*return configuredFeature;*/
                return null;
            }
        };
        return this;
    }

    /**
     * Sapling: set tree grower, use to grow the tree
     *
     * @param feature feature getter
     * @return this builder
     */
    public TreeBuilder grower(Supplier<ConfiguredFeature<?, ?>> feature) {
        return grower(r -> feature.get());
    }

    /**
     * Sapling: set tree grower, use to grow the tree
     *
     * @param feature feature
     * @return this builder
     */
    public TreeBuilder grower(ConfiguredFeature<?, ?> feature) {
        return grower(r -> feature);
    }

    public TreeBuilder customSapling(Function<Tree, SaplingBlock> factory) {
        this.sapling = factory;
        return this;
    }

    public TreeBuilder customSaplingItem(Function<Tree, BlockItem> factory) {
        this.saplingItem = factory;
        return this;
    }

    public TreeBuilder customLog(Function<Tree, RotatedPillarBlock> factory) {
        this.log = factory;
        return this;
    }

    public TreeBuilder customLogItem(Function<Tree, BlockItem> factory) {
        this.logItem = factory;
        return this;
    }

    public TreeBuilder customStrippedLog(Function<Tree, RotatedPillarBlock> factory) {
        this.strippedLog = factory;
        return this;
    }

    public TreeBuilder customStrippedLogItem(Function<Tree, BlockItem> factory) {
        this.strippedLogItem = factory;
        return this;
    }

    public TreeBuilder customWood(Function<Tree, RotatedPillarBlock> factory) {
        this.wood = factory;
        return this;
    }

    public TreeBuilder customWoodItem(Function<Tree, BlockItem> factory) {
        this.woodItem = factory;
        return this;
    }

    public TreeBuilder customStrippedWood(Function<Tree, RotatedPillarBlock> factory) {
        this.strippedWoods = factory;
        return this;
    }

    public TreeBuilder customStrippedWoodItem(Function<Tree, BlockItem> factory) {
        this.strippedWoodsItem = factory;
        return this;
    }

    public TreeBuilder customLeaves(Function<Tree, LeavesBlock> factory) {
        this.leaves = factory;
        return this;
    }

    public TreeBuilder customLeavesItem(Function<Tree, BlockItem> factory) {
        this.leavesItem = factory;
        return this;
    }

    public TreeBuilder customPottedSapling(Function<Tree, FlowerPotBlock> factory) {
        this.pottedSapling = factory;
        return this;
    }

    public TreeBuilder blockStrengthModifier(FloatModifier modifier) {
        strengthModifier = modifier;
        return this;
    }

    public Tree build(DeferredRegister<Block> blocks, DeferredRegister<Item> items) {
        return new Tree(this, blocks, items);
    }
}
