package games.moegirl.sinocraft.sinocore.tree;

import games.moegirl.sinocraft.sinocore.data.base.BaseCodecProvider;
import games.moegirl.sinocraft.sinocore.data.base.warn_provider.WarnBlockStateProvider;
import games.moegirl.sinocraft.sinocore.data.base.warn_provider.WarnItemModelProvider;
import net.minecraft.advancements.critereon.InventoryChangeTrigger;
import net.minecraft.advancements.critereon.ItemPredicate;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.minecraft.data.loot.LootTableProvider;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraft.data.tags.TagsProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraftforge.client.model.generators.ModelProvider;
import net.minecraftforge.common.data.BlockTagsProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.common.data.LanguageProvider;
import net.minecraftforge.data.event.GatherDataEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.registries.RegistryObject;

import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;
import java.util.function.Supplier;

/**
 * 树的 data generator 注册
 */
public class TreeDataHandler {

    private static final Map<String, TreeDataHandler> PROVIDERS = new HashMap<>();

    public static TreeDataHandler obtain(String modid) {
        return PROVIDERS.computeIfAbsent(modid, TreeDataHandler::new);
    }

    final List<Tree> langEn = new ArrayList<>();
    final List<Tree> langZh = new ArrayList<>();
    final List<Tree> mBlock = new ArrayList<>();
    final List<Tree> mItem = new ArrayList<>();
    final List<Tree> recipe = new ArrayList<>();
    final List<Tree> blockTags = new ArrayList<>();
    final List<Tree> itemTags = new ArrayList<>();
    final List<Tree> lootTable = new ArrayList<>();
    final Map<ResourceLocation, Supplier<ConfiguredFeature<?, ?>>> features = new HashMap<>();

    final String modid;
    PackOutput output;
    ExistingFileHelper helper;
    CompletableFuture<HolderLookup.Provider> provider;

    public TreeDataHandler(String modid) {
        this.modid = modid;
    }

    @SubscribeEvent
    public void onGatherEvent(GatherDataEvent event) {
        DataGenerator generator = event.getGenerator();
        output = generator.getPackOutput();
        helper = event.getExistingFileHelper();
        provider = event.getLookupProvider();

        if (!langEn.isEmpty()) generator.addProvider(true, new TEnLangProvider());
        if (!langZh.isEmpty()) generator.addProvider(true, new TZhLangProvider());
        if (!mBlock.isEmpty()) generator.addProvider(true, new TBlockStateProvider());
        if (!mItem.isEmpty()) generator.addProvider(true, new TItemModelProvider());
        if (!recipe.isEmpty()) generator.addProvider(true, new TRecipeProvider());
        if (!(blockTags.isEmpty() && itemTags.isEmpty())) {
            BlockTagsProvider b;
            generator.addProvider(true, b = new TBlockTagsProvider());
            if (!itemTags.isEmpty())
                generator.addProvider(true, new TItemTagsProvider(b));
        }
        if (!lootTable.isEmpty()) generator.addProvider(true, new TLootProvider());
        if (!features.isEmpty()) generator.addProvider(true, new TFeatureProvider());
    }

    void register(IEventBus bus) {
        if (features.isEmpty() && langEn.isEmpty() && langZh.isEmpty() && mBlock.isEmpty() && mItem.isEmpty()
                && recipe.isEmpty() && blockTags.isEmpty() && itemTags.isEmpty() && lootTable.isEmpty()) return;
        bus.register(this);
    }

    class TEnLangProvider extends LanguageProvider {

        public TEnLangProvider() {
            super(output, modid, "en_us");
        }

        @Override
        protected void addTranslations() {
            langEn.forEach(tree -> {
                String name = tree.properties().enName();
                addBlock(tree.sapling, name + " Sapling");
                addBlock(tree.log, name + " Log");
                addBlock(tree.strippedLog, "Stripped" + name + " Stripped");
                addBlock(tree.wood, name + " Wood");
                addBlock(tree.strippedWood, "Stripped " + name + " Wood");
                addBlock(tree.leaves, name + " Leaves");
                addBlock(tree.pottedSapling, "Potted " + name + " Sapling");
            });
        }

        @Override
        public String getName() {
            return "  Tree " + super.getName();
        }
    }

    class TZhLangProvider extends LanguageProvider {

        public TZhLangProvider() {
            super(output, modid, "zh_cn");
        }

        @Override
        protected void addTranslations() {
            langZh.forEach(tree -> {
                String name = tree.properties().zhName();
                String woodName = name.endsWith("树") ? name.substring(0, name.length() - 1) : name;
                addBlock(tree.sapling, name + "树苗");
                addBlock(tree.log, name + "原木");
                addBlock(tree.strippedLog, "去皮" + name + "原木");
                addBlock(tree.wood, woodName + "木");
                addBlock(tree.strippedWood, "去皮" + woodName + "木");
                addBlock(tree.leaves, name + "树叶");
                addBlock(tree.pottedSapling, name + "树苗盆栽");
            });
        }

        @Override
        public String getName() {
            return "  Tree " + super.getName();
        }
    }

    class TBlockStateProvider extends WarnBlockStateProvider {

        public TBlockStateProvider() {
            super(output, modid, helper);
        }

        @Override
        protected void registerStatesAndModels() {
            mBlock.forEach(tree -> {
                simpleBlock(tree.sapling(), models().cross(tree.sapling.getId().getPath(), blockTexture(tree.sapling())));
                logBlock(tree.log());
                logBlock(tree.strippedLog());
                simpleBlock(tree.wood(), models().cubeColumn(tree.wood.getId().getPath(), blockTexture(tree.log()), blockTexture(tree.log())));
                simpleBlock(tree.strippedWood(), models().cubeColumn(tree.strippedWood.getId().getPath(), blockTexture(tree.strippedLog()), blockTexture(tree.strippedLog())));
                simpleBlock(tree.leaves(), models().singleTexture(tree.leaves.getId().getPath(), mcLoc(ModelProvider.BLOCK_FOLDER + "/leaves"), "all", blockTexture(tree.leaves())));
                simpleBlock(tree.pottedSapling(), models().singleTexture(tree.pottedSapling.getId().getPath(), mcLoc(ModelProvider.BLOCK_FOLDER + "/flower_pot_cross"), "plant", modLoc(ModelProvider.BLOCK_FOLDER + "/" + tree.sapling.getId().getPath())));
            });
        }

        @Override
        public String getName() {
            return "  Tree " + super.getName();
        }
    }

    class TItemModelProvider extends WarnItemModelProvider {

        public TItemModelProvider() {
            super(TreeDataHandler.this.output, TreeDataHandler.this.modid, helper);
        }

        @Override
        protected void registerModels() {
            mItem.forEach(tree -> {
                addItem(tree.sapling);
                addBlockItem(tree.log);
                addBlockItem(tree.strippedLog);
                addBlockItem(tree.wood);
                addBlockItem(tree.strippedWood);
                addBlockItem(tree.leaves);
            });
        }

        private void addItem(RegistryObject<? extends Block> sapling) {
            String path = sapling.getId().getPath();
            withExistingParent(path, mcLoc("item/handheld")).texture("layer0", modLoc("block/" + path));
        }

        private void addBlockItem(RegistryObject<? extends Block> block) {
            String path = block.getId().getPath();
            withExistingParent(path, modLoc("block/" + path));
        }

        @Override
        public String getName() {
            return "  Tree " + super.getName();
        }
    }

    class TRecipeProvider extends RecipeProvider {

        public TRecipeProvider() {
            super(output);
        }

        @Override
        protected void buildRecipes(Consumer<FinishedRecipe> writer) {
            recipe.forEach(tree -> {
                InventoryChangeTrigger.TriggerInstance hasLog = InventoryChangeTrigger.TriggerInstance
                        .hasItems(ItemPredicate.Builder.item().of(tree.log()).build());
                // woodFromLogs
                ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, tree.wood(), 3).group("bark")
                        .define('#', tree.log())
                        .pattern("##")
                        .pattern("##")
                        .unlockedBy("has_log", hasLog)
                        .save(writer);
                ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, tree.strippedWood(), 3).group("bark")
                        .define('#', tree.strippedLog())
                        .pattern("##")
                        .pattern("##")
                        .unlockedBy("has_log", hasLog)
                        .save(writer);
            });
        }
    }

    class TBlockTagsProvider extends BlockTagsProvider {

        public TBlockTagsProvider() {
            super(output, provider, modid, helper);
        }

        @Override
        protected void addTags(HolderLookup.Provider provider) {
            blockTags.forEach(tree -> {
                tag(tree.tagLogs).add(tree.log(), tree.strippedLog(), tree.wood(), tree.strippedLog());
                tag(BlockTags.SAPLINGS).add(tree.sapling());
                tag(BlockTags.LOGS).addTag(tree.tagLogs);
                tag(BlockTags.LOGS_THAT_BURN).addTag(tree.tagLogs);
                tag(BlockTags.FLOWER_POTS).add(tree.pottedSapling());
                tag(BlockTags.LEAVES).add(tree.leaves());
            });
        }

        @Override
        public String getName() {
            return "  Tree " + super.getName();
        }
    }

    class TItemTagsProvider extends ItemTagsProvider {

        public TItemTagsProvider(TagsProvider<Block> b) {
            super(output, provider, b, modid, helper);
        }

        @Override
        protected void addTags(HolderLookup.Provider provider) {
            itemTags.forEach(tree -> {
                tag(tree.tagItemLogs).add(tree.log().asItem(), tree.strippedLog().asItem(), tree.wood().asItem(), tree.strippedLog().asItem());
                tag(ItemTags.SAPLINGS).add(tree.sapling().asItem());
                tag(ItemTags.LOGS_THAT_BURN).addTag(tree.tagItemLogs);
                tag(ItemTags.LEAVES).add(tree.leaves().asItem());
            });
        }

        @Override
        public String getName() {
            return "  Tree " + super.getName();
        }
    }

    class TLootProvider extends LootTableProvider {

        public TLootProvider() {
            super(output, new HashSet<>(), new ArrayList<>());
        }

        @Override
        public List<SubProviderEntry> getTables() {
            return lootTable.stream()
                    .map(TreeBlockLoot::new)
                    .map(loot -> new SubProviderEntry(() -> loot, LootContextParamSets.BLOCK))
                    .toList();
        }
    }

    class TFeatureProvider extends BaseCodecProvider {

        public TFeatureProvider() {
            super(provider, output, modid);
        }

        @Override
        public void addAll() {
            features.forEach((k, s) -> add(k, s.get()));
        }
    }
}

