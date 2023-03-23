package games.moegirl.sinocraft.sinocore.tree.depr.woodwork.event;

import games.moegirl.sinocraft.sinocore.data.abstracted.AbstractBlockStateProvider;
import games.moegirl.sinocraft.sinocore.data.abstracted.AbstractItemModelProvider;
import games.moegirl.sinocraft.sinocore.mixin_inter.INamedProvider;
import games.moegirl.sinocraft.sinocore.tree.depr.woodwork.Woodwork;
import games.moegirl.sinocraft.sinocore.tree.depr.woodwork.WoodworkBlockFamily;
import games.moegirl.sinocraft.sinocore.tree.depr.woodwork.WoodworkBlockLoot;
import net.minecraft.Util;
import net.minecraft.advancements.critereon.InventoryChangeTrigger;
import net.minecraft.advancements.critereon.ItemPredicate;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.BlockFamily;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.minecraft.data.loot.LootTableProvider;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.data.recipes.ShapelessRecipeBuilder;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraft.data.tags.TagsProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.PackType;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.DoorBlock;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraftforge.client.model.generators.ModelFile;
import net.minecraftforge.client.model.generators.ModelProvider;
import net.minecraftforge.common.Tags;
import net.minecraftforge.common.data.BlockTagsProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.common.data.LanguageProvider;
import net.minecraftforge.data.event.GatherDataEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import org.apache.commons.lang3.tuple.Pair;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;
import java.util.function.Supplier;

/**
 * 木制品的 data generator 注册
 */
public class WoodworkDataHandler {

    private static final Map<String, WoodworkDataHandler> PROVIDERS = new HashMap<>();
    private static final Logger LOGGER = LoggerFactory.getLogger(WoodworkDataHandler.class);

    public static WoodworkDataHandler obtain(String modid) {
        return PROVIDERS.computeIfAbsent(modid, WoodworkDataHandler::new);
    }

    public final List<Woodwork> lang = new ArrayList<>();
    public final List<Woodwork> mBlock = new ArrayList<>();
    public final List<Woodwork> mItem = new ArrayList<>();
    public final List<Pair<Woodwork, Supplier<? extends ItemLike>>> recipe = new ArrayList<>();
    public final List<Woodwork> blockTags = new ArrayList<>();
    public final List<Woodwork> itemTags = new ArrayList<>();
    public final List<Woodwork> lootTable = new ArrayList<>();

    final String modid;
    PackOutput output;
    ExistingFileHelper helper;
    CompletableFuture<HolderLookup.Provider> provider;

    public WoodworkDataHandler(String modid) {
        this.modid = modid;
    }

    @SubscribeEvent
    public void onGatherEvent(GatherDataEvent event) {
        DataGenerator generator = event.getGenerator();
        output = generator.getPackOutput();
        helper = event.getExistingFileHelper();
        provider = event.getLookupProvider();

        if (!lang.isEmpty()) {
            generator.addProvider(true, new WEnLangProvider());
            generator.addProvider(true, new WZhLangProvider());
        }
        if (!recipe.isEmpty()) generator.addProvider(true, new WRecipeProvider());
        if (!lootTable.isEmpty()) generator.addProvider(true, new WLootProvider());

        for (Woodwork woodwork : mBlock) {
            ResourceLocation woodName = new ResourceLocation(woodwork.type.name());
            ResourceLocation tex = new ResourceLocation(woodName.getNamespace(), "textures/entity/signs/" + woodName.getPath() + ".png");
            ResourceLocation tex2 = new ResourceLocation(woodName.getNamespace(), "textures/entity/signs/hanging/" + woodName.getPath() + ".png");
            if (!helper.exists(tex, PackType.CLIENT_RESOURCES)) {
                LOGGER.error("Not found sign skin at {}", tex);
            }
            if (!helper.exists(tex2, PackType.CLIENT_RESOURCES)) {
                LOGGER.error("Not found sign skin at {}", tex2);
            }
        }
    }

    public void register(IEventBus bus) {
        if (lang.isEmpty() && mBlock.isEmpty() && mItem.isEmpty() && recipe.isEmpty() && blockTags.isEmpty()
                && itemTags.isEmpty() && lootTable.isEmpty()) return;
        bus.register(this);
    }

    class WEnLangProvider extends LanguageProvider {

        public WEnLangProvider() {
            super(output, modid, "[woodwork]en_us");
        }

        @Override
        protected void addTranslations() {
            for (Woodwork woodwork : lang) {
                if (woodwork.lang.containsKey("en_us")) {
                    String name = woodwork.lang.get("en_us");
                    addBlock(woodwork.planks, name + " Planks");
                    addBlock(woodwork.sign, name + " Sign");
                    add(Util.makeDescriptionId("block", woodwork.wallSign.getId()), name + " Wall Sign");
                    addBlock(woodwork.pressurePlate, name + " Pressure Plate");
                    addBlock(woodwork.trapdoor, name + " Trap Trapdoor");
                    addBlock(woodwork.stairs, name + " Stairs");
                    addBlock(woodwork.button, name + " Button");
                    addBlock(woodwork.slab, name + " Slab");
                    addBlock(woodwork.fenceGate, name + " Fence Gate");
                    addBlock(woodwork.fence, name + " Fence");
                    addBlock(woodwork.door, name + " Door");
                }
            }
        }

        @Override
        public String getName() {
            return super.getName() + " Woodwork " + modid;
        }
    }

    class WZhLangProvider extends LanguageProvider {

        public WZhLangProvider() {
            super(output, modid, "[woodwork]zh_cn");
        }

        @Override
        protected void addTranslations() {
            for (Woodwork woodwork : lang) {
                if (woodwork.lang.containsKey("zh_cn")) {
                    String name = woodwork.lang.get("zh_cn");
                    addBlock(woodwork.planks, name + "木板");
                    addBlock(woodwork.sign, name + "木告示牌");
                    add(Util.makeDescriptionId("block", woodwork.wallSign.getId()), "墙上的" + name + "木告示牌");
                    addBlock(woodwork.pressurePlate, name + "木压力板");
                    addBlock(woodwork.trapdoor, name + "木活板门");
                    addBlock(woodwork.stairs, name + "木楼梯");
                    addBlock(woodwork.button, name + "木按钮");
                    addBlock(woodwork.slab, name + "木台阶");
                    addBlock(woodwork.fenceGate, name + "木栅栏门");
                    addBlock(woodwork.fence, name + "木栅栏");
                    addBlock(woodwork.door, name + "木门");
                }
            }
        }

        @Override
        public String getName() {
            return super.getName() + " Woodwork " + modid;
        }
    }

    class WRecipeProvider extends RecipeProvider implements INamedProvider {

        public WRecipeProvider() {
            super(output);
        }

        @Override
        protected void buildRecipes(Consumer<FinishedRecipe> writer) {
            recipe.forEach(p -> {
                Woodwork woodwork = p.getKey();
                ItemLike log = p.getValue().get();
                InventoryChangeTrigger.TriggerInstance hasLogs = InventoryChangeTrigger.TriggerInstance
                        .hasItems(ItemPredicate.Builder.item().of(ItemTags.LOGS).build());
                InventoryChangeTrigger.TriggerInstance hasPlank = InventoryChangeTrigger.TriggerInstance
                        .hasItems(ItemPredicate.Builder.item().of(woodwork.planks()).build());
                // planksFromLog
                ShapelessRecipeBuilder.shapeless(RecipeCategory.BUILDING_BLOCKS, woodwork.planks(), 4)
                        .group("planks")
                        .requires(log)
                        .unlockedBy("has_log", hasLogs)
                        .save(writer);

                WoodworkBlockFamily.generateRecipes(writer, new BlockFamily.Builder(woodwork.planks())
                        .button(woodwork.button())
                        .fence(woodwork.fence())
                        .fenceGate(woodwork.fenceGate())
                        .pressurePlate(woodwork.pressurePlate())
                        .sign(woodwork.sign(), woodwork.wallSign())
                        .slab(woodwork.slab())
                        .stairs(woodwork.stairs())
                        .door(woodwork.door())
                        .trapdoor(woodwork.trapdoor())
                        .recipeGroupPrefix("wooden")
                        .recipeUnlockedBy("has_planks")
                        .dontGenerateModel()
                        .getFamily());
            });
        }

        @Override
        public String sinocoreGetName() {
            return "Recipes Woodwork " + modid;
        }
    }

    class WLootProvider extends LootTableProvider implements INamedProvider {

        public WLootProvider() {
            super(output, new HashSet<>(), new ArrayList<>());
        }

        @Override
        public List<SubProviderEntry> getTables() {
            return lootTable.stream()
                    .map(WoodworkBlockLoot::new)
                    .map(loot -> new SubProviderEntry(() -> loot, LootContextParamSets.BLOCK))
                    .toList();
        }

        @Override
        public String sinocoreGetName() {
            return "Loot Tables Woodwork " + modid;
        }
    }
}

