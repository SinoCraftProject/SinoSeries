package games.moegirl.sinocraft.sinocalligraphy.data.gen;

import games.moegirl.sinocraft.sinocalligraphy.block.tree.SCATrees;
import games.moegirl.sinocraft.sinocalligraphy.item.SCAItems;
import games.moegirl.sinocraft.sinocore.data.gen.AbstractRecipeProvider;
import games.moegirl.sinocraft.sinofoundation.data.gen.tag.SFDItemTags;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.world.item.ItemStack;

import java.util.function.Consumer;

public class SCARecipeProvider extends AbstractRecipeProvider {
    public SCARecipeProvider(PackOutput output, String modid) {
        super(output, modid);
    }

    @Override
    protected void buildRecipes(Consumer<FinishedRecipe> writer) {
        treeStrippingRecipe(SCATrees.GREEN_SANDALWOOD, SFDItemTags.KNIVES, new ItemStack(SCAItems.GREEN_SANDALWOOD_BARK.get()), writer);
    }
}
