package games.moegirl.sinocraft.sinodeco.data.gen;

import games.moegirl.sinocraft.sinocore.data.gen.recipe.AbstractRecipeProvider;
import games.moegirl.sinocraft.sinocore.data.gen.IDataGenContext;
import games.moegirl.sinocraft.sinocore.data.gen.recipe.CommonRecipes;
import games.moegirl.sinocraft.sinodeco.block.item.SDBlockItems;
import games.moegirl.sinocraft.sinodeco.data.gen.tag.SDItemTags;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.data.recipes.ShapelessRecipeBuilder;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;

public class RecipeProvider extends AbstractRecipeProvider {
    public RecipeProvider(IDataGenContext context) {
        super(context);
    }

    @Override
    protected void buildRecipes(RecipeOutput writer) {
        {
            ShapelessRecipeBuilder.shapeless(RecipeCategory.BUILDING_BLOCKS, SDBlockItems.MARBLE_BLOCK.get(), 2)
                    .requires(Items.CALCITE)
                    .requires(Items.DIORITE)
                    .requires(Items.QUARTZ)
                    .unlockedBy("has_quartz", has(Items.QUARTZ))
                    .save(writer);

            CommonRecipes.chiseledBuilder(SDBlockItems.CHISELED_MARBLE.get(), SDBlockItems.MARBLE_BLOCK.get()).save(writer);
            CommonRecipes.pillarBuilder(SDBlockItems.MARBLE_PILLAR.get(), SDBlockItems.MARBLE_BLOCK.get()).save(writer);
            CommonRecipes.stairsBuilder(SDBlockItems.MARBLE_STAIRS.get(), SDItemTags.MARBLE_BLOCKS).save(writer);
            CommonRecipes.slabBuilder(SDBlockItems.MARBLE_SLAB.get(), SDItemTags.MARBLE_BLOCKS).save(writer);
            CommonRecipes.bricksBuilder(SDBlockItems.MARBLE_BRICKS.get(), SDBlockItems.MARBLE_BLOCK.get()).save(writer);
            CommonRecipes.stairsBuilder(SDBlockItems.SMOOTH_MARBLE_STAIRS.get(), SDBlockItems.SMOOTH_MARBLE.get()).save(writer);
            CommonRecipes.slabBuilder(SDBlockItems.SMOOTH_MARBLE_SLAB.get(), SDBlockItems.SMOOTH_MARBLE.get()).save(writer);
            CommonRecipes.wallBuilder(SDBlockItems.MARBLE_BALUSTRADE.get(), SDBlockItems.MARBLE_BLOCK.get()).save(writer);

            smeltingResultFromBase(writer, SDBlockItems.SMOOTH_MARBLE.get(), SDBlockItems.MARBLE_BLOCK.get());

            stonecutterResultFromBase(writer, RecipeCategory.BUILDING_BLOCKS, SDBlockItems.CHISELED_MARBLE.get(), SDBlockItems.MARBLE_BLOCK.get());
            stonecutterResultFromBase(writer, RecipeCategory.BUILDING_BLOCKS, SDBlockItems.MARBLE_PILLAR.get(), SDBlockItems.MARBLE_BLOCK.get());
            stonecutterResultFromBase(writer, RecipeCategory.BUILDING_BLOCKS, SDBlockItems.MARBLE_BRICKS.get(), SDBlockItems.MARBLE_BLOCK.get());
            stonecutterResultFromBase(writer, RecipeCategory.BUILDING_BLOCKS, SDBlockItems.MARBLE_STAIRS.get(), SDBlockItems.MARBLE_BLOCK.get());
            stonecutterResultFromBase(writer, RecipeCategory.BUILDING_BLOCKS, SDBlockItems.MARBLE_SLAB.get(), SDBlockItems.MARBLE_BLOCK.get(), 2);
            stonecutterResultFromBase(writer, RecipeCategory.BUILDING_BLOCKS, SDBlockItems.SMOOTH_MARBLE_STAIRS.get(), SDBlockItems.SMOOTH_MARBLE.get());
            stonecutterResultFromBase(writer, RecipeCategory.BUILDING_BLOCKS, SDBlockItems.SMOOTH_MARBLE_SLAB.get(), SDBlockItems.SMOOTH_MARBLE.get(), 2);
            stonecutterResultFromBase(writer, RecipeCategory.BUILDING_BLOCKS, SDBlockItems.MARBLE_BALUSTRADE.get(), SDBlockItems.MARBLE_BLOCK.get());
        }

        {
            table(SDBlockItems.ACACIA_WOOD_TABLE.get(), Items.ACACIA_PLANKS, writer);
            table(SDBlockItems.BAMBOO_WOOD_TABLE.get(), Items.BAMBOO_PLANKS, writer);
            table(SDBlockItems.BIRCH_WOOD_TABLE.get(), Items.BIRCH_PLANKS, writer);
            table(SDBlockItems.CHERRY_WOOD_TABLE.get(), Items.CHERRY_PLANKS, writer);
            table(SDBlockItems.CRIMSON_WOOD_TABLE.get(), Items.CRIMSON_PLANKS, writer);
            table(SDBlockItems.DARK_OAK_WOOD_TABLE.get(), Items.DARK_OAK_PLANKS, writer);
            table(SDBlockItems.JUNGLE_WOOD_TABLE.get(), Items.JUNGLE_PLANKS, writer);
            table(SDBlockItems.MANGROVE_WOOD_TABLE.get(), Items.MANGROVE_PLANKS, writer);
            table(SDBlockItems.OAK_WOOD_TABLE.get(), Items.OAK_PLANKS, writer);
            table(SDBlockItems.SPRUCE_WOOD_TABLE.get(), Items.SPRUCE_PLANKS, writer);
            table(SDBlockItems.WARPED_WOOD_TABLE.get(), Items.WARPED_PLANKS, writer);
            table(SDBlockItems.PEACH_WOOD_TABLE.get(), SDBlockItems.PEACH_PLANKS.get(), writer);
        }

        {
            CommonRecipes.logsToWoodBuilder(SDBlockItems.PEACH_WOOD.get(), SDBlockItems.PEACH_LOG.get());
            CommonRecipes.logsToWoodBuilder(SDBlockItems.STRIPPED_PEACH_WOOD.get(), SDBlockItems.STRIPPED_PEACH_LOG.get());
            CommonRecipes.logsToPlanksBuilder(SDBlockItems.PEACH_PLANKS.get(), SDItemTags.PEACH_LOGS, 4);
            CommonRecipes.stairsBuilder(SDBlockItems.PEACH_STAIRS.get(), SDBlockItems.PEACH_PLANKS.get()).save(writer);
            CommonRecipes.slabBuilder(SDBlockItems.PEACH_SLAB.get(), SDBlockItems.PEACH_PLANKS.get()).save(writer);
            CommonRecipes.fenceBuilder(SDBlockItems.PEACH_FENCE.get(), SDBlockItems.PEACH_PLANKS.get()).save(writer);
            CommonRecipes.fenceGateBuilder(SDBlockItems.PEACH_FENCE_GATE.get(), SDBlockItems.PEACH_PLANKS.get()).save(writer);
            CommonRecipes.doorBuilder(SDBlockItems.PEACH_DOOR.get(), SDBlockItems.PEACH_PLANKS.get()).save(writer);
            CommonRecipes.trapdoorBuilder(SDBlockItems.PEACH_TRAPDOOR.get(), SDBlockItems.PEACH_PLANKS.get()).save(writer);
            CommonRecipes.pressurePlateBuilder(SDBlockItems.PEACH_PRESSURE_PLATE.get(), SDBlockItems.PEACH_PLANKS.get()).save(writer);;
            CommonRecipes.buttonBuilder(SDBlockItems.PEACH_BUTTON.get(), SDBlockItems.PEACH_PLANKS.get()).save(writer);
            CommonRecipes.signBuilder(SDBlockItems.PEACH_SIGN.get(), SDBlockItems.PEACH_PLANKS.get()).save(writer);
            CommonRecipes.hangingSignBuilder(SDBlockItems.PEACH_HANGING_SIGN.get(), SDBlockItems.PEACH_PLANKS.get()).save(writer);
        }
    }

    private void table(Item result, Item planks, RecipeOutput writer) {
        ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, result)
                .pattern("PPP")
                .pattern("S S")
                .pattern("S S")
                .define('P', planks)
                .define('S', Items.STICK)
                .unlockedBy("planks", has(planks))
                .group("tables")
                .save(writer);
    }
}
