package games.moegirl.sinocraft.sinodeco.data.gen;

import games.moegirl.sinocraft.sinocore.data.gen.AbstractRecipeProvider;
import games.moegirl.sinocraft.sinocore.data.gen.IDataGenContext;
import games.moegirl.sinocraft.sinodeco.block.item.SDBlockItems;
import games.moegirl.sinocraft.sinodeco.data.gen.tag.SDItemTags;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.data.recipes.ShapelessRecipeBuilder;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;

import java.util.function.Consumer;

public class RecipeProvider extends AbstractRecipeProvider {
    public RecipeProvider(IDataGenContext context) {
        super(context);
    }

    @Override
    protected void buildRecipes(Consumer<FinishedRecipe> writer) {
        {
            ShapelessRecipeBuilder.shapeless(RecipeCategory.BUILDING_BLOCKS, SDBlockItems.MARBLE_BLOCK.get(), 2)
                    .requires(Items.CALCITE)
                    .requires(Items.DIORITE)
                    .requires(Items.QUARTZ)
                    .unlockedBy("has_quartz", has(Items.QUARTZ))
                    .save(writer);
            chiseled(writer, RecipeCategory.BUILDING_BLOCKS, SDBlockItems.CHISELED_MARBLE.get(), SDBlockItems.MARBLE_BLOCK.get());
            ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, SDBlockItems.MARBLE_PILLAR.get())
                    .pattern("#")
                    .pattern("#")
                    .define('#', SDBlockItems.MARBLE_BLOCK.get())
                    .unlockedBy("has_marble_blocks", has(SDItemTags.MARBLE_BLOCKS))
                    .save(writer);
            stairBuilder(SDBlockItems.MARBLE_STAIRS.get(), Ingredient.of(SDItemTags.MARBLE_BLOCKS))
                    .unlockedBy("has_marble_blocks", has(SDItemTags.MARBLE_BLOCKS))
                    .save(writer);
            slabBuilder(RecipeCategory.BUILDING_BLOCKS, SDBlockItems.MARBLE_SLAB.get(), Ingredient.of(SDItemTags.MARBLE_BLOCKS))
                    .unlockedBy("has_marble_blocks", has(SDItemTags.MARBLE_BLOCKS))
                    .save(writer);
            ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, SDBlockItems.MARBLE_BRICKS.get())
                    .pattern("##")
                    .pattern("##")
                    .define('#', SDBlockItems.MARBLE_BLOCK.get())
                    .unlockedBy("has_marble_blocks", has(SDItemTags.MARBLE_BLOCKS))
                    .save(writer);
            stairBuilder(SDBlockItems.SMOOTH_MARBLE_STAIRS.get(), Ingredient.of(SDBlockItems.SMOOTH_MARBLE.get()))
                    .unlockedBy("has_smooth_marble_block", has(SDBlockItems.SMOOTH_MARBLE.get()))
                    .save(writer);
            slabBuilder(RecipeCategory.BUILDING_BLOCKS, SDBlockItems.SMOOTH_MARBLE_SLAB.get(), Ingredient.of(SDBlockItems.SMOOTH_MARBLE.get()))
                    .unlockedBy("has_smooth_marble_block", has(SDBlockItems.SMOOTH_MARBLE.get()))
                    .save(writer);
            wall(writer, RecipeCategory.BUILDING_BLOCKS, SDBlockItems.MARBLE_WALL.get(), SDBlockItems.MARBLE_BLOCK.get());

            smeltingResultFromBase(writer, SDBlockItems.SMOOTH_MARBLE.get(), SDBlockItems.MARBLE_BLOCK.get());

            stonecutterResultFromBase(writer, RecipeCategory.BUILDING_BLOCKS, SDBlockItems.CHISELED_MARBLE.get(), SDBlockItems.MARBLE_BLOCK.get());
            stonecutterResultFromBase(writer, RecipeCategory.BUILDING_BLOCKS, SDBlockItems.MARBLE_PILLAR.get(), SDBlockItems.MARBLE_BLOCK.get());
            stonecutterResultFromBase(writer, RecipeCategory.BUILDING_BLOCKS, SDBlockItems.MARBLE_BRICKS.get(), SDBlockItems.MARBLE_BLOCK.get());
            stonecutterResultFromBase(writer, RecipeCategory.BUILDING_BLOCKS, SDBlockItems.MARBLE_STAIRS.get(), SDBlockItems.MARBLE_BLOCK.get());
            stonecutterResultFromBase(writer, RecipeCategory.BUILDING_BLOCKS, SDBlockItems.MARBLE_SLAB.get(), SDBlockItems.MARBLE_BLOCK.get(), 2);
            stonecutterResultFromBase(writer, RecipeCategory.BUILDING_BLOCKS, SDBlockItems.SMOOTH_MARBLE_STAIRS.get(), SDBlockItems.SMOOTH_MARBLE.get());
            stonecutterResultFromBase(writer, RecipeCategory.BUILDING_BLOCKS, SDBlockItems.SMOOTH_MARBLE_SLAB.get(), SDBlockItems.SMOOTH_MARBLE.get(), 2);
            stonecutterResultFromBase(writer, RecipeCategory.BUILDING_BLOCKS, SDBlockItems.MARBLE_WALL.get(), SDBlockItems.MARBLE_BLOCK.get());
        }
    }
}
