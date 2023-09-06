package games.moegirl.sinocraft.sinocore.data.gen;

import com.google.common.collect.ImmutableMap;
import com.google.gson.JsonObject;
import games.moegirl.sinocraft.sinocore.block.ChestBlockBase;
import games.moegirl.sinocraft.sinocore.block.TrappedChestBlockBase;
import games.moegirl.sinocraft.sinocore.tree.Tree;
import games.moegirl.sinocraft.sinocore.tree.TreeBlockType;
import net.minecraft.advancements.critereon.*;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.BlockFamily;
import net.minecraft.data.CachedOutput;
import net.minecraft.data.DataProvider;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.HoneycombItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.AbstractCookingRecipe;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.registries.RegistryObject;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.function.BiFunction;
import java.util.function.Consumer;

public abstract class AbstractRecipeProvider implements DataProvider {
    protected final PackOutput output;
    protected final String modid;
    protected final PackOutput.PathProvider recipePathProvider;
    protected final PackOutput.PathProvider advancementPathProvider;

    public AbstractRecipeProvider(PackOutput output, String modid) {
        this.output = output;
        this.modid = modid;

        this.recipePathProvider = output.createPathProvider(PackOutput.Target.DATA_PACK, "recipes");
        this.advancementPathProvider = output.createPathProvider(PackOutput.Target.DATA_PACK, "advancements");
    }

    @Override
    public CompletableFuture<?> run(CachedOutput output) {
        List<CompletableFuture<?>> list = new ArrayList<>();
        this.buildRecipes((arg2) -> {
            list.add(DataProvider.saveStable(output, arg2.serializeRecipe(), this.recipePathProvider.json(arg2.getId())));
            JsonObject jsonobject = arg2.serializeAdvancement();
            if (jsonobject != null) {
                CompletableFuture<?> saveAdvancementFuture = this.saveAdvancement(output, arg2, jsonobject);
                if (saveAdvancementFuture != null) {
                    list.add(saveAdvancementFuture);
                }
            }
        });
        return CompletableFuture.allOf(list.toArray(CompletableFuture[]::new));
    }

    protected @Nullable CompletableFuture<?> saveAdvancement(CachedOutput output, FinishedRecipe finishedRecipe, JsonObject advancementJson) {
        return DataProvider.saveStable(output, advancementJson, this.advancementPathProvider.json(finishedRecipe.getAdvancementId()));
    }

    protected abstract void buildRecipes(Consumer<FinishedRecipe> writer);

    @Override
    public String getName() {
        return "Recipes: " + modid;
    }

    public ResourceLocation modLoc(String path) {
        return new ResourceLocation(modid, path);
    }

    /// <editor-fold desc="Methods from RecipeProvider.">

    private static final Map<BlockFamily.Variant, BiFunction<ItemLike, ItemLike, RecipeBuilder>> SHAPE_BUILDERS;

    static {
        SHAPE_BUILDERS = ImmutableMap.<BlockFamily.Variant, BiFunction<ItemLike, ItemLike, RecipeBuilder>>builder()
                .put(BlockFamily.Variant.BUTTON, (arg, arg2) -> buttonBuilder(arg, Ingredient.of(arg2)))
                .put(BlockFamily.Variant.CHISELED, (arg, arg2) -> chiseledBuilder(RecipeCategory.BUILDING_BLOCKS, arg, Ingredient.of(arg2)))
                .put(BlockFamily.Variant.CUT, (arg, arg2) -> cutBuilder(RecipeCategory.BUILDING_BLOCKS, arg, Ingredient.of(arg2)))
                .put(BlockFamily.Variant.DOOR, (arg, arg2) -> doorBuilder(arg, Ingredient.of(arg2)))
                .put(BlockFamily.Variant.CUSTOM_FENCE, (arg, arg2) -> fenceBuilder(arg, Ingredient.of(arg2)))
                .put(BlockFamily.Variant.FENCE, (arg, arg2) -> fenceBuilder(arg, Ingredient.of(arg2)))
                .put(BlockFamily.Variant.CUSTOM_FENCE_GATE, (arg, arg2) -> fenceGateBuilder(arg, Ingredient.of(arg2)))
                .put(BlockFamily.Variant.FENCE_GATE, (arg, arg2) -> fenceGateBuilder(arg, Ingredient.of(arg2)))
                .put(BlockFamily.Variant.SIGN, (arg, arg2) -> signBuilder(arg, Ingredient.of(arg2)))
                .put(BlockFamily.Variant.SLAB, (arg, arg2) -> slabBuilder(RecipeCategory.BUILDING_BLOCKS, arg, Ingredient.of(arg2)))
                .put(BlockFamily.Variant.STAIRS, (arg, arg2) -> stairBuilder(arg, Ingredient.of(arg2)))
                .put(BlockFamily.Variant.PRESSURE_PLATE, (arg, arg2) -> pressurePlateBuilder(RecipeCategory.REDSTONE, arg, Ingredient.of(arg2)))
                .put(BlockFamily.Variant.POLISHED, (arg, arg2) -> polishedBuilder(RecipeCategory.BUILDING_BLOCKS, arg, Ingredient.of(arg2)))
                .put(BlockFamily.Variant.TRAPDOOR, (arg, arg2) -> trapdoorBuilder(arg, Ingredient.of(arg2)))
                .put(BlockFamily.Variant.WALL, (arg, arg2) -> wallBuilder(RecipeCategory.DECORATIONS, arg, Ingredient.of(arg2))).build();
    }

    protected static void oneToOneConversionRecipe(Consumer<FinishedRecipe> finishedRecipeConsumer, ItemLike result, ItemLike ingredient, @javax.annotation.Nullable String group) {
        oneToOneConversionRecipe(finishedRecipeConsumer, result, ingredient, group, 1);
    }

    protected static void oneToOneConversionRecipe(Consumer<FinishedRecipe> finishedRecipeConsumer, ItemLike result, ItemLike ingredient, @javax.annotation.Nullable String group, int resultCount) {
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, result, resultCount).requires(ingredient).group(group).unlockedBy(getHasName(ingredient), has(ingredient)).save(finishedRecipeConsumer, getConversionRecipeName(result, ingredient));
    }

    protected static void oreSmelting(Consumer<FinishedRecipe> finishedRecipeConsumer, List<ItemLike> ingredients, RecipeCategory category, ItemLike result, float experience, int cookingTIme, String group) {
        oreCooking(finishedRecipeConsumer, RecipeSerializer.SMELTING_RECIPE, ingredients, category, result, experience, cookingTIme, group, "_from_smelting");
    }

    protected static void oreBlasting(Consumer<FinishedRecipe> finishedRecipeConsumer, List<ItemLike> ingredients, RecipeCategory category, ItemLike result, float experience, int cookingTime, String group) {
        oreCooking(finishedRecipeConsumer, RecipeSerializer.BLASTING_RECIPE, ingredients, category, result, experience, cookingTime, group, "_from_blasting");
    }

    protected static void oreCooking(Consumer<FinishedRecipe> finishedRecipeConsumer, RecipeSerializer<? extends AbstractCookingRecipe> cookingSerializer, List<ItemLike> ingredients, RecipeCategory category, ItemLike result, float experience, int cookingTime, String group, String recipeName) {
        for (ItemLike itemlike : ingredients) {
            SimpleCookingRecipeBuilder.generic(Ingredient.of(itemlike), category, result, experience, cookingTime, cookingSerializer).group(group).unlockedBy(getHasName(itemlike), has(itemlike)).save(finishedRecipeConsumer, getItemName(result) + recipeName + "_" + getItemName(itemlike));
        }
    }

    protected static void netheriteSmithing(Consumer<FinishedRecipe> finishedRecipeConsumer, Item ingredientItem, RecipeCategory category, Item resultItem) {
        SmithingTransformRecipeBuilder.smithing(Ingredient.of(Items.NETHERITE_UPGRADE_SMITHING_TEMPLATE), Ingredient.of(ingredientItem), Ingredient.of(Items.NETHERITE_INGOT), category, resultItem).unlocks("has_netherite_ingot", has(Items.NETHERITE_INGOT)).save(finishedRecipeConsumer, getItemName(resultItem) + "_smithing");
    }

    protected static void trimSmithing(Consumer<FinishedRecipe> consumer, Item arg, ResourceLocation name) {
        SmithingTrimRecipeBuilder.smithingTrim(Ingredient.of(arg), Ingredient.of(ItemTags.TRIMMABLE_ARMOR), Ingredient.of(ItemTags.TRIM_MATERIALS), RecipeCategory.MISC).unlocks("has_smithing_trim_template", has(arg)).save(consumer, name);
    }

    protected static void twoByTwoPacker(Consumer<FinishedRecipe> finishedRecipeConsumer, RecipeCategory category, ItemLike packed, ItemLike unpacked) {
        ShapedRecipeBuilder.shaped(category, packed, 1).define('#', unpacked).pattern("##").pattern("##").unlockedBy(getHasName(unpacked), has(unpacked)).save(finishedRecipeConsumer);
    }

    protected static void threeByThreePacker(Consumer<FinishedRecipe> finishedRecipeConsumer, RecipeCategory category, ItemLike packed, ItemLike unpacked, String criterionName) {
        ShapelessRecipeBuilder.shapeless(category, packed).requires((ItemLike)unpacked, 9).unlockedBy(criterionName, has(unpacked)).save(finishedRecipeConsumer);
    }

    protected static void threeByThreePacker(Consumer<FinishedRecipe> finishedRecipeConsumer, RecipeCategory category, ItemLike packed, ItemLike unpacked) {
        threeByThreePacker(finishedRecipeConsumer, category, packed, unpacked, getHasName(unpacked));
    }

    protected static void planksFromLog(Consumer<FinishedRecipe> finishedRecipeConsumer, ItemLike planks, TagKey<Item> logs, int resultCount) {
        ShapelessRecipeBuilder.shapeless(RecipeCategory.BUILDING_BLOCKS, planks, resultCount).requires(logs).group("planks").unlockedBy("has_log", has(logs)).save(finishedRecipeConsumer);
    }

    protected static void planksFromLogs(Consumer<FinishedRecipe> finishedRecipeConsumer, ItemLike planks, TagKey<Item> logs, int resultCount) {
        ShapelessRecipeBuilder.shapeless(RecipeCategory.BUILDING_BLOCKS, planks, resultCount).requires(logs).group("planks").unlockedBy("has_logs", has(logs)).save(finishedRecipeConsumer);
    }

    protected static void woodFromLogs(Consumer<FinishedRecipe> finishedRecipeConsumer, ItemLike wood, ItemLike log) {
        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, wood, 3).define('#', log).pattern("##").pattern("##").group("bark").unlockedBy("has_log", has(log)).save(finishedRecipeConsumer);
    }

    protected static void woodenBoat(Consumer<FinishedRecipe> finishedRecipeConsumer, ItemLike boat, ItemLike material) {
        ShapedRecipeBuilder.shaped(RecipeCategory.TRANSPORTATION, boat).define('#', material).pattern("# #").pattern("###").group("boat").unlockedBy("in_water", insideOf(Blocks.WATER)).save(finishedRecipeConsumer);
    }

    protected static void chestBoat(Consumer<FinishedRecipe> finishedRecipeConsumer, ItemLike boat, ItemLike material) {
        ShapelessRecipeBuilder.shapeless(RecipeCategory.TRANSPORTATION, boat).requires((ItemLike)Blocks.CHEST).requires(material).group("chest_boat").unlockedBy("has_boat", has(ItemTags.BOATS)).save(finishedRecipeConsumer);
    }

    protected static RecipeBuilder buttonBuilder(ItemLike button, Ingredient material) {
        return ShapelessRecipeBuilder.shapeless(RecipeCategory.REDSTONE, button).requires(material);
    }

    protected static RecipeBuilder doorBuilder(ItemLike door, Ingredient material) {
        return ShapedRecipeBuilder.shaped(RecipeCategory.REDSTONE, door, 3).define('#', material).pattern("##").pattern("##").pattern("##");
    }

    protected static RecipeBuilder fenceBuilder(ItemLike fence, Ingredient material) {
        int i = fence == Blocks.NETHER_BRICK_FENCE ? 6 : 3;
        Item item = fence == Blocks.NETHER_BRICK_FENCE ? Items.NETHER_BRICK : Items.STICK;
        return ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, fence, i).define('W', material).define('#', (ItemLike)item).pattern("W#W").pattern("W#W");
    }

    protected static RecipeBuilder fenceGateBuilder(ItemLike fenceGate, Ingredient material) {
        return ShapedRecipeBuilder.shaped(RecipeCategory.REDSTONE, fenceGate).define('#', (ItemLike)Items.STICK).define('W', material).pattern("#W#").pattern("#W#");
    }

    protected static void pressurePlate(Consumer<FinishedRecipe> finishedRecipeConsumer, ItemLike pressurePlate, ItemLike material) {
        pressurePlateBuilder(RecipeCategory.REDSTONE, pressurePlate, Ingredient.of(material)).unlockedBy(getHasName(material), has(material)).save(finishedRecipeConsumer);
    }

    protected static RecipeBuilder pressurePlateBuilder(RecipeCategory category, ItemLike pressurePlate, Ingredient material) {
        return ShapedRecipeBuilder.shaped(category, pressurePlate).define('#', material).pattern("##");
    }

    protected static void slab(Consumer<FinishedRecipe> finishedRecipeConsumer, RecipeCategory category, ItemLike pressurePlate, ItemLike material) {
        slabBuilder(category, pressurePlate, Ingredient.of(material)).unlockedBy(getHasName(material), has(material)).save(finishedRecipeConsumer);
    }

    protected static RecipeBuilder slabBuilder(RecipeCategory category, ItemLike slab, Ingredient material) {
        return ShapedRecipeBuilder.shaped(category, slab, 6).define('#', material).pattern("###");
    }

    protected static RecipeBuilder stairBuilder(ItemLike stairs, Ingredient material) {
        return ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, stairs, 4).define('#', material).pattern("#  ").pattern("## ").pattern("###");
    }

    protected static RecipeBuilder trapdoorBuilder(ItemLike trapdoor, Ingredient material) {
        return ShapedRecipeBuilder.shaped(RecipeCategory.REDSTONE, trapdoor, 2).define('#', material).pattern("###").pattern("###");
    }

    protected static RecipeBuilder signBuilder(ItemLike sign, Ingredient material) {
        return ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, sign, 3).group("sign").define('#', material).define('X', (ItemLike)Items.STICK).pattern("###").pattern("###").pattern(" X ");
    }

    protected static void hangingSign(Consumer<FinishedRecipe> finishedRecipeConsumer, ItemLike sign, ItemLike material) {
        ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, sign, 6).group("hanging_sign").define('#', material).define('X', (ItemLike)Items.CHAIN).pattern("X X").pattern("###").pattern("###").unlockedBy("has_stripped_logs", has(material)).save(finishedRecipeConsumer);
    }

    protected static void coloredWoolFromWhiteWoolAndDye(Consumer<FinishedRecipe> finishedRecipeConsumer, ItemLike dyedWool, ItemLike dye) {
        ShapelessRecipeBuilder.shapeless(RecipeCategory.BUILDING_BLOCKS, dyedWool).requires(dye).requires((ItemLike)Blocks.WHITE_WOOL).group("wool").unlockedBy("has_white_wool", has((ItemLike)Blocks.WHITE_WOOL)).save(finishedRecipeConsumer);
    }

    protected static void carpet(Consumer<FinishedRecipe> finishedRecipeConsumer, ItemLike carpet, ItemLike material) {
        ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, carpet, 3).define('#', material).pattern("##").group("carpet").unlockedBy(getHasName(material), has(material)).save(finishedRecipeConsumer);
    }

    protected static void coloredCarpetFromWhiteCarpetAndDye(Consumer<FinishedRecipe> finishedRecipeConsumer, ItemLike dyedCarpet, ItemLike dye) {
        ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, dyedCarpet, 8).define('#', (ItemLike)Blocks.WHITE_CARPET).define('$', dye).pattern("###").pattern("#$#").pattern("###").group("carpet").unlockedBy("has_white_carpet", has((ItemLike)Blocks.WHITE_CARPET)).unlockedBy(getHasName(dye), has(dye)).save(finishedRecipeConsumer, getConversionRecipeName(dyedCarpet, Blocks.WHITE_CARPET));
    }

    protected static void bedFromPlanksAndWool(Consumer<FinishedRecipe> finishedRecipeConsumer, ItemLike bed, ItemLike wool) {
        ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, bed).define('#', wool).define('X', ItemTags.PLANKS).pattern("###").pattern("XXX").group("bed").unlockedBy(getHasName(wool), has(wool)).save(finishedRecipeConsumer);
    }

    protected static void bedFromWhiteBedAndDye(Consumer<FinishedRecipe> finishedRecipeConsumer, ItemLike dyedBed, ItemLike dye) {
        ShapelessRecipeBuilder.shapeless(RecipeCategory.DECORATIONS, dyedBed).requires((ItemLike)Items.WHITE_BED).requires(dye).group("dyed_bed").unlockedBy("has_bed", has((ItemLike)Items.WHITE_BED)).save(finishedRecipeConsumer, getConversionRecipeName(dyedBed, Items.WHITE_BED));
    }

    protected static void banner(Consumer<FinishedRecipe> finishedRecipeConsumer, ItemLike banner, ItemLike material) {
        ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, banner).define('#', material).define('|', (ItemLike)Items.STICK).pattern("###").pattern("###").pattern(" | ").group("banner").unlockedBy(getHasName(material), has(material)).save(finishedRecipeConsumer);
    }

    protected static void stainedGlassFromGlassAndDye(Consumer<FinishedRecipe> finishedRecipeConsumer, ItemLike stainedGlass, ItemLike dye) {
        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, stainedGlass, 8).define('#', (ItemLike)Blocks.GLASS).define('X', dye).pattern("###").pattern("#X#").pattern("###").group("stained_glass").unlockedBy("has_glass", has((ItemLike)Blocks.GLASS)).save(finishedRecipeConsumer);
    }

    protected static void stainedGlassPaneFromStainedGlass(Consumer<FinishedRecipe> finishedRecipeConsumer, ItemLike stainedGlassPane, ItemLike stainedGlass) {
        ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, stainedGlassPane, 16).define('#', stainedGlass).pattern("###").pattern("###").group("stained_glass_pane").unlockedBy("has_glass", has(stainedGlass)).save(finishedRecipeConsumer);
    }

    protected static void stainedGlassPaneFromGlassPaneAndDye(Consumer<FinishedRecipe> finishedRecipeConsumer, ItemLike stainedGlassPane, ItemLike dye) {
        ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, stainedGlassPane, 8).define('#', (ItemLike)Blocks.GLASS_PANE).define('$', dye).pattern("###").pattern("#$#").pattern("###").group("stained_glass_pane").unlockedBy("has_glass_pane", has((ItemLike)Blocks.GLASS_PANE)).unlockedBy(getHasName(dye), has(dye)).save(finishedRecipeConsumer, getConversionRecipeName(stainedGlassPane, Blocks.GLASS_PANE));
    }

    protected static void coloredTerracottaFromTerracottaAndDye(Consumer<FinishedRecipe> finishedRecipeConsumer, ItemLike coloredTerracotta, ItemLike dye) {
        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, coloredTerracotta, 8).define('#', (ItemLike)Blocks.TERRACOTTA).define('X', dye).pattern("###").pattern("#X#").pattern("###").group("stained_terracotta").unlockedBy("has_terracotta", has((ItemLike)Blocks.TERRACOTTA)).save(finishedRecipeConsumer);
    }

    protected static void concretePowder(Consumer<FinishedRecipe> finishedRecipeConsumer, ItemLike dyedConcretePowder, ItemLike dye) {
        ShapelessRecipeBuilder.shapeless(RecipeCategory.BUILDING_BLOCKS, dyedConcretePowder, 8).requires(dye).requires((ItemLike)Blocks.SAND, 4).requires((ItemLike)Blocks.GRAVEL, 4).group("concrete_powder").unlockedBy("has_sand", has((ItemLike)Blocks.SAND)).unlockedBy("has_gravel", has((ItemLike)Blocks.GRAVEL)).save(finishedRecipeConsumer);
    }

    protected static void candle(Consumer<FinishedRecipe> finishedRecipeConsumer, ItemLike candle, ItemLike dye) {
        ShapelessRecipeBuilder.shapeless(RecipeCategory.DECORATIONS, candle).requires((ItemLike)Blocks.CANDLE).requires(dye).group("dyed_candle").unlockedBy(getHasName(dye), has(dye)).save(finishedRecipeConsumer);
    }

    protected static void wall(Consumer<FinishedRecipe> finishedRecipeConsumer, RecipeCategory category, ItemLike wall, ItemLike material) {
        wallBuilder(category, wall, Ingredient.of(material)).unlockedBy(getHasName(material), has(material)).save(finishedRecipeConsumer);
    }

    protected static RecipeBuilder wallBuilder(RecipeCategory category, ItemLike wall, Ingredient material) {
        return ShapedRecipeBuilder.shaped(category, wall, 6).define('#', material).pattern("###").pattern("###");
    }

    protected static void polished(Consumer<FinishedRecipe> finishedRecipeConsumer, RecipeCategory category, ItemLike result, ItemLike material) {
        polishedBuilder(category, result, Ingredient.of(material)).unlockedBy(getHasName(material), has(material)).save(finishedRecipeConsumer);
    }

    protected static RecipeBuilder polishedBuilder(RecipeCategory category, ItemLike result, Ingredient material) {
        return ShapedRecipeBuilder.shaped(category, result, 4).define('S', material).pattern("SS").pattern("SS");
    }

    protected static void cut(Consumer<FinishedRecipe> finishedRecipeConsumer, RecipeCategory category, ItemLike cutResult, ItemLike material) {
        cutBuilder(category, cutResult, Ingredient.of(material)).unlockedBy(getHasName(material), has(material)).save(finishedRecipeConsumer);
    }

    protected static ShapedRecipeBuilder cutBuilder(RecipeCategory category, ItemLike cutResult, Ingredient material) {
        return ShapedRecipeBuilder.shaped(category, cutResult, 4).define('#', material).pattern("##").pattern("##");
    }

    protected static void chiseled(Consumer<FinishedRecipe> finishedRecipeConsumer, RecipeCategory category, ItemLike chiseledResult, ItemLike material) {
        chiseledBuilder(category, chiseledResult, Ingredient.of(material)).unlockedBy(getHasName(material), has(material)).save(finishedRecipeConsumer);
    }

    protected static void mosaicBuilder(Consumer<FinishedRecipe> finishedRecipeConsumer, RecipeCategory category, ItemLike result, ItemLike material) {
        ShapedRecipeBuilder.shaped(category, result).define('#', material).pattern("#").pattern("#").unlockedBy(getHasName(material), has(material)).save(finishedRecipeConsumer);
    }

    protected static ShapedRecipeBuilder chiseledBuilder(RecipeCategory category, ItemLike chiseledResult, Ingredient material) {
        return ShapedRecipeBuilder.shaped(category, chiseledResult).define('#', material).pattern("#").pattern("#");
    }

    protected static void stonecutterResultFromBase(Consumer<FinishedRecipe> finishedRecipeConsumer, RecipeCategory category, ItemLike result, ItemLike material) {
        stonecutterResultFromBase(finishedRecipeConsumer, category, result, material, 1);
    }

    protected static void stonecutterResultFromBase(Consumer<FinishedRecipe> finishedRecipeConsumer, RecipeCategory category, ItemLike result, ItemLike material, int resultCount) {
        SingleItemRecipeBuilder var10000 = SingleItemRecipeBuilder.stonecutting(Ingredient.of(material), category, result, resultCount).unlockedBy(getHasName(material), has(material));
        String var10002 = getConversionRecipeName(result, material);
        var10000.save(finishedRecipeConsumer, var10002 + "_stonecutting");
    }

    protected static void smeltingResultFromBase(Consumer<FinishedRecipe> finishedRecipeConsumer, ItemLike result, ItemLike ingredient) {
        SimpleCookingRecipeBuilder.smelting(Ingredient.of(ingredient), RecipeCategory.BUILDING_BLOCKS, result, 0.1F, 200).unlockedBy(getHasName(ingredient), has(ingredient)).save(finishedRecipeConsumer);
    }

    protected static void nineBlockStorageRecipes(Consumer<FinishedRecipe> finishedRecipeConsumer, RecipeCategory unpackedCategory, ItemLike unpacked, RecipeCategory packedCategory, ItemLike packed) {
        nineBlockStorageRecipes(finishedRecipeConsumer, unpackedCategory, unpacked, packedCategory, packed, getSimpleRecipeName(packed), (String)null, getSimpleRecipeName(unpacked), (String)null);
    }

    protected static void nineBlockStorageRecipesWithCustomPacking(Consumer<FinishedRecipe> finishedRecipeConsumer, RecipeCategory unpackedCategory, ItemLike unpacked, RecipeCategory packedCategory, ItemLike packed, String packedName, String packedGroup) {
        nineBlockStorageRecipes(finishedRecipeConsumer, unpackedCategory, unpacked, packedCategory, packed, packedName, packedGroup, getSimpleRecipeName(unpacked), (String)null);
    }

    protected static void nineBlockStorageRecipesRecipesWithCustomUnpacking(Consumer<FinishedRecipe> finishedRecipeConsumer, RecipeCategory unpackedCategory, ItemLike unpacked, RecipeCategory packedCategory, ItemLike packed, String unpackedName, String unpackedGroup) {
        nineBlockStorageRecipes(finishedRecipeConsumer, unpackedCategory, unpacked, packedCategory, packed, getSimpleRecipeName(packed), (String)null, unpackedName, unpackedGroup);
    }

    protected static void nineBlockStorageRecipes(Consumer<FinishedRecipe> finishedRecipeConsumer, RecipeCategory unpackedCategory, ItemLike unpacked, RecipeCategory packedCategory, ItemLike packed, String packedName, @javax.annotation.Nullable String packedGroup, String unpackedName, @javax.annotation.Nullable String unpackedGroup) {
        ShapelessRecipeBuilder.shapeless(unpackedCategory, unpacked, 9).requires(packed).group(unpackedGroup).unlockedBy(getHasName(packed), has(packed)).save(finishedRecipeConsumer, new ResourceLocation(unpackedName));
        ShapedRecipeBuilder.shaped(packedCategory, packed).define('#', unpacked).pattern("###").pattern("###").pattern("###").group(packedGroup).unlockedBy(getHasName(unpacked), has(unpacked)).save(finishedRecipeConsumer, new ResourceLocation(packedName));
    }

    protected static void copySmithingTemplate(Consumer<FinishedRecipe> consumer, ItemLike arg, TagKey<Item> arg2) {
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, arg, 2).define('#', (ItemLike)Items.DIAMOND).define('C', arg2).define('S', arg).pattern("#S#").pattern("#C#").pattern("###").unlockedBy(getHasName(arg), has(arg)).save(consumer);
    }

    protected static void copySmithingTemplate(Consumer<FinishedRecipe> consumer, ItemLike arg, ItemLike arg2) {
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, arg, 2).define('#', (ItemLike)Items.DIAMOND).define('C', arg2).define('S', arg).pattern("#S#").pattern("#C#").pattern("###").unlockedBy(getHasName(arg), has(arg)).save(consumer);
    }

    protected static void simpleCookingRecipe(Consumer<FinishedRecipe> finishedRecipeConsumer, String cookingMethod, RecipeSerializer<? extends AbstractCookingRecipe> cookingSerializer, int cookingTime, ItemLike ingredient, ItemLike result, float experience) {
        SimpleCookingRecipeBuilder var10000 = SimpleCookingRecipeBuilder.generic(Ingredient.of(ingredient), RecipeCategory.FOOD, result, experience, cookingTime, cookingSerializer).unlockedBy(getHasName(ingredient), has(ingredient));
        String var10002 = getItemName(result);
        var10000.save(finishedRecipeConsumer, var10002 + "_from_" + cookingMethod);
    }

    protected static void waxRecipes(Consumer<FinishedRecipe> finishedRecipeConsumer) {
        HoneycombItem.WAXABLES.get().forEach((arg, arg2) -> {
            ShapelessRecipeBuilder.shapeless(RecipeCategory.BUILDING_BLOCKS, arg2).requires((ItemLike)arg).requires((ItemLike)Items.HONEYCOMB).group(getItemName(arg2)).unlockedBy(getHasName(arg), has((ItemLike)arg)).save(finishedRecipeConsumer, getConversionRecipeName(arg2, Items.HONEYCOMB));
        });
    }

    protected static void generateRecipes(Consumer<FinishedRecipe> finishedRecipeConsumer, BlockFamily family) {
        family.getVariants().forEach((arg2, arg3) -> {
            BiFunction<ItemLike, ItemLike, RecipeBuilder> bifunction = SHAPE_BUILDERS.get(arg2);
            ItemLike itemlike = getBaseBlock(family, arg2);
            if (bifunction != null) {
                RecipeBuilder recipebuilder = bifunction.apply(arg3, itemlike);
                family.getRecipeGroupPrefix().ifPresent((string) -> {
                    recipebuilder.group(string + (arg2 == BlockFamily.Variant.CUT ? "" : "_" + arg2.getName()));
                });
                recipebuilder.unlockedBy(family.getRecipeUnlockedBy().orElseGet(() -> getHasName(itemlike)), has(itemlike));
                recipebuilder.save(finishedRecipeConsumer);
            }

            if (arg2 == BlockFamily.Variant.CRACKED) {
                smeltingResultFromBase(finishedRecipeConsumer, arg3, itemlike);
            }

        });
    }

    protected static Block getBaseBlock(BlockFamily family, BlockFamily.Variant variant) {
        if (variant == BlockFamily.Variant.CHISELED) {
            if (!family.getVariants().containsKey(BlockFamily.Variant.SLAB)) {
                throw new IllegalStateException("Slab is not defined for the family.");
            } else {
                return family.get(BlockFamily.Variant.SLAB);
            }
        } else {
            return family.getBaseBlock();
        }
    }

    /**
     * Creates a new {@link EnterBlockTrigger} for use with recipe unlock criteria.
     */
    protected static EnterBlockTrigger.TriggerInstance insideOf(Block block) {
        return new EnterBlockTrigger.TriggerInstance(ContextAwarePredicate.ANY, block, StatePropertiesPredicate.ANY);
    }

    protected static InventoryChangeTrigger.TriggerInstance has(MinMaxBounds.Ints count, ItemLike item) {
        return inventoryTrigger(ItemPredicate.Builder.item().of(item).withCount(count).build());
    }

    /**
     * Creates a new {@link InventoryChangeTrigger} that checks for a player having a certain item.
     */
    protected static InventoryChangeTrigger.TriggerInstance has(ItemLike itemLike) {
        return inventoryTrigger(ItemPredicate.Builder.item().of(itemLike).build());
    }

    /**
     * Creates a new {@link InventoryChangeTrigger} that checks for a player having an item within the given tag.
     */
    protected static InventoryChangeTrigger.TriggerInstance has(TagKey<Item> tag) {
        return inventoryTrigger(ItemPredicate.Builder.item().of(tag).build());
    }

    /**
     * Creates a new {@link InventoryChangeTrigger} that checks for a player having a certain item.
     */
    protected static InventoryChangeTrigger.TriggerInstance inventoryTrigger(ItemPredicate... predicates) {
        return new InventoryChangeTrigger.TriggerInstance(ContextAwarePredicate.ANY, MinMaxBounds.Ints.ANY, MinMaxBounds.Ints.ANY, MinMaxBounds.Ints.ANY, predicates);
    }

    protected static String getHasName(ItemLike itemLike) {
        return "has_" + getItemName(itemLike);
    }

    protected static String getItemName(ItemLike itemLike) {
        return BuiltInRegistries.ITEM.getKey(itemLike.asItem()).getPath();
    }

    protected static String getSimpleRecipeName(ItemLike itemLike) {
        return getItemName(itemLike);
    }

    protected static String getConversionRecipeName(ItemLike result, ItemLike ingredient) {
        String var10000 = getItemName(result);
        return var10000 + "_from_" + getItemName(ingredient);
    }

    protected static String getSmeltingRecipeName(ItemLike itemLike) {
        return getItemName(itemLike) + "_from_smelting";
    }

    protected static String getBlastingRecipeName(ItemLike itemLike) {
        return getItemName(itemLike) + "_from_blasting";
    }

    protected void chest(Tree tree, Consumer<FinishedRecipe> writer,
                         RegistryObject<? extends ChestBlockBase> chest, RegistryObject<? extends TrappedChestBlockBase> trappedChest) {
        ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, chest.get())
                .define('#', tree.getItem(TreeBlockType.PLANKS))
                .pattern("###")
                .pattern("# #")
                .pattern("###")
                .unlockedBy("has_lots_of_items", new InventoryChangeTrigger.TriggerInstance(ContextAwarePredicate.ANY, MinMaxBounds.Ints.atLeast(10), MinMaxBounds.Ints.ANY, MinMaxBounds.Ints.ANY, new ItemPredicate[0]))
                .save(writer);

        ShapelessRecipeBuilder.shapeless(RecipeCategory.REDSTONE, trappedChest.get())
                .requires(chest.get())
                .requires(Blocks.TRIPWIRE_HOOK)
                .unlockedBy("has_tripwire_hook", has(Blocks.TRIPWIRE_HOOK))
                .save(writer);
    }

    /// </editor-fold>
}
