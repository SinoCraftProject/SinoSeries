package games.moegirl.sinocraft.sinocore.datagen;

import games.moegirl.sinocraft.sinocore.mixin_interfaces.interfaces.IRenamedProvider;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.advancements.Criterion;
import net.minecraft.advancements.critereon.EnterBlockTrigger;
import net.minecraft.advancements.critereon.InventoryChangeTrigger;
import net.minecraft.advancements.critereon.ItemPredicate;
import net.minecraft.advancements.critereon.MinMaxBounds;
import net.minecraft.data.BlockFamily;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.AbstractCookingRecipe;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public abstract class AbstractRecipeProvider extends RecipeProvider implements IRenamedProvider {

    private final String modId;

    public AbstractRecipeProvider(PackOutput output, String modId) {
        super(output);
        this.modId = modId;
    }

    public AbstractRecipeProvider(IDataGenContext context) {
        this(context.getOutput(), context.getModId());
    }

    @Override
    public String getNewName() {
        return "Recipes: " + modId;
    }

    // region Recipes

    public static void oreCooking(RecipeOutput recipeOutput,
                                  RecipeSerializer<? extends AbstractCookingRecipe> serializer,
                                  List<ItemLike> ingredients, RecipeCategory category, ItemLike result,
                                  float experience, int cookingTime, String group, String suffix) {
        for (ItemLike itemlike : ingredients) {
            SimpleCookingRecipeBuilder.generic(Ingredient.of(itemlike), category, result, experience, cookingTime, serializer)
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
                .save(recipeOutput, new ResourceLocation(unpackedName));
        ShapedRecipeBuilder.shaped(packedCategory, packed)
                .group(packedGroup).unlockedBy(getHasName(unpacked), has(unpacked))
                .define('#', unpacked)
                .pattern("###")
                .pattern("###")
                .pattern("###")
                .save(recipeOutput, new ResourceLocation(packedName));
    }

    public static void simpleCookingRecipe(RecipeOutput recipeOutput, String cookingMethod,
                                           RecipeSerializer<? extends AbstractCookingRecipe> cookingSerializer,
                                           int cookingTime, ItemLike material, ItemLike result, float experience) {
        SimpleCookingRecipeBuilder
                .generic(Ingredient.of(material), RecipeCategory.FOOD, result, experience, cookingTime, cookingSerializer)
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

    public static Criterion<EnterBlockTrigger.TriggerInstance> insideOf(Block block) {
        return CriteriaTriggers.ENTER_BLOCK.createCriterion(
                new EnterBlockTrigger.TriggerInstance(Optional.empty(), block, Optional.empty()));
    }

    public static Criterion<InventoryChangeTrigger.TriggerInstance> has(MinMaxBounds.Ints count, ItemLike item) {
        return inventoryTrigger(ItemPredicate.Builder.item().of(item).withCount(count));
    }

    public static Criterion<InventoryChangeTrigger.TriggerInstance> inventoryTrigger(ItemPredicate.Builder... items) {
        return inventoryTrigger(Arrays.stream(items).map(ItemPredicate.Builder::build).toArray(ItemPredicate[]::new));
    }

    public static Criterion<InventoryChangeTrigger.TriggerInstance> inventoryTrigger(ItemPredicate... predicates) {
        return CriteriaTriggers.INVENTORY_CHANGED.createCriterion(new InventoryChangeTrigger.TriggerInstance(
                Optional.empty(), MinMaxBounds.Ints.ANY, MinMaxBounds.Ints.ANY, MinMaxBounds.Ints.ANY, List.of(predicates)));
    }

    // endregion
}
