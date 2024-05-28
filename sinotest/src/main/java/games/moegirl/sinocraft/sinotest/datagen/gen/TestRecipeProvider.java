package games.moegirl.sinocraft.sinotest.datagen.gen;

import games.moegirl.sinocraft.sinocore.datagen.AbstractRecipeProvider;
import games.moegirl.sinocraft.sinocore.datagen.IDataGenContext;
import games.moegirl.sinocraft.sinotest.registry.TestRegistry;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.RecipeCategory;

import java.util.function.Consumer;

public class TestRecipeProvider extends AbstractRecipeProvider {

    public TestRecipeProvider(IDataGenContext context) {
        super(context);
    }

    @Override
    protected void buildRecipes(Consumer<FinishedRecipe> recipeOutput) {
        threeByThreePacker(recipeOutput, RecipeCategory.MISC,
                TestRegistry.TEST_ITEM_MOD_MC_TAB.get(),
                TestRegistry.TEST_ITEM_MC_TAB.get());
    }
}
