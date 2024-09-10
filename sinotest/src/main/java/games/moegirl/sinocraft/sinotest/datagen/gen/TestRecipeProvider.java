package games.moegirl.sinocraft.sinotest.datagen.gen;

import games.moegirl.sinocraft.sinocore.data.gen.AbstractRecipeProvider;
import games.moegirl.sinocraft.sinocore.data.gen.IDataGenContext;
import games.moegirl.sinocraft.sinotest.registry.TestRegistry;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeOutput;

public class TestRecipeProvider extends AbstractRecipeProvider {

    public TestRecipeProvider(IDataGenContext context) {
        super(context);
    }

    @Override
    protected void buildRecipes(RecipeOutput recipeOutput) {
        threeByThreePacker(recipeOutput, RecipeCategory.MISC,
                TestRegistry.TEST_ITEM_MOD_MC_TAB.get(),
                TestRegistry.TEST_ITEM_MC_TAB.get());
    }
}
