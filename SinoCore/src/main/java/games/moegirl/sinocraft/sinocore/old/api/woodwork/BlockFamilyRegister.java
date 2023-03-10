package games.moegirl.sinocraft.sinocore.old.api.woodwork;

import com.google.common.collect.ImmutableMap;
import net.minecraft.advancements.critereon.EntityPredicate;
import net.minecraft.advancements.critereon.InventoryChangeTrigger;
import net.minecraft.advancements.critereon.ItemPredicate;
import net.minecraft.advancements.critereon.MinMaxBounds;
import net.minecraft.core.Registry;
import net.minecraft.data.BlockFamily;
import net.minecraft.data.recipes.*;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;

import java.util.Map;
import java.util.function.BiFunction;
import java.util.function.Consumer;

import static net.minecraft.data.recipes.RecipeProvider.*;

public interface BlockFamilyRegister {

    Map<BlockFamily.Variant, BiFunction<ItemLike, ItemLike, RecipeBuilder>> shapeBuilders = ImmutableMap.<BlockFamily.Variant, BiFunction<ItemLike, ItemLike, RecipeBuilder>>builder()
            .put(BlockFamily.Variant.BUTTON, (out, in) -> buttonBuilder(out, Ingredient.of(in)))
            .put(BlockFamily.Variant.CHISELED, (out, in) -> chiseledBuilder(out, Ingredient.of(in)))
            .put(BlockFamily.Variant.CUT, (out, in) -> cutBuilder(out, Ingredient.of(in)))
            .put(BlockFamily.Variant.DOOR, (out, in) -> doorBuilder(out, Ingredient.of(in)))
            .put(BlockFamily.Variant.FENCE, (out, in) -> fenceBuilder(out, Ingredient.of(in)))
            .put(BlockFamily.Variant.FENCE_GATE, (out, in) -> fenceGateBuilder(out, Ingredient.of(in)))
            .put(BlockFamily.Variant.SIGN, (out, in) -> signBuilder(out, Ingredient.of(in)))
            .put(BlockFamily.Variant.SLAB, (out, in) -> slabBuilder(out, Ingredient.of(in)))
            .put(BlockFamily.Variant.STAIRS, (out, in) -> stairBuilder(out, Ingredient.of(in)))
            .put(BlockFamily.Variant.PRESSURE_PLATE, (out, in) -> pressurePlateBuilder(out, Ingredient.of(in)))
            .put(BlockFamily.Variant.POLISHED, (out, in) -> polishedBuilder(out, Ingredient.of(in)))
            .put(BlockFamily.Variant.TRAPDOOR, (out, in) -> trapdoorBuilder(out, Ingredient.of(in)))
            .put(BlockFamily.Variant.WALL, (out, in) -> wallBuilder(out, Ingredient.of(in)))
            .build();

    static void generateRecipes(Consumer<FinishedRecipe> consumer, BlockFamily family) {
        family.getVariants().forEach((variant, block) -> {
            ItemLike item = getBaseBlock(family, variant);
            BiFunction<ItemLike, ItemLike, RecipeBuilder> function = shapeBuilders.get(variant);
            if (function != null) {
                RecipeBuilder recipebuilder = function.apply(block, item);
                family.getRecipeGroupPrefix().ifPresent(pre ->
                        recipebuilder.group(pre + (variant == BlockFamily.Variant.CUT ? "" : "_" + variant.getName())));
                recipebuilder.unlockedBy(family.getRecipeUnlockedBy().orElseGet(() -> getHasName(item)), has(item));
                recipebuilder.save(consumer);
            }

            if (variant == BlockFamily.Variant.CRACKED) {
                smeltingResultFromBase(consumer, block, item);
            }
        });
    }

    private static Block getBaseBlock(BlockFamily pFamily, BlockFamily.Variant pVariant) {
        if (pVariant == BlockFamily.Variant.CHISELED) {
            if (!pFamily.getVariants().containsKey(BlockFamily.Variant.SLAB)) {
                throw new IllegalStateException("Slab is not defined for the family.");
            } else {
                return pFamily.get(BlockFamily.Variant.SLAB);
            }
        } else {
            return pFamily.getBaseBlock();
        }
    }

    private static String getHasName(ItemLike pItemLike) {
        return "has_" + Registry.ITEM.getKey(pItemLike.asItem()).getPath();
    }

    private static InventoryChangeTrigger.TriggerInstance has(ItemLike pItemLike) {
        return new InventoryChangeTrigger.TriggerInstance(EntityPredicate.Composite.ANY, MinMaxBounds.Ints.ANY, MinMaxBounds.Ints.ANY, MinMaxBounds.Ints.ANY, new ItemPredicate[]{ItemPredicate.Builder.item().of(pItemLike).build()});
    }

    private static void smeltingResultFromBase(Consumer<FinishedRecipe> pFinishedRecipeConsumer, ItemLike pResult, ItemLike pIngredient) {
        SimpleCookingRecipeBuilder.smelting(Ingredient.of(pIngredient), pResult, 0.1F, 200).unlockedBy(getHasName(pIngredient), has(pIngredient)).save(pFinishedRecipeConsumer);
    }

    private static RecipeBuilder buttonBuilder(ItemLike pButton, Ingredient pMaterial) {
        return ShapelessRecipeBuilder.shapeless(pButton).requires(pMaterial);
    }

    private static RecipeBuilder doorBuilder(ItemLike pDoor, Ingredient pMaterial) {
        return ShapedRecipeBuilder.shaped(pDoor, 3).define('#', pMaterial).pattern("##").pattern("##").pattern("##");
    }

    private static RecipeBuilder fenceBuilder(ItemLike pFence, Ingredient pMaterial) {
        return ShapedRecipeBuilder.shaped(pFence, 3).define('W', pMaterial).define('#', Items.STICK).pattern("W#W").pattern("W#W");
    }

    private static RecipeBuilder fenceGateBuilder(ItemLike pFenceGate, Ingredient pMaterial) {
        return ShapedRecipeBuilder.shaped(pFenceGate).define('#', Items.STICK).define('W', pMaterial).pattern("#W#").pattern("#W#");
    }

    private static RecipeBuilder signBuilder(ItemLike pSign, Ingredient pMaterial) {
        return ShapedRecipeBuilder.shaped(pSign, 3).group("sign").define('#', pMaterial).define('X', Items.STICK).pattern("###").pattern("###").pattern(" X ");
    }

    private static RecipeBuilder slabBuilder(ItemLike pSlab, Ingredient pMaterial) {
        return ShapedRecipeBuilder.shaped(pSlab, 6).define('#', pMaterial).pattern("###");
    }

    private static RecipeBuilder stairBuilder(ItemLike pStairs, Ingredient pMaterial) {
        return ShapedRecipeBuilder.shaped(pStairs, 4).define('#', pMaterial).pattern("#  ").pattern("## ").pattern("###");
    }

    private static RecipeBuilder pressurePlateBuilder(ItemLike pPressurePlate, Ingredient pMaterial) {
        return ShapedRecipeBuilder.shaped(pPressurePlate).define('#', pMaterial).pattern("##");
    }

    private static RecipeBuilder trapdoorBuilder(ItemLike pTrapdoor, Ingredient pMaterial) {
        return ShapedRecipeBuilder.shaped(pTrapdoor, 2).define('#', pMaterial).pattern("###").pattern("###");
    }

}
