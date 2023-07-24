package games.moegirl.sinocraft.sinodivination.plugin.jei;

import games.moegirl.sinocraft.sinocore.crafting.abstracted.RecipeHolder;
import games.moegirl.sinocraft.sinodivination.SinoDivination;
import games.moegirl.sinocraft.sinodivination.block.SDBlocks;
import games.moegirl.sinocraft.sinodivination.item.SDItems;
import games.moegirl.sinocraft.sinodivination.plugin.jei.category.CarvingTableRecipeCategory;
import games.moegirl.sinocraft.sinodivination.plugin.jei.category.ChangeSoupRecipeCategory;
import games.moegirl.sinocraft.sinodivination.plugin.jei.category.KettlePotRecipeCategory;
import games.moegirl.sinocraft.sinodivination.recipe.CarvingTableRecipe;
import games.moegirl.sinocraft.sinodivination.recipe.ChangeSoupRecipe;
import games.moegirl.sinocraft.sinodivination.recipe.KettlePotRecipe;
import games.moegirl.sinocraft.sinodivination.recipe.SDRecipes;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.recipe.RecipeType;
import mezz.jei.api.registration.IModIngredientRegistration;
import mezz.jei.api.registration.IRecipeCatalystRegistration;
import mezz.jei.api.registration.IRecipeCategoryRegistration;
import mezz.jei.api.registration.IRecipeRegistration;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.Container;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.level.Level;

import java.util.Collections;
import java.util.List;

@JeiPlugin
public class JEIPlugin implements IModPlugin {

    private static final ResourceLocation ID = new ResourceLocation(SinoDivination.MODID, "jei");

    public static final RecipeType<ChangeSoupRecipe> CHANGE_SOUP = newRecipeType(SDRecipes.CHANGE_SOUP);
    public static final RecipeType<CarvingTableRecipe> CARVING_TABLE = newRecipeType(SDRecipes.CARVING_TABLE);
    public static final RecipeType<KettlePotRecipe> KETTLE_POT = newRecipeType(SDRecipes.KETTLE_POT);

    @Override
    public void registerIngredients(IModIngredientRegistration registration) {
    }

    @Override
    public ResourceLocation getPluginUid() {
        return ID;
    }

    @Override
    public void registerCategories(IRecipeCategoryRegistration registration) {
        registration.addRecipeCategories(new ChangeSoupRecipeCategory(registration));
        registration.addRecipeCategories(new CarvingTableRecipeCategory(registration));
        registration.addRecipeCategories(new KettlePotRecipeCategory(registration));
    }

    @Override
    public void registerRecipes(IRecipeRegistration registration) {
        Level level = net.minecraft.client.Minecraft.getInstance().level;
        registration.addRecipes(CHANGE_SOUP, getRecipes(level, SDRecipes.CHANGE_SOUP));
        registration.addRecipes(CARVING_TABLE, getRecipes(level, SDRecipes.CARVING_TABLE));
        registration.addRecipes(KETTLE_POT, getRecipes(level, SDRecipes.KETTLE_POT));
    }

    @Override
    public void registerRecipeCatalysts(IRecipeCatalystRegistration registration) {
        registration.addRecipeCatalyst(new ItemStack(SDItems.CHANGE_SOUP.get()), CHANGE_SOUP);
        registration.addRecipeCatalyst(new ItemStack(SDBlocks.CARVING_TABLE.get()), CARVING_TABLE);
        registration.addRecipeCatalyst(new ItemStack(SDBlocks.KETTLE_POT.get()), KETTLE_POT);
    }

    private static <C extends Container, T extends Recipe<C>> RecipeType<T> newRecipeType(RecipeHolder<?, T, ?> type) {
        return RecipeType.create(type.name().getNamespace(), type.name().getPath(), type.type());
    }

    private static <C extends Container, T extends Recipe<C>> List<T> getRecipes(Level level, RecipeHolder<?, T, ?> mcType) {
        return level == null ? Collections.emptyList() : List.copyOf(level.getRecipeManager().getAllRecipesFor(mcType.recipeType()));
    }
}
