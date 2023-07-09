package games.moegirl.sinocraft.sinocore.tree.event.data;

import games.moegirl.sinocraft.sinocore.data.gen.recipe.AbstractRecipeProvider;
import games.moegirl.sinocraft.sinocore.tree.Tree;
import games.moegirl.sinocraft.sinocore.tree.TreeBlockType;
import net.minecraft.data.BlockFamily;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.tags.ItemTags;

import java.util.List;
import java.util.function.Consumer;

public class ProviderRecipe extends AbstractRecipeProvider {
    protected final List<Tree> treeTypes;

    public ProviderRecipe(PackOutput output, String modid, List<Tree> treeTypes) {
        super(output, modid);

        this.treeTypes = treeTypes;
    }

    @Override
    protected void buildRecipes(Consumer<FinishedRecipe> writer) {
        for (var tree : treeTypes) {
            planksFromLog(writer, tree.getItem(TreeBlockType.PLANKS), ItemTags.create(tree.getDefaultLogTag()), 4);
            woodFromLogs(writer, tree.getBlock(TreeBlockType.LOG_WOOD), tree.getBlock(TreeBlockType.LOG));
            woodFromLogs(writer, tree.getBlock(TreeBlockType.STRIPPED_LOG_WOOD), tree.getBlock(TreeBlockType.STRIPPED_LOG));

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

            hangingSign(writer, tree.getBlock(TreeBlockType.HANGING_SIGN), tree.getBlock(TreeBlockType.PLANKS));
        }
    }

    @Override
    public String getName() {
        return "Mod Tree Recipes: " + modid;
    }
}
