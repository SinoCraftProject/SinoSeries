package games.moegirl.sinocraft.sinocore.tree;

import com.google.common.collect.ImmutableMap;
import games.moegirl.sinocraft.sinocore.utility.decorator.StringDecorator;
import games.moegirl.sinocraft.sinocore.world.gen.ModConfiguredFeatures;
import games.moegirl.sinocraft.sinocore.world.gen.tree.ModTreeGrowerBase;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SaplingBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.properties.BlockSetType;
import net.minecraft.world.level.block.state.properties.WoodType;
import net.minecraft.world.level.levelgen.feature.configurations.TreeConfiguration;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

import javax.annotation.Nullable;
import java.util.*;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collectors;

import static games.moegirl.sinocraft.sinocore.tree.TreeBlockUtilities.*;

public class Tree {

    public final ResourceLocation name;

    protected final WoodType woodType;
    protected final BlockSetType blockSetType;

    protected final Map<TreeBlockType, Supplier<? extends Block>> blocks = new HashMap<>();
    protected final Map<TreeBlockType, Supplier<? extends Item>> items = new HashMap<>();
    protected final Map<TreeBlockType, List<CreativeModeTab>> itemCreativeTabs = new HashMap<>();
    protected final Map<CreativeModeTab, List<Supplier<? extends Item>>> creativeTabItems = new HashMap<>();

    protected final Map<TreeBlockType, BlockBehaviour.Properties> customBlockProperties;

    protected final TreeBlockNameTranslator translator;

    protected ModTreeGrowerBase grower;

    @Nullable
    protected TreeConfiguration configuration;

    protected Tree(ResourceLocation name,
                   Map<String, String> translateRoots,
                   Map<String, Map<TreeBlockType, StringDecorator>> customTranslates,
                   Map<String, Map<TreeBlockType, String>> customLiteralTranslates,
                   Map<TreeBlockType, Supplier<? extends Block>> customBlocks,
                   Map<TreeBlockType, BlockBehaviour.Properties> customBlockProperties,
                   Map<TreeBlockType, Supplier<? extends Item>> customItems,
                   Map<TreeBlockType, List<CreativeModeTab>> customItemTabs,
                   @Nullable ModTreeGrowerBase grower, @Nullable TreeConfiguration configuration) {
        this.name = name;

        this.translator = new TreeBlockNameTranslator(name, translateRoots, customTranslates, customLiteralTranslates);

        this.grower = Objects.requireNonNullElse(grower, new ModTreeGrowerBase(name));
        this.configuration = configuration;

        blockSetType = new BlockSetType(name.toString(), SoundType.WOOD,
                SoundEvents.WOODEN_DOOR_CLOSE, SoundEvents.WOODEN_DOOR_OPEN,
                SoundEvents.WOODEN_TRAPDOOR_CLOSE, SoundEvents.WOODEN_TRAPDOOR_OPEN,
                SoundEvents.WOODEN_PRESSURE_PLATE_CLICK_OFF, SoundEvents.WOODEN_PRESSURE_PLATE_CLICK_ON,
                SoundEvents.WOODEN_BUTTON_CLICK_OFF, SoundEvents.WOODEN_BUTTON_CLICK_ON);
        BlockSetType.register(blockSetType);
        woodType = new WoodType(name.toString(), blockSetType);
        WoodType.register(woodType);

        this.customBlockProperties = customBlockProperties;

        blocks.putAll(customBlocks);
        items.putAll(customItems);
        itemCreativeTabs.putAll(customItemTabs);

        TreeRegistry.getRegistry().computeIfAbsent(name.getNamespace(), id -> new ArrayList<>()).add(this);
    }

    public void register(DeferredRegister<Block> blockRegister, DeferredRegister<Item> itemRegister) {
        makeDefaultBlocks(blockRegister);
        makeDefaultBlockItems(itemRegister);
        makeDefaultItems(itemRegister);

        fillCreativeTabs();
    }

    private void makeDefaultBlocks(DeferredRegister<Block> blockRegister) {
        for (var entry : customBlockProperties.entrySet()) {
            var type = entry.getKey();
            if (type.isGeneralBlock()) {
                var block = blockRegister.register(type.makeRegistryName(getName()), () -> makeGeneralBlock(type, blockSetType, entry.getValue()));
                blocks.put(type, block);
            }
        }

        var blockTypesRemain = new ArrayList<>(List.of(TreeBlockType.values()))
                .stream()
                .filter(t -> !t.hasNoBlock())
                .filter(t -> !blocks.containsKey(t))
                .filter(t -> !customBlockProperties.containsKey(t))
                .collect(Collectors.toList());  // qyl27: Use Collectors but not toList, in order to get a mutable ArrayList.

        // Add block stage 1.
        var it = blockTypesRemain.iterator();
        while (it.hasNext()) {
            var type = it.next();

            Supplier<Block> blockSupplier = () -> switch (type) {
                case LOG, STRIPPED_LOG -> makeGeneralBlock(type, blockSetType, logProp());
                case LOG_WOOD, STRIPPED_LOG_WOOD -> makeGeneralBlock(type, blockSetType, woodProp());
                case PLANKS -> makeGeneralBlock(type, blockSetType, planksProp());
                case LEAVES -> makeGeneralBlock(type, blockSetType, leavesProp());
                case SLAB -> makeGeneralBlock(type, blockSetType, slabProp());
                case BUTTON -> makeGeneralBlock(type, blockSetType, buttonProp());
                case DOOR -> makeGeneralBlock(type, blockSetType, doorProp());
                case TRAPDOOR -> makeGeneralBlock(type, blockSetType, trapdoorProp());
                case FENCE, FENCE_GATE -> makeGeneralBlock(type, blockSetType, fenceProp());
                case PRESSURE_PLATE -> makeGeneralBlock(type, blockSetType, pressurePlateProp());
                default -> null;
            };

            if (type == TreeBlockType.LOG
                    || type == TreeBlockType.STRIPPED_LOG
                    || type == TreeBlockType.LOG_WOOD
                    || type == TreeBlockType.STRIPPED_LOG_WOOD
                    || type == TreeBlockType.PLANKS
                    || type == TreeBlockType.LEAVES
                    || type == TreeBlockType.SLAB
                    || type == TreeBlockType.BUTTON
                    || type == TreeBlockType.DOOR
                    || type == TreeBlockType.TRAPDOOR
                    || type == TreeBlockType.FENCE
                    || type == TreeBlockType.FENCE_GATE
                    || type == TreeBlockType.PRESSURE_PLATE) {
                var block = blockRegister.register(type.makeRegistryName(getName()), blockSupplier);
                blocks.put(type, block);
                it.remove();
            }
        }

        // Add block stage 2.
        it = blockTypesRemain.iterator();
        while (it.hasNext()) {
            var type = it.next();

            Supplier<Block> blockSupplier = () -> switch (type) {
                case SAPLING -> sapling(grower, saplingProp());
                case STAIRS -> stairs(getBlock(TreeBlockType.PLANKS));
                case SIGN -> makeSignBlock(type, signProp(), getWoodType());
                case HANGING_SIGN -> makeSignBlock(type, hangingSignProp(), getWoodType());
                default -> null;
            };

            if (type == TreeBlockType.SAPLING
                    || type == TreeBlockType.STAIRS
                    || type == TreeBlockType.SIGN
                    || type == TreeBlockType.HANGING_SIGN) {
                var block = blockRegister.register(type.makeRegistryName(getName()), blockSupplier);
                blocks.put(type, block);
                it.remove();
            }
        }

        // Add block stage 3.
        it = blockTypesRemain.iterator();
        while (it.hasNext()) {
            var type = it.next();

            Supplier<Block> blockSupplier = () -> switch (type) {
                case POTTED_SAPLING ->
                        pottedSapling((SaplingBlock) getBlock(TreeBlockType.SAPLING), pottedSaplingProp());
                case WALL_SIGN -> makeSignBlock(type, wallSignProp(getBlock(TreeBlockType.SIGN)), getWoodType());
                case WALL_HANGING_SIGN ->
                        makeSignBlock(type, wallHangingSignProp(getBlock(TreeBlockType.HANGING_SIGN)), getWoodType());
                default -> throw new IllegalArgumentException("Bad type: " + type);
            };

            var block = blockRegister.register(type.makeRegistryName(getName()), blockSupplier);
            blocks.put(type, block);
            it.remove();
        }
    }

    private void makeDefaultBlockItems(DeferredRegister<Item> itemRegister) {
        var itemTypesRemain = new ArrayList<>(List.of(TreeBlockType.values()))
                .stream()
                .filter(TreeBlockType::hasItem)
                .filter(t -> getBlocks().containsKey(t))
                .filter(t -> !getItems().containsKey(t))
                .toList();

        for (var type : itemTypesRemain) {
            RegistryObject<Item> item;
            if (type == TreeBlockType.DOOR) {
                item = itemRegister.register(type.makeRegistryName(getName()), () -> doubleBlockItem(getBlock(type)));
            } else if (type == TreeBlockType.SIGN) {
                item = itemRegister.register(type.makeRegistryName(getName()), () -> signBlockItem(getBlock(TreeBlockType.SIGN), getBlock(TreeBlockType.WALL_SIGN)));
            } else if (type == TreeBlockType.HANGING_SIGN) {
                item = itemRegister.register(type.makeRegistryName(getName()), () -> hangingSignBlockItem(getBlock(TreeBlockType.HANGING_SIGN), getBlock(TreeBlockType.WALL_HANGING_SIGN)));
            } else {
                item = itemRegister.register(type.makeRegistryName(getName()), () -> blockItem(getBlock(type)));
            }
            items.put(type, item);
        }
    }

    private void makeDefaultItems(DeferredRegister<Item> itemRegister) {
        var itemTypesRemain = new ArrayList<>(List.of(TreeBlockType.values()))
                .stream()
                .filter(TreeBlockType::hasItem)
                .filter(TreeBlockType::hasNoBlock)
                .filter(t -> !getItems().containsKey(t))
                .toList();

        // Todo: qyl27: supports boat or other item without block.
    }

    private void fillCreativeTabs() {
        var itemTypesRemain = new ArrayList<>(List.of(TreeBlockType.values()))
                .stream()
                .filter(TreeBlockType::hasItem)
                .filter(t -> !itemCreativeTabs.containsKey(t))
                .toList();

        for (var type : itemTypesRemain) {
            if (getItems().containsKey(type)) {
                itemCreativeTabs.put(type, getDefaultTabs(type));
            }
        }

        for (var entry : itemCreativeTabs.entrySet()) {
            var item = items.get(entry.getKey());
            if (item != null) {
                for (var tab : entry.getValue()) {
                    if (!creativeTabItems.containsKey(tab)) {
                        creativeTabItems.put(tab, new ArrayList<>());
                    }

                    creativeTabItems.get(tab).add(item);
                }
            }
        }
    }

    public WoodType getWoodType() {
        return woodType;
    }

    public Map<TreeBlockType, Supplier<? extends Block>> getBlocks() {
        return ImmutableMap.<TreeBlockType, Supplier<? extends Block>>builder().putAll(blocks).build();
    }

    public Map<TreeBlockType, Supplier<? extends Item>> getItems() {
        return ImmutableMap.<TreeBlockType, Supplier<? extends Item>>builder().putAll(items).build();
    }

    public Map<TreeBlockType, List<CreativeModeTab>> getItemTabs() {
        return ImmutableMap.<TreeBlockType, List<CreativeModeTab>>builder().putAll(itemCreativeTabs).build();
    }

    public Map<CreativeModeTab, List<Supplier<? extends Item>>> getTabItems() {
        return ImmutableMap.<CreativeModeTab, List<Supplier<? extends Item>>>builder().putAll(creativeTabItems).build();
    }

    public ResourceLocation getName() {
        return name;
    }

    public Block getBlock(TreeBlockType treeBlockType) {
        return blocks.get(treeBlockType).get();
    }

    public Item getItem(TreeBlockType treeBlockType) {
        return items.get(treeBlockType).get();
    }

    public ModTreeGrowerBase getGrower() {
        if (grower == null) {
            grower = new ModTreeGrowerBase(getName());
        }

        return grower;
    }

    public TreeConfiguration getFeaturedConfiguration() {
        if (configuration == null) {
            configuration = ModConfiguredFeatures.defaultTree(getBlock(TreeBlockType.LOG), getBlock(TreeBlockType.LEAVES));
        }

        return configuration;
    }

    /**
     * Make translates map.
     *
     * @return Map &lt;String locale, Map&lt;String key, String value&gt; translateMap&gt;
     */
    public Map<String, Map<String, String>> makeTranslates() {
        return translator.makeTranslates();
    }

    /**
     * Make translates map for specific locale. <br />
     *
     * @return Map&lt;String key, String value&gt;
     */
    public Map<String, String> makeTranslatesForLocale(String locale) {
        return translator.makeTranslatesForLocale(locale);
    }

    public static Builder builder(ResourceLocation name) {
        return new Builder(name);
    }

    public static class Builder {
        protected ResourceLocation name;

        protected Map<String, String> translateRoots = new HashMap<>();
        protected Map<String, Map<TreeBlockType, StringDecorator>> translates = new HashMap<>();
        protected Map<String, Map<TreeBlockType, String>> literalTranslates = new HashMap<>();

        protected Map<TreeBlockType, Supplier<? extends Block>> customBlocks = new HashMap<>();
        protected Map<TreeBlockType, BlockBehaviour.Properties> customBlockProperties = new HashMap<>();
        protected Map<TreeBlockType, Supplier<? extends Item>> customItems = new HashMap<>();
        protected Map<TreeBlockType, List<CreativeModeTab>> customItemTabs = new HashMap<>();

        @Nullable
        protected ModTreeGrowerBase grower;

        @Nullable
        protected TreeConfiguration configuration;

        /**
         * New builder.
         *
         * @param name ResourceLocation of tree.
         */
        public Builder(ResourceLocation name) {
            this.name = name;
        }

        /**
         * Translate root for a locale.
         *
         * @param locale        Locale of translation, like en_us or zh_cn.
         * @param translateRoot Translate root value, like Oak, Spruce.
         * @return Builder
         */
        public Builder translate(String locale, String translateRoot) {
            translateRoots.put(locale, translateRoot);
            return this;
        }

        /**
         * Translate decorators for a locale.
         *
         * @param locale    Locale of translation, like en_us or zh_cn.
         * @param translate Translate value.
         * @return Builder
         */
        public Builder translate(String locale, Map<TreeBlockType, StringDecorator> translate) {
            translates.put(locale, translate);
            return this;
        }

        /**
         * Custom literal translates for a specific block/item.
         *
         * @param locale           Locale of translation, like en_us or zh_cn.
         * @param treeBlockType    Specific block.
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
         *
         * @param treeBlockType Specific block.
         * @param block         Block.
         * @return Builder
         */
        public Builder block(TreeBlockType treeBlockType, Supplier<Block> block) {
            customBlocks.put(treeBlockType, block);
            return this;
        }

        /**
         * Custom block Property instead of auto generate.
         *
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
         *
         * @param treeBlockType Specific block item.
         * @param item          Block item.
         * @return Builder
         */
        public Builder item(TreeBlockType treeBlockType, Supplier<BlockItem> item) {
            customItems.put(treeBlockType, item);
            return this;
        }

        /**
         * Custom creative mode tab instead of auto generate.
         *
         * @param treeBlockType Specific block item.
         * @param tab           Item tab.
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
         *
         * @param treeBlockType Specific block item.
         * @param tabs          Item tabs.
         * @return Builder
         */
        public Builder blockItemTabs(TreeBlockType treeBlockType, List<CreativeModeTab> tabs) {
            customItemTabs.put(treeBlockType, tabs);
            return this;
        }

        /**
         * Set grower for sapling.
         *
         * @param grower Grower.
         * @return Builder
         */
        public Builder grower(ModTreeGrowerBase grower, TreeConfiguration configuration) {
            this.grower = grower;
            this.configuration = configuration;
            return this;
        }

        /**
         * Set grower for sapling.
         *
         * @param grower Grower.
         * @return Builder
         */
        public Builder grower(ModTreeGrowerBase grower) {
            this.grower = grower;
            return this;
        }

        // Todo: generate trees in wild.

        public Tree build() {
            return new Tree(name, translateRoots, translates, literalTranslates,
                    customBlocks, customBlockProperties,
                    customItems, customItemTabs,
                    grower, configuration);
        }
    }
}
