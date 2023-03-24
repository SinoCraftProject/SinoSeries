package games.moegirl.sinocraft.sinocore.tree.event.data;

import games.moegirl.sinocraft.sinocore.tree.TreeBlockType;
import games.moegirl.sinocraft.sinocore.tree.Tree;
import net.minecraft.data.BlockFamily;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.*;

import java.util.List;
import java.util.function.Consumer;

public class SCTreeRecipeProvider extends RecipeProvider {
    protected final List<Tree> treeTypes;

    public SCTreeRecipeProvider(PackOutput output, List<Tree> treeTypes) {
        super(output);

        this.treeTypes = treeTypes;
    }

    @Override
    protected void buildRecipes(Consumer<FinishedRecipe> writer) {
        for (var tree : treeTypes) {
            ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, tree.getBlock(TreeBlockType.LOG_WOOD), 3).group("bark")
                    .define('#', tree.getBlock(TreeBlockType.LOG))
                    .pattern("##")
                    .pattern("##")
                    .unlockedBy("has_log", has(tree.getBlock(TreeBlockType.STRIPPED_LOG)))
                    .save(writer);

            ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, tree.getBlock(TreeBlockType.STRIPPED_LOG_WOOD), 3).group("bark")
                    .define('#', tree.getBlock(TreeBlockType.STRIPPED_LOG))
                    .pattern("##")
                    .pattern("##")
                    .unlockedBy("has_stripped_log", has(tree.getBlock(TreeBlockType.STRIPPED_LOG)))
                    .save(writer);

            var family = new BlockFamily.Builder(tree.getBlock(TreeBlockType.PLANKS))
                    .stairs(tree.getBlock(TreeBlockType.STAIRS))
                    .slab(tree.getBlock(TreeBlockType.SLAB))
                    .sign(tree.getBlock(TreeBlockType.SIGN), tree.getBlock(TreeBlockType.WALL_SIGN))
                    .button(tree.getBlock(TreeBlockType.BUTTON))
                    .pressurePlate(tree.getBlock(TreeBlockType.PRESSURE_PLATE))
                    .fence(tree.getBlock(TreeBlockType.FENCE))
                    .fenceGate(tree.getBlock(TreeBlockType.FENCE_GATE))
                    .door(tree.getBlock(TreeBlockType.DOOR))
                    .trapdoor(tree.getBlock(TreeBlockType.TRAPDOOR))
                    .recipeGroupPrefix("wooden")
                    .recipeUnlockedBy("has_planks")
                    .dontGenerateModel()
                    .getFamily();
            generateRecipes(writer, family);
        }
    }
}
