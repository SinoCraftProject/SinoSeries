package games.moegirl.sinocraft.sinocore.tree.depr.event;

import games.moegirl.sinocraft.sinocore.data.BaseCodecProvider;
import games.moegirl.sinocraft.sinocore.data.abstracted.AbstractBlockStateProvider;
import games.moegirl.sinocraft.sinocore.data.abstracted.AbstractItemModelProvider;
import games.moegirl.sinocraft.sinocore.mixin_inter.INamedProvider;
import games.moegirl.sinocraft.sinocore.tree.Tree;
import games.moegirl.sinocraft.sinocore.tree.depr.TreeBlockLoot;
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
import org.jetbrains.annotations.NotNull;

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

    public final List<Tree> lang = new ArrayList<>();
    public final List<Tree> mBlock = new ArrayList<>();
    public final List<Tree> mItem = new ArrayList<>();
    public final List<Tree> recipe = new ArrayList<>();
    public final List<Tree> blockTags = new ArrayList<>();
    public final List<Tree> itemTags = new ArrayList<>();
    public final List<Tree> lootTable = new ArrayList<>();
    public final Map<ResourceLocation, Supplier<ConfiguredFeature<?, ?>>> features = new HashMap<>();

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

        if (!lang.isEmpty()) {
            generator.addProvider(true, new TEnLangProvider());
            generator.addProvider(true, new TZhLangProvider());
        }

        if (!features.isEmpty()) generator.addProvider(true, new TFeatureProvider());

        ((IDataGenerator) generator).sinocoreSetPost(modid, output, helper);
    }

    public void register(IEventBus bus) {
        if (features.isEmpty() && lang.isEmpty() && mBlock.isEmpty() && mItem.isEmpty() && recipe.isEmpty()
                && blockTags.isEmpty() && itemTags.isEmpty() && lootTable.isEmpty()) return;
        bus.register(this);
    }

    class TEnLangProvider extends LanguageProvider {

        public TEnLangProvider() {
            super(output, modid, "[tree]en_us");
        }

        @Override
        protected void addTranslations() {
            for (Tree tree : lang) {
                if (tree.properties().langs().containsKey("en_us")) {
                    String name = tree.properties().langs().get("en_us");
                    addBlock(tree.sapling, name + " Sapling");
                    addBlock(tree.log, name + " Log");
                    addBlock(tree.strippedLog, "Stripped" + name + " Stripped");
                    addBlock(tree.wood, name + " Wood");
                    addBlock(tree.strippedWood, "Stripped " + name + " Wood");
                    addBlock(tree.leaves, name + " Leaves");
                    addBlock(tree.pottedSapling, "Potted " + name + " Sapling");
                }
            }
        }

        @Override
        public String getName() {
            return super.getName() + " Tree " + modid;
        }
    }

    class TZhLangProvider extends LanguageProvider {

        public TZhLangProvider() {
            super(output, modid, "[tree]zh_cn");
        }

        @Override
        protected void addTranslations() {
            for (Tree tree : lang) {
                if (tree.properties().langs().containsKey("zh_cn")) {
                    String name = tree.properties().langs().get("zh_cn");
                    String woodName = name.endsWith("树") ? name.substring(0, name.length() - 1) : name;
                    addBlock(tree.sapling, name + "树苗");
                    addBlock(tree.log, name + "原木");
                    addBlock(tree.strippedLog, "去皮" + name + "原木");
                    addBlock(tree.wood, woodName + "木");
                    addBlock(tree.strippedWood, "去皮" + woodName + "木");
                    addBlock(tree.leaves, name + "树叶");
                    addBlock(tree.pottedSapling, name + "树苗盆栽");
                }
            }
        }

        @Override
        public String getName() {
            return super.getName() + " Tree " + modid;
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

        @Override
        public String getName() {
            return super.getName() + " Tree " + modid;
        }
    }
}

