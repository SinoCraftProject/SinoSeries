package games.moegirl.sinocraft.sinocore.data.gen.recipe;

import games.moegirl.sinocraft.sinocore.data.gen.DataGenContext;
import games.moegirl.sinocraft.sinocore.data.gen.ISinoDataProvider;
import games.moegirl.sinocraft.sinocore.interfaces.bridge.ISinoRenamedProviderBridge;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.advancements.Criterion;
import net.minecraft.advancements.critereon.*;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.BlockFamily;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.crafting.AbstractCookingRecipe;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

public abstract class AbstractRecipeProvider extends RecipeProvider implements ISinoRenamedProviderBridge, ISinoDataProvider {

    protected final String modId;

    public AbstractRecipeProvider(PackOutput output, String modId, CompletableFuture<HolderLookup.Provider> registries) {
        super(output, registries);
        this.modId = modId;
    }

    public AbstractRecipeProvider(DataGenContext context) {
        this(context.getOutput(), context.getModId(), context.getRegistries());
    }

    @Override
    public String sino$getNewName() {
        return "Recipes: " + modId;
    }

    @Override
    public @NotNull String getModId() {
        return modId;
    }

    // region Recipes

    public static <T extends AbstractCookingRecipe> void oreCooking(
            RecipeOutput recipeOutput, ItemLike result, float experience, int cookingTime, List<ItemLike> ingredients,
            RecipeSerializer<T> serializer, AbstractCookingRecipe.Factory<T> factory,
            RecipeCategory category, String group, String suffix) {
        for (ItemLike itemlike : ingredients) {
            SimpleCookingRecipeBuilder.generic(Ingredient.of(itemlike), category, result, experience, cookingTime, serializer, factory)
                    .group(group)
                    .unlockedBy(getHasName(itemlike), has(itemlike))
                    .save(recipeOutput, getItemName(result) + suffix + "_" + getItemName(itemlike));
        }
    }

    public static void smeltingResultFromBase(RecipeOutput recipeOutput, ItemLike result, ItemLike ingredient) {
        SimpleCookingRecipeBuilder
                .smelting(Ingredient.of(ingredient), RecipeCategory.BUILDING_BLOCKS, result, 0.1f, 200)
                .unlockedBy(getHasName(ingredient), has(ingredient))
                .save(recipeOutput);
    }

    public static void nineBlockStorageRecipes(RecipeOutput recipeOutput, RecipeCategory unpackedCategory,
                                               ItemLike unpacked, RecipeCategory packedCategory, ItemLike packed,
                                               String packedName, @Nullable String packedGroup, String unpackedName,
                                               @Nullable String unpackedGroup) {
        ShapelessRecipeBuilder.shapeless(unpackedCategory, unpacked, 9)
                .requires(packed).group(unpackedGroup)
                .unlockedBy(getHasName(packed), has(packed))
                .save(recipeOutput, ResourceLocation.parse(unpackedName));
        ShapedRecipeBuilder.shaped(packedCategory, packed)
                .group(packedGroup).unlockedBy(getHasName(unpacked), has(unpacked))
                .define('#', unpacked)
                .pattern("###")
                .pattern("###")
                .pattern("###")
                .save(recipeOutput, ResourceLocation.parse(packedName));
    }

    public static <T extends AbstractCookingRecipe> void simpleCookingRecipe(
            RecipeOutput recipeOutput, String cookingMethod,
            ItemLike result, float experience, int cookingTime, ItemLike material,
            RecipeSerializer<T> serializer, AbstractCookingRecipe.Factory<T> factory) {
        SimpleCookingRecipeBuilder
                .generic(Ingredient.of(material), RecipeCategory.FOOD, result, experience, cookingTime, serializer, factory)
                .unlockedBy(getHasName(material), has(material))
                .save(recipeOutput, getItemName(result) + "_from_" + cookingMethod);
    }

    public static Block getBaseBlock(BlockFamily family, BlockFamily.Variant variant) {
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

    // endregion

    // region CriterionInstances

    public static EnterBlockTrigger.TriggerInstance insideOf(Block block) {
        return new EnterBlockTrigger.TriggerInstance(Optional.empty(), Optional.of(block.builtInRegistryHolder()), Optional.empty());
    }

    public static @NotNull Criterion<InventoryChangeTrigger.TriggerInstance> has(MinMaxBounds.Ints count, ItemLike item) {
        return inventoryTrigger(ItemPredicate.Builder.item().of(item).withCount(count));
    }

    public static @NotNull Criterion<InventoryChangeTrigger.TriggerInstance> has(ItemLike itemLike) {
        return inventoryTrigger(ItemPredicate.Builder.item().of(itemLike));
    }

    public static @NotNull Criterion<InventoryChangeTrigger.TriggerInstance> has(ItemLike... items) {
        return inventoryTrigger(ItemPredicate.Builder.item().of(items));
    }

    public static @NotNull Criterion<InventoryChangeTrigger.TriggerInstance> has(TagKey<Item> tag) {
        return inventoryTrigger(ItemPredicate.Builder.item().of(tag));
    }

    public static @NotNull Criterion<InventoryChangeTrigger.TriggerInstance> inventoryTrigger(ItemPredicate.Builder... items) {
        return inventoryTrigger(Arrays.stream(items).map(ItemPredicate.Builder::build).toArray(ItemPredicate[]::new));
    }

    public static @NotNull Criterion<InventoryChangeTrigger.TriggerInstance> inventoryTrigger(ItemPredicate... predicates) {
        return CriteriaTriggers.INVENTORY_CHANGED.createCriterion(new InventoryChangeTrigger.TriggerInstance(Optional.empty(), InventoryChangeTrigger.TriggerInstance.Slots.ANY, List.of(predicates)));
    }

    // endregion
}
