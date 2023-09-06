package games.moegirl.sinocraft.sinofoundation.data.gen;

import games.moegirl.sinocraft.sinocore.crafting.block_ingredient.BlockIngredients;
import games.moegirl.sinocraft.sinocore.data.gen.AbstractRecipeProvider;
import games.moegirl.sinocraft.sinocore.tree.TreeBlockType;
import games.moegirl.sinocraft.sinofoundation.SFDTrees;
import games.moegirl.sinocraft.sinofoundation.block.SFDBlockItems;
import games.moegirl.sinocraft.sinofoundation.block.SFDBlocks;
import games.moegirl.sinocraft.sinofoundation.crafting.knife.KnifeRecipe;
import games.moegirl.sinocraft.sinofoundation.data.gen.tag.SFDItemTags;
import games.moegirl.sinocraft.sinofoundation.item.SFDItems;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.data.recipes.ShapelessRecipeBuilder;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ItemLike;

import java.util.function.Consumer;
import java.util.function.Supplier;

public class SFDRecipeProvider extends AbstractRecipeProvider {
    public SFDRecipeProvider(PackOutput output, String modid) {
        super(output, modid);
    }

    @Override
    protected void buildRecipes(Consumer<FinishedRecipe> writer) {
        addKnife("iron", Items.IRON_INGOT, SFDItems.IRON_KNIFE, writer);
        addKnife("gold", Items.GOLD_INGOT, SFDItems.GOLD_KNIFE, writer);
        addKnife("diamond", Items.DIAMOND, SFDItems.DIAMOND_KNIFE, writer);

        fruitsToSeed("chili_pepper", SFDItems.CHILI_PEPPER.get(), SFDBlockItems.CHILI_PEPPER_SEED.get(), 2, writer);
        fruitsToSeed("green_pepper", SFDItems.GREEN_PEPPER.get(), SFDBlockItems.GREEN_PEPPER_SEED.get(), 2, writer);
        fruitsToSeed("eggplant", SFDItems.EGGPLANT.get(), SFDBlockItems.EGGPLANT_SEED.get(), 4, writer);
        fruitsToSeed("cabbage", SFDItems.CABBAGE.get(), SFDBlockItems.CABBAGE_SEED.get(), 2, writer);


        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, Items.GUNPOWDER, 2)
                .requires(Ingredient.of(SFDItems.SULPHUR.get()))
                .requires(Ingredient.of(SFDItems.NITER.get()))
                .requires(Ingredient.of(ItemTags.COALS))
                .unlockedBy("got_niter", has(SFDItems.NITER.get()))
                .save(writer);

        chest(SFDTrees.JUJUBE, writer, SFDBlocks.JUJUBE_CHEST, SFDBlocks.JUJUBE_TRAPPED_CHEST);

        KnifeRecipe.builder(SFDTrees.JUJUBE.getBlock(TreeBlockType.STRIPPED_LOG))
                .setBlock(BlockIngredients.block(SFDTrees.JUJUBE.getBlock(TreeBlockType.LOG)))
                .setOutput(new ItemStack(SFDItems.TREE_BARK.get()))
                .save(writer);
        KnifeRecipe.builder(SFDTrees.JUJUBE.getBlock(TreeBlockType.STRIPPED_LOG_WOOD))
                .setBlock(BlockIngredients.block(SFDTrees.JUJUBE.getBlock(TreeBlockType.LOG_WOOD)))
                .setOutput(new ItemStack(SFDItems.TREE_BARK.get()))
                .save(writer);
        KnifeRecipe.builder(SFDTrees.MULBERRY.getBlock(TreeBlockType.STRIPPED_LOG))
                .setBlock(BlockIngredients.block(SFDTrees.MULBERRY.getBlock(TreeBlockType.LOG)))
                .setOutput(new ItemStack(SFDItems.TREE_BARK.get()))
                .save(writer);
        KnifeRecipe.builder(SFDTrees.MULBERRY.getBlock(TreeBlockType.STRIPPED_LOG_WOOD))
                .setBlock(BlockIngredients.block(SFDTrees.MULBERRY.getBlock(TreeBlockType.LOG_WOOD)))
                .setOutput(new ItemStack(SFDItems.TREE_BARK.get()))
                .save(writer);
    }

    protected void fruitsToSeed(String name, ItemLike fruit, ItemLike seed, int count, Consumer<FinishedRecipe> writer) {
        ShapelessRecipeBuilder.shapeless(RecipeCategory.FOOD, seed, count)
                .requires(fruit)
                .unlockedBy("got_fruit", has(fruit))
                .save(writer, modLoc(name));
    }

    protected void addKnife(String level, Item ingot, Supplier<? extends Item> result, Consumer<FinishedRecipe> writer) {
        ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, result.get())
                .pattern("I ")
                .pattern(" S")
                .define('I', ingot)
                .define('S', SFDItems.WOODEN_HANDLE.get())
                .showNotification(false)
                .unlockedBy("got_" + level, has(ingot))
                .save(writer, modLoc(level + "_knife"));

        ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, result.get())
                .pattern(" I")
                .pattern("S ")
                .define('I', ingot)
                .define('S', SFDItems.WOODEN_HANDLE.get())
                .showNotification(false)
                .unlockedBy("got_" + level, has(ingot))
                .save(writer, modLoc(level + "_knife_reverse"));
    }
}
