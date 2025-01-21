package games.moegirl.sinocraft.sinocore.data.gen.recipe;

import net.minecraft.data.recipes.*;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ItemLike;

import static games.moegirl.sinocraft.sinocore.data.gen.recipe.AbstractRecipeProvider.has;

public class CommonRecipes {

    public static RecipeBuilder stairsBuilder(ItemLike stairs, TagKey<Item> block) {
        return stairsBuilder(stairs, Ingredient.of(block))
                .unlockedBy("m", has(block));
    }

    public static RecipeBuilder stairsBuilder(ItemLike stairs, ItemLike block) {
        return stairsBuilder(stairs, Ingredient.of(block))
                .unlockedBy("m", has(block));
    }

    public static RecipeBuilder stairsBuilder(ItemLike stairs, Ingredient block) {
        return ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, stairs, 4)
                .define('#', block)
                .pattern("#  ")
                .pattern("## ")
                .pattern("###");
    }

    public static RecipeBuilder slabBuilder(ItemLike slab, TagKey<Item> block) {
        return slabBuilder(slab, Ingredient.of(block))
                .unlockedBy("m", has(block));
    }

    public static RecipeBuilder slabBuilder(ItemLike slab, ItemLike block) {
        return slabBuilder(slab, Ingredient.of(block))
                .unlockedBy("m", has(block));
    }

    public static RecipeBuilder slabBuilder(ItemLike slab, Ingredient block) {
        return ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, slab, 6)
                .define('#', block)
                .pattern("###");
    }

    public static RecipeBuilder buttonBuilder(ItemLike button, ItemLike material) {
        return ShapelessRecipeBuilder.shapeless(RecipeCategory.REDSTONE, button)
                .requires(material)
                .unlockedBy("material", has(material));
    }

    public static RecipeBuilder fenceBuilder(ItemLike fence, ItemLike planks) {
        return ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, fence, 3)
                .define('W', planks)
                .define('#', Items.STICK)
                .pattern("W#W")
                .pattern("W#W")
                .unlockedBy("w", has(planks))
                .unlockedBy("s", has(Items.STICK));
    }

    public static RecipeBuilder brickFenceBuilder(ItemLike fence, ItemLike block, ItemLike brick) {
        return ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, fence, 6)
                .define('W', block)
                .define('#', brick)
                .pattern("W#W")
                .pattern("W#W")
                .unlockedBy("w", has(block))
                .unlockedBy("s", has(brick));
    }

    public static RecipeBuilder fenceGateBuilder(ItemLike fenceGate, ItemLike planks) {
        return ShapedRecipeBuilder.shaped(RecipeCategory.REDSTONE, fenceGate)
                .define('#', Items.STICK)
                .define('W', planks)
                .pattern("#W#")
                .pattern("#W#")
                .unlockedBy("w", has(planks))
                .unlockedBy("s", has(Items.STICK));
    }

    public static RecipeBuilder pressurePlateBuilder(ItemLike pressurePlate, ItemLike planks) {
        return ShapedRecipeBuilder.shaped(RecipeCategory.REDSTONE, pressurePlate)
                .define('#', planks)
                .pattern("##")
                .unlockedBy("m", has(planks));
    }

    public static RecipeBuilder doorBuilder(ItemLike trapdoor, ItemLike planks) {
        return ShapedRecipeBuilder.shaped(RecipeCategory.REDSTONE, trapdoor, 2)
                .define('#', planks)
                .pattern("##")
                .pattern("##")
                .pattern("##")
                .unlockedBy("m", has(planks));
    }

    public static RecipeBuilder trapdoorBuilder(ItemLike trapdoor, ItemLike planks) {
        return ShapedRecipeBuilder.shaped(RecipeCategory.REDSTONE, trapdoor, 2)
                .define('#', planks)
                .pattern("###")
                .pattern("###")
                .unlockedBy("m", has(planks));
    }

    public static RecipeBuilder signBuilder(ItemLike sign, ItemLike planks) {
        return ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, sign, 3)
                .group("sign")
                .define('#', planks)
                .define('X', Items.STICK)
                .pattern("###")
                .pattern("###")
                .pattern(" X ")
                .unlockedBy("m", has(planks))
                .unlockedBy("s", has(Items.STICK));
    }

    public static RecipeBuilder hangingSignBuilder(ItemLike sign, ItemLike material) {
        return ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, sign, 6)
                .group("hanging_sign")
                .define('#', material)
                .define('X', Items.CHAIN)
                .pattern("X X")
                .pattern("###")
                .pattern("###")
                .unlockedBy("m", has(material));
    }

    public static RecipeBuilder wallBuilder(ItemLike wall, ItemLike block) {
        return ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, wall, 6)
                .define('#', block)
                .pattern("###")
                .pattern("###")
                .unlockedBy("m", has(block));
    }

    public static RecipeBuilder bricksBuilder(ItemLike result, ItemLike material) {
        return ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, result, 4)
                .define('S', material)
                .pattern("SS")
                .pattern("SS")
                .unlockedBy("m", has(material));
    }

    public static RecipeBuilder polishedBuilder(ItemLike result, ItemLike material) {
        return ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, result, 4)
                .define('S', material)
                .pattern("SS")
                .pattern("SS")
                .unlockedBy("m", has(material));
    }

    public static RecipeBuilder chiseledBuilder(ItemLike chiseledResult, ItemLike slab) {
        return ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, chiseledResult)
                .define('#', slab)
                .pattern("#")
                .pattern("#")
                .unlockedBy("m", has(slab));
    }

    public static RecipeBuilder pillarBuilder(ItemLike chiseledResult, ItemLike block) {
        return ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, chiseledResult)
                .define('#', block)
                .pattern("#")
                .pattern("#")
                .unlockedBy("m", has(block));
    }

    public static RecipeBuilder logsToWoodBuilder(ItemLike result, ItemLike log) {
        return ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, result, 3)
                .define('#', log)
                .pattern("##")
                .pattern("##")
                .unlockedBy("m", has(log));
    }

    public static RecipeBuilder logsToPlanksBuilder(ItemLike result, ItemLike log, int count) {
        return ShapelessRecipeBuilder.shapeless(RecipeCategory.BUILDING_BLOCKS, result, count)
                .requires(log)
                .unlockedBy("m", has(log));
    }

    public static RecipeBuilder logsToPlanksBuilder(ItemLike result, TagKey<Item> log, int count) {
        return ShapelessRecipeBuilder.shapeless(RecipeCategory.BUILDING_BLOCKS, result, count)
                .requires(log)
                .unlockedBy("m", has(log));
    }
}
