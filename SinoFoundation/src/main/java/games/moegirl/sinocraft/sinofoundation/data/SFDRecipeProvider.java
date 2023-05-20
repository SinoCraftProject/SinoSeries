package games.moegirl.sinocraft.sinofoundation.data;

import games.moegirl.sinocraft.sinocore.data.abstracted.AbstractRecipeProvider;
import games.moegirl.sinocraft.sinofoundation.item.SFDItems;
import net.minecraft.advancements.critereon.InventoryChangeTrigger;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.world.item.Items;

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
                .unlockedBy("got_iron", InventoryChangeTrigger.TriggerInstance.hasItems(Items.IRON_INGOT))
                .save(writer);


        ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, SFDItems.GOLD_KNIFE.get())
                .pattern("I ")
                .pattern(" S")
                .define('I', Items.GOLD_INGOT)
                .define('S', Items.STICK)
                .showNotification(false)
                .unlockedBy("got_gold", InventoryChangeTrigger.TriggerInstance.hasItems(Items.GOLD_INGOT))
                .save(writer);


        ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, SFDItems.DIAMOND_KNIFE.get())
                .pattern("I ")
                .pattern(" S")
                .define('I', Items.DIAMOND)
                .define('S', Items.STICK)
                .showNotification(false)
                .unlockedBy("got_diamond", InventoryChangeTrigger.TriggerInstance.hasItems(Items.DIAMOND))
                .save(writer);

        ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, SFDItems.IRON_KNIFE.get())
                .pattern(" I")
                .pattern("S ")
                .define('I', Items.IRON_INGOT)
                .define('S', Items.STICK)
                .showNotification(false)
                .unlockedBy("got_iron", InventoryChangeTrigger.TriggerInstance.hasItems(Items.IRON_INGOT))
                .save(writer);


        ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, SFDItems.GOLD_KNIFE.get())
                .pattern(" I")
                .pattern("S ")
                .define('I', Items.GOLD_INGOT)
                .define('S', Items.STICK)
                .showNotification(false)
                .unlockedBy("got_gold", InventoryChangeTrigger.TriggerInstance.hasItems(Items.GOLD_INGOT))
                .save(writer);


        ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, SFDItems.DIAMOND_KNIFE.get())
                .pattern(" I")
                .pattern("S ")
                .define('I', Items.DIAMOND)
                .define('S', Items.STICK)
                .showNotification(false)
                .unlockedBy("got_diamond", InventoryChangeTrigger.TriggerInstance.hasItems(Items.DIAMOND))
                .save(writer);
    }
}
