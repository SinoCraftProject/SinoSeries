package games.moegirl.sinocraft.sinofoundation.data;

import games.moegirl.sinocraft.sinocore.data.abstracted.AbstractRecipeProvider;
import games.moegirl.sinocraft.sinofoundation.block.SFDBlockItems;
import games.moegirl.sinocraft.sinofoundation.item.SFDItems;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.data.recipes.ShapelessRecipeBuilder;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ItemLike;

import java.util.function.Consumer;

public class SFDRecipeProvider extends AbstractRecipeProvider {
    public SFDRecipeProvider(PackOutput output, String modid) {
        super(output, modid);
    }

    @Override
    protected void buildRecipes(Consumer<FinishedRecipe> writer) {
        ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, SFDItems.IRON_KNIFE.get())
                .pattern("I ")
                .pattern(" S")
                .define('I', Items.IRON_INGOT)
                .define('S', Items.STICK)
                .showNotification(false)
                .unlockedBy("got_iron", has(Items.IRON_INGOT))
                .save(writer, modLoc("iron_knife"));


        ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, SFDItems.GOLD_KNIFE.get())
                .pattern("I ")
                .pattern(" S")
                .define('I', Items.GOLD_INGOT)
                .define('S', Items.STICK)
                .showNotification(false)
                .unlockedBy("got_gold", has(Items.GOLD_INGOT))
                .save(writer, modLoc("gold_knife"));


        ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, SFDItems.DIAMOND_KNIFE.get())
                .pattern("I ")
                .pattern(" S")
                .define('I', Items.DIAMOND)
                .define('S', Items.STICK)
                .showNotification(false)
                .unlockedBy("got_diamond", has(Items.DIAMOND))
                .save(writer, modLoc("diamond_knife"));

        ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, SFDItems.IRON_KNIFE.get())
                .pattern(" I")
                .pattern("S ")
                .define('I', Items.IRON_INGOT)
                .define('S', Items.STICK)
                .showNotification(false)
                .unlockedBy("got_iron", has(Items.IRON_INGOT))
                .save(writer, modLoc("iron_knife_reverse"));


        ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, SFDItems.GOLD_KNIFE.get())
                .pattern(" I")
                .pattern("S ")
                .define('I', Items.GOLD_INGOT)
                .define('S', Items.STICK)
                .showNotification(false)
                .unlockedBy("got_gold", has(Items.GOLD_INGOT))
                .save(writer, modLoc("gold_knife_reverse"));


        ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, SFDItems.DIAMOND_KNIFE.get())
                .pattern(" I")
                .pattern("S ")
                .define('I', Items.DIAMOND)
                .define('S', Items.STICK)
                .showNotification(false)
                .unlockedBy("got_diamond", has(Items.DIAMOND))
                .save(writer, modLoc("diamond_knife_reverse"));


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
    }

    protected void fruitsToSeed(String name, ItemLike fruit, ItemLike seed, int count, Consumer<FinishedRecipe> writer) {
        ShapelessRecipeBuilder.shapeless(RecipeCategory.FOOD, seed, count)
                .requires(fruit)
                .unlockedBy("got_fruit", has(fruit))
                .save(writer, modLoc(name));
    }
}
