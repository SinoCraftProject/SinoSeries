package games.moegirl.sinocraft.sinodivination.plugin.jei.category;

import games.moegirl.sinocraft.sinodivination.data.SDLangKeys;
import games.moegirl.sinocraft.sinodivination.plugin.jei.JEIPlugin;
import games.moegirl.sinocraft.sinodivination.recipe.ChangeSoupRecipe;
import games.moegirl.sinocraft.sinodivination.recipe.SDRecipes;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeIngredientRole;
import mezz.jei.api.registration.IRecipeCategoryRegistration;
import net.minecraft.world.item.ItemStack;

public class ChangeSoupRecipeCategory extends AbstractRecipeCategory<ChangeSoupRecipe> {

    public ChangeSoupRecipeCategory(IRecipeCategoryRegistration registration) {
        super(registration, JEIPlugin.CHANGE_SOUP, SDRecipes.CHANGE_SOUP, SDLangKeys.JEI_RECIPE_CHANGE_SOUP);
    }

    @Override
    protected IDrawable buildBackground(IGuiHelper guiHelper) {
        return guiHelper.createDrawable(texture("jei", "recipe", "changing_seeding"), 0, 0, 120, 30);
    }

    @Override
    public void setRecipe(IRecipeLayoutBuilder builder, ChangeSoupRecipe recipe, IFocusGroup focuses) {
        builder.addSlot(RecipeIngredientRole.INPUT, 15, 6).addIngredient(VanillaTypes.ITEM_STACK, new ItemStack(recipe.inputItem));
        builder.addSlot(RecipeIngredientRole.OUTPUT, 87, 6).addIngredient(VanillaTypes.ITEM_STACK, new ItemStack(recipe.result));
    }
}
