package games.moegirl.sinocraft.sinodivination.old.plugin.jei.category;

import games.moegirl.sinocraft.sinocore.utility.texture.SlotEntry;
import games.moegirl.sinocraft.sinocore.utility.texture.SlotsEntry;
import games.moegirl.sinocraft.sinocore.utility.texture.TextureEntry;
import games.moegirl.sinocraft.sinodivination.recipe.CarvingTableRecipe;

import static games.moegirl.sinocraft.sinodivination.menu.CarvingTableMenu.TEXTURE;

// TODO jei support
public class CarvingTableRecipeCategory extends AbstractRecipeCategory<CarvingTableRecipe> {

    private static final TextureEntry JEI = TEXTURE.textures().ensureGet("jei");
    private static final SlotsEntry INPUTS = TEXTURE.slots().ensureGet("input");
    private static final SlotEntry DYE = TEXTURE.slot().ensureGet("dye");
    private static final SlotEntry OUTPUT = TEXTURE.slot().ensureGet("output");

//    public CarvingTableRecipeCategory(IRecipeCategoryRegistration registration) {
//        super(registration, JEIPlugin.CARVING_TABLE, SDRecipes.CARVING_TABLE, SDLangKeys.JEI_RECIPE_CARVING_TABLE);
//    }
//
//    @Override
//    protected IDrawable buildBackground(IGuiHelper guiHelper) {
//        return guiHelper.createDrawable(TEXTURE.texture(), (int) JEI.u(), (int) JEI.v(), JEI.w(), JEI.h());
//    }
//
//    @Override
//    public void setRecipe(IRecipeLayoutBuilder builder, CarvingTableRecipe recipe, IFocusGroup focuses) {
//        for (int row = 0; row < 4; row++) {
//            for (int column = 0; column < 4; column++) {
//                Ingredient input = recipe.getInput(row, column);
//                if (input.isEmpty()) continue;
//                int idx = row * 4 + column;
//                SlotEntry entry = INPUTS.entries().get(idx);
//                builder.addSlot(RecipeIngredientRole.INPUT, entry.x() - JEI.x(), entry.y() - JEI.y()).addIngredients(input);
//            }
//        }
//        if (!recipe.getDye().isEmpty()) {
//            builder.addSlot(RecipeIngredientRole.INPUT, DYE.x() - JEI.x(), DYE.y() - JEI.y()).addIngredients(recipe.getDye());
//        }
//        builder.addSlot(RecipeIngredientRole.OUTPUT, CarvingTableRecipeCategory.OUTPUT.x() - JEI.x(), CarvingTableRecipeCategory.OUTPUT.y() - JEI.y())
//                .addIngredient(VanillaTypes.ITEM_STACK, recipe.getResultItem());
//    }
}
