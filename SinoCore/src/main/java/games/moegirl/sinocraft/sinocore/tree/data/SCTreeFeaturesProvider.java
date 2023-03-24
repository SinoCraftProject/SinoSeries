package games.moegirl.sinocraft.sinocore.tree.data;

import games.moegirl.sinocraft.sinocore.data.BaseCodecProvider;
import games.moegirl.sinocraft.sinocore.tree.TreeBlockType;
import games.moegirl.sinocraft.sinocore.tree.TreeType;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.BlockFamily;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.data.recipes.ShapedRecipeBuilder;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;

public class SCTreeFeaturesProvider extends BaseCodecProvider {
    protected final List<TreeType> treeTypes;

    public SCTreeFeaturesProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> provider, String modid, List<TreeType> treeTypes) {
        super(output, provider, modid);

        this.treeTypes = treeTypes;
    }

    @Override
    protected void addAll() {
        for (var tree : treeTypes) {
            // Todo.
        }
        features.forEach((k, s) -> add(k, s.get()));
    }
}
