package games.moegirl.sinocraft.sinocore.tree;

import com.google.common.collect.ImmutableMap;
import games.moegirl.sinocraft.sinocore.utility.decorator.StringDecorator;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.grower.AbstractTreeGrower;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.properties.WoodType;

import javax.annotation.Nullable;
import java.util.*;

import static games.moegirl.sinocraft.sinocore.tree.TreeBlockUtilities.*;

public class TreeType {

    public final ResourceLocation name;

    protected WoodType woodType;

    protected Map<TreeBlockType, Block> blocks = new HashMap<>();
    protected Map<TreeBlockType, Item> items = new HashMap<>();
    protected Map<TreeBlockType, List<CreativeModeTab>> itemCreativeTabs = new HashMap<>();
    protected Map<CreativeModeTab, List<Item>> creativeTabItems = new HashMap<>();

    protected TreeBlockNameTranslator translator;

    protected AbstractTreeGrower grower;

    protected TreeType(ResourceLocation name,
                       Map<String, String> translateRoots,
                       Map<String, Map<TreeBlockType, StringDecorator>> customTranslates,
                       Map<String, Map<TreeBlockType, String>> customLiteralTranslates,
                       Map<TreeBlockType, Block> customBlocks,
                       Map<TreeBlockType, BlockBehaviour.Properties> customBlockProperties,
                       Map<TreeBlockType, Item> customBlockItems,
                       Map<TreeBlockType, List<CreativeModeTab>> customItemTabs,
                       @Nullable AbstractTreeGrower grower) {
        this.name = name;

        this.translator = new TreeBlockNameTranslator(name, translateRoots, customTranslates, customLiteralTranslates);

        this.grower = Objects.requireNonNullElseGet(grower, () -> new DefaultTreeGrower(this));

        woodType = WoodType.create(name.toString());
        WoodType.register(woodType);

        fillBlocks(customBlocks, customBlockProperties);
        fillItems(customBlockItems);
        fillCreativeTabs(customItemTabs);
    }

    private void fillBlocks(Map<TreeBlockType, Block> customBlocks,
            Map<TreeBlockType, BlockBehaviour.Properties> customBlockProperties) {
        blocks.putAll(customBlocks);

        for (var entry : customBlockProperties.entrySet()) {
            var type = entry.getKey();
            if (type.isGeneralBlock()) {
                blocks.put(type, makeGeneralBlock(type, entry.getValue()));
            }
        }

        var blockTypesRemain = new ArrayList<>(List.of(TreeBlockType.values()))
                .stream()
                .filter(t -> !t.hasNoBlock())
                .filter(t -> !customBlocks.containsKey(t))
                .filter(t -> !customBlockProperties.containsKey(t))
                .toList();

        // Add block stage 1.
        for (var it = blockTypesRemain.iterator(); it.hasNext(); ) {
            var type = it.next();

            var block = switch (type) {
                case LOG, STRIPPED_LOG -> makeGeneralBlock(type, logProp());
                case LOG_WOOD, STRIPPED_LOG_WOOD -> makeGeneralBlock(type, woodProp());
                case PLANKS -> makeGeneralBlock(type, planksProp());
                case LEAVES -> makeGeneralBlock(type, leavesProp());
                case SLAB -> makeGeneralBlock(type, slabProp());
                case BUTTON -> makeGeneralBlock(type, buttonProp());
                case DOOR -> makeGeneralBlock(type, doorProp());
                case TRAPDOOR -> makeGeneralBlock(type, trapdoorProp());
                case FENCE, FENCE_GATE -> makeGeneralBlock(type, fenceProp());
                case PRESSURE_PLATE -> makeGeneralBlock(type, pressurePlateProp());
                default -> null;
            };

            if (block != null) {
                blocks.put(type, block);
                it.remove();
            }
        }

        // Add block stage 2.
        for (var it = blockTypesRemain.iterator(); it.hasNext(); ) {
            var type = it.next();
            Block block = null;

            if (type == TreeBlockType.SAPLING) {
                block = sapling(grower, saplingProp());
            } else if (type == TreeBlockType.STAIRS) {
                block = stairs(blocks.get(TreeBlockType.PLANKS));
            } else if (type == TreeBlockType.SIGN) {
                block = makeSignBlock(type, signProp(), getWoodType());
            } else if (type == TreeBlockType.HANGING_SIGN) {
                block = makeSignBlock(type, hangingSignProp(), getWoodType());
            }

            if (block != null) {
                blocks.put(type, block);
                it.remove();
            }
        }

        // Add block stage 3.
        for (var it = blockTypesRemain.iterator(); it.hasNext(); ) {
            var type = it.next();

            Block block = null;
            if (type == TreeBlockType.POTTED_SAPLING) {
                block = pottedSapling((SaplingBlock) blocks.get(TreeBlockType.SAPLING), pottedSaplingProp());
            } else if (type == TreeBlockType.WALL_SIGN) {
                block = makeSignBlock(type, wallSignProp(blocks.get(TreeBlockType.WALL_SIGN)), getWoodType());
            } else if (type == TreeBlockType.WALL_HANGING_SIGN) {
                block = makeSignBlock(type, wallHangingSignProp(blocks.get(TreeBlockType.WALL_SIGN)), getWoodType());
            }

            if (block != null) {
                blocks.put(type, block);
                it.remove();
            }
        }
    }

    private void fillItems(Map<TreeBlockType, Item> customBlockItems) {
        items.putAll(customBlockItems);

        var itemTypesRemain = new ArrayList<>(List.of(TreeBlockType.values()))
                .stream()
                .filter(TreeBlockType::hasItem)
                .filter(t -> !customBlockItems.containsKey(t))
                .toList();

        for (var type : itemTypesRemain) {
            if (type == TreeBlockType.DOOR) {
                items.put(type, doubleBlockItem(blocks.get(type)));
            } else {
                items.put(type, blockItem(blocks.get(type)));
            }
        }
    }

    private void fillCreativeTabs(Map<TreeBlockType, List<CreativeModeTab>> customItemTabs) {
        itemCreativeTabs.putAll(customItemTabs);

        var itemTypesRemain = new ArrayList<>(List.of(TreeBlockType.values()))
                .stream()
                .filter(TreeBlockType::hasItem)
                .filter(t -> !customItemTabs.containsKey(t))
                .toList();

        for (var type : itemTypesRemain) {
            itemCreativeTabs.put(type, getDefaultTabs(type));
        }

        for (var entry : itemCreativeTabs.entrySet()) {
            var item = items.get(entry.getKey());
            for (var tab : entry.getValue()) {
                if (!creativeTabItems.containsKey(tab)) {
                    creativeTabItems.put(tab, new ArrayList<>());
                }

                creativeTabItems.get(tab).add(item);
            }
        }
    }

    public WoodType getWoodType() {
        return woodType;
    }

    public Map<TreeBlockType, Block> getBlocks() {
        return ImmutableMap.<TreeBlockType, Block>builder().putAll(blocks).build();
    }

    public Map<TreeBlockType, Item> getItems() {
        return ImmutableMap.<TreeBlockType, Item>builder().putAll(items).build();
    }

    public Map<TreeBlockType, List<CreativeModeTab>> getItemTabs() {
        return ImmutableMap.<TreeBlockType, List<CreativeModeTab>>builder().putAll(itemCreativeTabs).build();
    }

    public Map<CreativeModeTab, List<Item>> getTabItems() {
        return ImmutableMap.<CreativeModeTab, List<Item>>builder().putAll(creativeTabItems).build();
    }

    public ResourceLocation getName() {
        return name;
    }

    public Block getBlock(TreeBlockType treeBlockType) {
        return blocks.get(treeBlockType);
    }

    public Item getItem(TreeBlockType treeBlockType) {
        return items.get(treeBlockType);
    }

    /**
     * Make translates map.
     * @return Map &lt;String locale, Map&lt;String key, String value&gt; translateMap&gt;
     */
    public Map<String, Map<String, String>> makeTranslates() {
        return translator.makeTranslates();
    }

    /**
     * Make translates map for specific locale. <br />
     * @return Map&lt;String key, String value&gt;
     */
    public Map<String, String> makeTranslatesForLocale(String locale) {
        return translator.makeTranslatesForLocale(locale);
    }

    public static class Builder {
        protected ResourceLocation name;

        protected Map<String, String> translateRoots = new HashMap<>();
        protected Map<String, Map<TreeBlockType, StringDecorator>> translates = new HashMap<>();
        protected Map<String, Map<TreeBlockType, String>> literalTranslates = new HashMap<>();

        protected Map<TreeBlockType, Block> customBlocks = new HashMap<>();
        protected Map<TreeBlockType, BlockBehaviour.Properties> customBlockProperties = new HashMap<>();
        protected Map<TreeBlockType, Item> customBlockItems = new HashMap<>();
        protected Map<TreeBlockType, List<CreativeModeTab>> customItemTabs = new HashMap<>();

        @Nullable
        protected AbstractTreeGrower grower;

        /**
         * New builder.
         * @param name ResourceLocation of tree.
         */
        public Builder(ResourceLocation name) {
            this.name = name;
        }

        /**
         * Translate root for a locale.
         * @param locale Locale of translation, like en_us or zh_cn.
         * @param translateRoot Translate root value, like Oak, Spruce.
         * @return Builder
         */
        public Builder translate(String locale, String translateRoot) {
            translateRoots.put(locale, translateRoot);
            return this;
        }

        /**
         * Translate decorators for a locale.
         * @param locale Locale of translation, like en_us or zh_cn.
         * @param translate Translate value.
         * @return Builder
         */
        public Builder translate(String locale, Map<TreeBlockType, StringDecorator> translate) {
            translates.put(locale, translate);
            return this;
        }

        /**
         * Custom literal translates for a specific block/item.
         * @param locale Locale of translation, like en_us or zh_cn.
         * @param treeBlockType Specific block.
         * @param literalTranslate Literal translate value.
         * @return Builder
         */
        public Builder translate(String locale, TreeBlockType treeBlockType, String literalTranslate) {
            if (!literalTranslates.containsKey(locale)) {
                literalTranslates.put(locale, new HashMap<>());
            }

            var map = literalTranslates.get(locale);
            map.put(treeBlockType, literalTranslate);

            return this;
        }

        /**
         * Custom block instead of auto generate.
         * @param treeBlockType Specific block.
         * @param block Block.
         * @return Builder
         */
        public Builder block(TreeBlockType treeBlockType, Block block) {
            customBlocks.put(treeBlockType, block);
            return this;
        }

        /**
         * Custom block Property instead of auto generate.
         * @param treeBlockType Specific block Property.
         * @param blockProperty Block property.
         * @return Builder
         */
        public Builder blockProperty(TreeBlockType treeBlockType, BlockBehaviour.Properties blockProperty) {
            customBlockProperties.put(treeBlockType, blockProperty);
            return this;
        }

        /**
         * Custom block item instead of auto generate.
         * @param treeBlockType Specific block item.
         * @param blockItem Block item.
         * @return Builder
         */
        public Builder blockItem(TreeBlockType treeBlockType, BlockItem blockItem) {
            customBlockItems.put(treeBlockType, blockItem);
            return this;
        }

        /**
         * Custom creative mode tab instead of auto generate.
         * @param treeBlockType Specific block item.
         * @param tab Item tab.
         * @return Builder
         */
        public Builder blockItemTab(TreeBlockType treeBlockType, CreativeModeTab tab) {
            if (!customItemTabs.containsKey(treeBlockType)) {
                customItemTabs.put(treeBlockType, new ArrayList<>());
            }

            customItemTabs.get(treeBlockType).add(tab);
            return this;
        }

        /**
         * Custom creative mode tabs instead of auto generate.
         * Will replace previous sets.
         * @param treeBlockType Specific block item.
         * @param tabs Item tabs.
         * @return Builder
         */
        public Builder blockItemTabs(TreeBlockType treeBlockType, List<CreativeModeTab> tabs) {
            customItemTabs.put(treeBlockType, tabs);
            return this;
        }

        /**
         * Set grower for sapling.
         * @param grower Grower.
         * @return Builder
         */
        public Builder grower(AbstractTreeGrower grower) {
            this.grower = grower;
            return this;
        }

        public TreeType build() {
            return new TreeType(name, translateRoots, translates, literalTranslates,
                    customBlocks, customBlockProperties,
                    customBlockItems, customItemTabs, grower);
        }
    }
}
