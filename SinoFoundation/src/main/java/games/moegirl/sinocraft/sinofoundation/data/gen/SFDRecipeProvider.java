package games.moegirl.sinocraft.sinofoundation.data.gen;

import games.moegirl.sinocraft.sinocore.block.ChestBlockBase;
import games.moegirl.sinocraft.sinocore.block.TrappedChestBlockBase;
import games.moegirl.sinocraft.sinocore.data.gen.recipe.AbstractRecipeProvider;
import games.moegirl.sinocraft.sinocore.tree.Tree;
import games.moegirl.sinocraft.sinocore.tree.TreeBlockType;
import games.moegirl.sinocraft.sinofoundation.SFDTrees;
import games.moegirl.sinocraft.sinofoundation.block.SFDBlockItems;
import games.moegirl.sinocraft.sinofoundation.block.SFDBlocks;
import games.moegirl.sinocraft.sinofoundation.item.SFDItems;
import net.minecraft.advancements.critereon.ContextAwarePredicate;
import net.minecraft.advancements.critereon.InventoryChangeTrigger;
import net.minecraft.advancements.critereon.ItemPredicate;
import net.minecraft.advancements.critereon.MinMaxBounds;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.data.recipes.ShapelessRecipeBuilder;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.registries.RegistryObject;

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

        chest(SFDTrees.SOPHORA, writer, SFDBlocks.SOPHORA_CHEST, SFDBlocks.SOPHORA_TRAPPED_CHEST);
        chest(SFDTrees.COTINUS, writer, SFDBlocks.COTINUS_CHEST, SFDBlocks.COTINUS_TRAPPED_CHEST);
        chest(SFDTrees.JUJUBE, writer, SFDBlocks.JUJUBE_CHEST, SFDBlocks.JUJUBE_TRAPPED_CHEST);
    }

    protected void fruitsToSeed(String name, ItemLike fruit, ItemLike seed, int count, Consumer<FinishedRecipe> writer) {
        ShapelessRecipeBuilder.shapeless(RecipeCategory.FOOD, seed, count)
                .requires(fruit)
                .unlockedBy("got_fruit", has(fruit))
                .save(writer, modLoc(name));
    }

    private void chest(Tree tree, Consumer<FinishedRecipe> writer,
                       RegistryObject<? extends ChestBlockBase> chest, RegistryObject<? extends TrappedChestBlockBase> trappedChest) {
        Item plank = tree.getItem(TreeBlockType.PLANKS);
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
}
