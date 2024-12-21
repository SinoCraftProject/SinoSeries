package games.moegirl.sinocraft.sinobrush.data.gen.recipe;

import games.moegirl.sinocraft.sinobrush.item.SBRItems;
import games.moegirl.sinocraft.sinocore.data.gen.recipe.AbstractRecipeProvider;
import games.moegirl.sinocraft.sinocore.data.gen.IDataGenContext;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.Items;

public class RecipeProvider extends AbstractRecipeProvider {
    public static final String GROUP = "sinoseries:sinobrush";

    public RecipeProvider(IDataGenContext context) {
        super(context);
    }

    @Override
    protected void buildRecipes(RecipeOutput output) {
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, SBRItems.INK_BOTTLE.get(), 6)
                .pattern("BCB")
                .pattern("BWB")
                .pattern("BCB")
                .define('B', Items.GLASS_BOTTLE)
                .define('C', ItemTags.COALS)
                .define('W', Items.WATER_BUCKET)
                .unlockedBy("got_water", has(Items.WATER_BUCKET))
                .group(GROUP)
                .showNotification(true)
                .save(output);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, SBRItems.XUAN_PAPER.get())
                .pattern("PP")
                .pattern("PP")
                .define('P', Items.PAPER)
                .unlockedBy("got_paper", has(Items.PAPER))
                .group(GROUP)
                .showNotification(true)
                .save(output);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, SBRItems.BRUSH.get())
                .pattern(" S")
                .pattern("W ")
                .define('S', Items.STICK)
                .define('W', Items.WHITE_WOOL)
                .unlockedBy("got_white_wool", has(Items.WHITE_WOOL))
                .group(GROUP)
                .showNotification(true)
                .save(output);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, SBRItems.FOLDED_FAN.get())
                .pattern("SPP")
                .pattern("SPP")
                .pattern(" SS")
                .define('S', Items.STICK)
                .define('P', Items.PAPER)
                .unlockedBy("got_stick", has(Items.STICK))
                .group(GROUP)
                .showNotification(true)
                .save(output);
    }
}
