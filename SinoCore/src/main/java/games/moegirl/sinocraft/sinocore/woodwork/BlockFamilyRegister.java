package games.moegirl.sinocraft.sinocore.woodwork;

import com.google.common.collect.ImmutableMap;
import net.minecraft.advancements.critereon.EntityPredicate;
import net.minecraft.advancements.critereon.InventoryChangeTrigger;
import net.minecraft.advancements.critereon.ItemPredicate;
import net.minecraft.advancements.critereon.MinMaxBounds;
import net.minecraft.data.BlockFamily;
import net.minecraft.data.recipes.*;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.Map;
import java.util.function.BiFunction;
import java.util.function.Consumer;

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
        return "has_" + ForgeRegistries.ITEMS.getKey(pItemLike.asItem()).getPath();
    }

    private static InventoryChangeTrigger.TriggerInstance has(ItemLike pItemLike) {
        return new InventoryChangeTrigger.TriggerInstance(EntityPredicate.Composite.ANY, MinMaxBounds.Ints.ANY, MinMaxBounds.Ints.ANY, MinMaxBounds.Ints.ANY, new ItemPredicate[]{ItemPredicate.Builder.item().of(pItemLike).build()});
    }

    private static void smeltingResultFromBase(Consumer<FinishedRecipe> finishedRecipeConsumer, ItemLike result, ItemLike ingredient) {
        SimpleCookingRecipeBuilder.smelting(Ingredient.of(ingredient), RecipeCategory.BUILDING_BLOCKS, result, 0.1f, 200)
                .unlockedBy(getHasName(ingredient), has(ingredient))
                .save(finishedRecipeConsumer);
    }

    private static RecipeBuilder buttonBuilder(ItemLike button, Ingredient material) {
        return ShapelessRecipeBuilder.shapeless(RecipeCategory.REDSTONE, button).requires(material);
    }

    private static ShapedRecipeBuilder chiseledBuilder(ItemLike chiseledResult, Ingredient material) {
        return ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, chiseledResult).define('#', material).pattern("#").pattern("#");
    }

    private static ShapedRecipeBuilder cutBuilder(ItemLike cutResult, Ingredient material) {
        return ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, cutResult, 4).define('#', material).pattern("##").pattern("##");
    }

    private static RecipeBuilder doorBuilder(ItemLike door, Ingredient material) {
        return ShapedRecipeBuilder.shaped(RecipeCategory.REDSTONE, door, 3).define('#', material).pattern("##").pattern("##").pattern("##");
    }

    private static RecipeBuilder fenceBuilder(ItemLike fence, Ingredient material) {
        int i = fence == Blocks.NETHER_BRICK_FENCE ? 6 : 3;
        Item item = fence == Blocks.NETHER_BRICK_FENCE ? Items.NETHER_BRICK : Items.STICK;
        return ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, fence, i).define('W', material).define('#', item).pattern("W#W").pattern("W#W");
    }

    private static RecipeBuilder fenceGateBuilder(ItemLike fenceGate, Ingredient material) {
        return ShapedRecipeBuilder.shaped(RecipeCategory.REDSTONE, fenceGate).define('#', Items.STICK).define('W', material).pattern("#W#").pattern("#W#");
    }

    private static RecipeBuilder signBuilder(ItemLike sign, Ingredient material) {
        return ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, sign, 3).group("sign").define('#', material).define('X', Items.STICK).pattern("###").pattern("###").pattern(" X ");
    }

    private static RecipeBuilder slabBuilder(ItemLike slab, Ingredient material) {
        return ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, slab, 6).define('#', material).pattern("###");
    }

    private static RecipeBuilder stairBuilder(ItemLike stairs, Ingredient material) {
        return ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, stairs, 4).define('#', material).pattern("#  ").pattern("## ").pattern("###");
    }

    private static RecipeBuilder pressurePlateBuilder(ItemLike pressurePlate, Ingredient material) {
        return ShapedRecipeBuilder.shaped(RecipeCategory.REDSTONE, pressurePlate).define('#', material).pattern("##");
    }

    private static RecipeBuilder polishedBuilder(ItemLike result, Ingredient material) {
        return ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, result, 4).define('S', material).pattern("SS").pattern("SS");
    }

    private static RecipeBuilder trapdoorBuilder(ItemLike trapdoor, Ingredient material) {
        return ShapedRecipeBuilder.shaped(RecipeCategory.REDSTONE, trapdoor, 2).define('#', material).pattern("###").pattern("###");
    }

    private static RecipeBuilder wallBuilder(ItemLike wall, Ingredient material) {
        return ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, wall, 6).define('#', material).pattern("###").pattern("###");
    }

}
