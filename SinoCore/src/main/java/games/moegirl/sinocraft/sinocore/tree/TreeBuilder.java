package games.moegirl.sinocraft.sinocore.tree;

import games.moegirl.sinocraft.sinocore.world.gen.ModConfiguredFeatures;
import games.moegirl.sinocraft.sinocore.world.gen.tree.ModTreeGrowerBase;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.levelgen.feature.configurations.TreeConfiguration;
import net.minecraftforge.registries.RegistryObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.EnumMap;
import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Function;

/**
 * @author qyl
 */
public class TreeBuilder {
    final ResourceLocation name;
    final TreeLanguages languages;
    private final EnumMap<TreeBlockType, TreeBlockFactory> treeBlocks;
    Function<Tree, ModTreeGrowerBase> grower = tree -> new ModTreeGrowerBase(tree.name);
    Function<Tree, TreeConfiguration> configuration = tree ->
            ModConfiguredFeatures.defaultTree(tree.getBlock(TreeBlockType.LOG), tree.getBlock(TreeBlockType.LEAVES));

    /**
     * New builder.
     *
     * @param name ResourceLocation of tree.
     */
    public TreeBuilder(ResourceLocation name) {
        this.name = name;
        this.languages = new TreeLanguages();
        this.treeBlocks = new EnumMap<>(TreeBlockType.class);
        for (TreeBlockType value : TreeBlockType.values())
            treeBlocks.put(value, new TreeBlockFactory(value));
    }

    /**
     * Translate root for a locale.
     *
     * @param name Translate root value, like Oak, Spruce.
     * @return TreeLanguages
     */
    public TreeBuilder translate(String locale, String name) {
        languages.rootNames.put(locale, name);
        return this;
    }

    /**
     * Translate decorators for a locale.
     *
     * @param translate Translate value.
     * @return TreeLanguages
     */
    public TreeBuilder translate(TreeBlockType type, String locale, Function<StringBuilder, StringBuilder> translate) {
        languages.put(locale, type, translate);
        return this;
    }

    /**
     * Translate decorators for a locale.
     *
     * @param name Literal translate value.
     * @return TreeLanguages
     */
    public TreeBuilder translate(TreeBlockType type, String locale, String name) {
        return translateOverride(type, locale, b -> new StringBuilder(name));
    }

    /**
     * Translate decorators for a locale, clear all existed translate function before insert.
     *
     * @param translate Translate value.
     * @return TreeLanguages
     */
    public TreeBuilder translateOverride(TreeBlockType type, String locale, Function<StringBuilder, StringBuilder> translate) {
        List<Function<StringBuilder, StringBuilder>> list = new ArrayList<>();
        list.add(translate);
        languages.names.put(locale, type, list);
        return this;
    }

    /**
     * Custom block instead of auto generate.
     *
     * @param treeBlockType Specific block.
     * @param block         Block.
     * @return Builder
     */
    public TreeBuilder block(TreeBlockType treeBlockType, BiFunction<Tree, BlockBehaviour.Properties, Block> block) {
        TreeBlockFactory factory = treeBlocks.get(treeBlockType);
        factory.blockBuilder = factory.wrapBlock(block);
        return this;
    }

    /**
     * Custom block Property instead of auto generate.
     *
     * @param treeBlockType Specific block Property.
     * @param property      Block property.
     * @return Builder
     */
    public TreeBuilder blockProperty(TreeBlockType treeBlockType, Function<Tree, BlockBehaviour.Properties> property) {
        treeBlocks.get(treeBlockType).blockPropBuilder = property;
        return this;
    }

    /**
     * Custom block instead of auto register.
     *
     * @param treeBlockType Specific block.
     * @param block         Block.
     * @return Builder
     */
    public TreeBuilder block(TreeBlockType treeBlockType, RegistryObject<? extends Block> block) {
        treeBlocks.get(treeBlockType).blockBuilder = (v1, v2, v3) -> block;
        return this;
    }

    /**
     * Custom block item instead of auto generate.
     *
     * @param treeBlockType Specific block item.
     * @param item          Block item.
     * @return Builder
     */
    public TreeBuilder item(TreeBlockType treeBlockType, BiFunction<Tree, TreeBlockType, Item> item) {
        TreeBlockFactory factory = treeBlocks.get(treeBlockType);
        factory.itemBuilder = factory.wrapItem(item);
        return this;
    }

    /**
     * Custom block item instead of auto register.
     *
     * @param treeBlockType Specific block item.
     * @param item          Block item.
     * @return Builder
     */
    public TreeBuilder item(TreeBlockType treeBlockType, RegistryObject<? extends Item> item) {
        TreeBlockFactory factory = treeBlocks.get(treeBlockType);
        factory.itemBuilder = (v1, v2, v3) -> item;
        return this;
    }

    /**
     * Custom creative mode tab instead of auto generate.
     *
     * @param treeBlockType Specific block item.
     * @param tab           Item tab.
     * @return Builder
     */
    public TreeBuilder tab(TreeBlockType treeBlockType, ResourceLocation tab) {
        TreeBlockFactory factory = treeBlocks.get(treeBlockType);
        factory.tabs.add(tab);
        return this;
    }

    /**
     * Add all items to tabs.
     *
     * @param tab Tabs to add all items to.
     * @return Builder
     */
    public TreeBuilder tab(ResourceLocation tab) {
        treeBlocks.values().forEach(factory -> factory.tabs.add(tab));
        return this;
    }

    public TreeBuilder fillDefaultTabs(TreeBlockType... types) {
        for (TreeBlockType type : types) {
            treeBlocks.get(type).fillDefaultTabs = true;
        }
        return this;
    }

    /**
     * Set grower for sapling.
     *
     * @param grower Grower.
     * @return Builder
     */
    public TreeBuilder grower(ModTreeGrowerBase grower, TreeConfiguration configuration) {
        this.grower = t -> grower;
        this.configuration = t -> configuration;
        return this;
    }

    /**
     * Set grower for sapling.
     *
     * @param grower Grower.
     * @return Builder
     */
    public TreeBuilder grower(ModTreeGrowerBase grower) {
        this.grower = t -> grower;
        return this;
    }

    /**
     * Add all block to tags.
     *
     * @param tags Block tags.
     * @return Builder
     */
    @SafeVarargs
    public final TreeBuilder blockTags(TagKey<Block>... tags) {
        treeBlocks.values().forEach(factory -> Collections.addAll(factory.blockTags, tags));
        return this;
    }

    /**
     * Add all block to tags.
     *
     * @param tags Block tags.
     * @return Builder
     */
    @SafeVarargs
    public final TreeBuilder blockTags(TreeBlockType type, TagKey<Block>... tags) {
        Collections.addAll(treeBlocks.get(type).blockTags, tags);
        return this;
    }

    /**
     * Remove block from adding tags.
     * @param types block type.
     * @return Builder
     */
    public TreeBuilder noDefaultBlockTags(TreeBlockType... types) {
        for (TreeBlockType type : types) {
            treeBlocks.get(type).addDefaultBlockTag = false;
        }
        return this;
    }

    /**
     * Add all item to tags.
     *
     * @param tags Item tags.
     * @return Builder
     */
    @SafeVarargs
    public final TreeBuilder itemTags(TagKey<Item>... tags) {
        treeBlocks.values().forEach(factory -> Collections.addAll(factory.itemTags, tags));
        return this;
    }

    /**
     * Add all item to tags.
     *
     * @param tags Item tags.
     * @return Builder
     */
    @SafeVarargs
    public final TreeBuilder itemTags(TreeBlockType type, TagKey<Item>... tags) {
        Collections.addAll(treeBlocks.get(type).itemTags, tags);
        return this;
    }

    /**
     * Remove item from adding tags.
     * @param types item type.
     * @return Builder
     */
    public TreeBuilder noDefaultItemTags(TreeBlockType... types) {
        for (TreeBlockType type : types) {
            treeBlocks.get(type).addDefaultItemTag = false;
        }
        return this;
    }

    public EnumMap<TreeBlockType, TreeBlockFactory> getBlockFactories() {
        return treeBlocks;
    }

    public Tree build() {
        return new Tree(this);
    }
}