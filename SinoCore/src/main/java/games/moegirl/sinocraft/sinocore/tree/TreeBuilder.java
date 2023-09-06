package games.moegirl.sinocraft.sinocore.tree;

import com.mojang.datafixers.util.Function3;
import games.moegirl.sinocraft.sinocore.tab.TabItemGenerator;
import games.moegirl.sinocraft.sinocore.utility.Functions;
import games.moegirl.sinocraft.sinocore.world.gen.ModTreeGrowerBase;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.util.valueproviders.ConstantInt;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.configurations.TreeConfiguration;
import net.minecraft.world.level.levelgen.feature.featuresize.TwoLayersFeatureSize;
import net.minecraft.world.level.levelgen.feature.foliageplacers.BlobFoliagePlacer;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.minecraft.world.level.levelgen.feature.trunkplacers.StraightTrunkPlacer;
import net.minecraftforge.registries.RegistryObject;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.EnumMap;
import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;

/**
 * @author qyl
 */
public class TreeBuilder {
    final ResourceLocation name;
    final TreeLanguages languages;
    private final EnumMap<TreeBlockType, TreeBlockFactory> treeBlocks;
    Function<Tree, ModTreeGrowerBase> grower = tree -> new ModTreeGrowerBase(tree.name) {
        @Override
        public TreeConfiguration getConfiguration(Tree tree) {
            return new TreeConfiguration.TreeConfigurationBuilder(BlockStateProvider.simple(tree.getBlock(TreeBlockType.LOG)),
                    new StraightTrunkPlacer(4, 2, 0),
                    BlockStateProvider.simple(tree.getBlock(TreeBlockType.LEAVES)),
                    new BlobFoliagePlacer(ConstantInt.of(2), ConstantInt.of(0), 3),
                    new TwoLayersFeatureSize(1, 0, 1))
                    .ignoreVines()
                    .build();
        }
    };

    /**
     * New builder.
     *
     * @param name ResourceLocation of tree.
     */
    public TreeBuilder(ResourceLocation name) {
        this.name = name;
        this.languages = new TreeLanguages();
        this.treeBlocks = new EnumMap<>(TreeBlockType.class);
        for (TreeBlockType value : TreeBlockType.values()) {
            treeBlocks.put(value, new TreeBlockFactory(value));
        }

    }

    /**
     * Translate root for a locale.
     *
     * @param name Translate root value, like Oak, Spruce.
     * @return TreeBuilder
     */
    public TreeBuilder translate(String locale, String name) {
        languages.rootNames.put(locale, name);
        return this;
    }

    /**
     * Translate decorators for a locale.
     *
     * @param translate Translate value.
     * @return TreeBuilder
     */
    public TreeBuilder translate(TreeBlockType type, String locale, Function<StringBuilder, StringBuilder> translate) {
        languages.put(locale, type, translate);
        return this;
    }

    /**
     * Translate decorators for a locale.
     *
     * @param name Literal translate value.
     * @return TreeBuilder
     */
    public TreeBuilder translate(TreeBlockType type, String locale, String name) {
        return translateOverride(type, locale, b -> new StringBuilder(name));
    }

    /**
     * Translate decorators for a locale, clear all existed translate function before insert.
     *
     * @param translate Translate value.
     * @return TreeBuilder
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
     * Custom block instead of auto register.
     *
     * @param treeBlockType Specific block.
     * @param block         Block.
     * @return Builder
     */
    public TreeBuilder block(TreeBlockType treeBlockType, RegistryObject<? extends Block> block) {
        treeBlocks.get(treeBlockType).blockBuilder = (v1, v2, v3, v4) -> block;
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
     * Change all block Property.
     *
     * @return Builder
     */
    public TreeBuilder blockProperty(Consumer<BlockBehaviour.Properties> property) {
        for (TreeBlockFactory factory : treeBlocks.values()) {
            factory.blockPropBuilder = Functions.compose(factory.blockPropBuilder, property);
        }
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
     * Custom block entity instead of auto generate.
     *
     * @param treeBlockType Specific block.
     * @param blockEntity   BlockEntity.
     * @return Builder
     */
    public TreeBuilder blockEntity(TreeBlockType treeBlockType, @Nullable Function<Tree, BlockEntityType<?>> blockEntity) {
        TreeBlockFactory factory = treeBlocks.get(treeBlockType);
        factory.blockEntityBuilder = factory.wrapBlockEntity(blockEntity);
        return this;
    }

    /**
     * Custom block entity instead of auto generate.
     *
     * @param treeBlockType Specific block.
     * @param blockEntity   BlockEntity.
     * @return Builder
     */
    public TreeBuilder blockEntity(TreeBlockType treeBlockType, Function3<Tree, BlockPos, BlockState, BlockEntity> blockEntity) {
        TreeBlockFactory factory = treeBlocks.get(treeBlockType);
        factory.blockEntityBuilder = factory.wrapBlockEntity(tree -> BlockEntityType.Builder
                .of((pos, state) -> blockEntity.apply(tree, pos, state), tree.getBlock(treeBlockType))
                .build(null));
        return this;
    }

    /**
     * Custom block entity instead of auto register.
     *
     * @param treeBlockType Specific block.
     * @param blockEntity   BlockEntity.
     * @return Builder
     */
    public TreeBuilder blockEntity(TreeBlockType treeBlockType, RegistryObject<BlockEntityType<?>> blockEntity) {
        treeBlocks.get(treeBlockType).blockEntityBuilder = (v1, v2, v3) -> blockEntity;
        return this;
    }

    /**
     * Custom creative mode tab instead of auto generate.
     *
     * @param treeBlockType Specific block item.
     * @param tab           Item tab.
     * @return Builder
     */
    public TreeBuilder tab(TreeBlockType treeBlockType, TabItemGenerator tab) {
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
    public TreeBuilder tab(TabItemGenerator tab) {
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
     * @return Builder
     */
    public TreeBuilder grower(Function<Tree, TreeConfiguration> configuration) {
        this.grower = t -> new ModTreeGrowerBase(t.name) {

            @Override
            public TreeConfiguration getConfiguration(Tree tree) {
                return configuration.apply(t);
            }
        };
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
     *
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
     *
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
