package games.moegirl.sinocraft.sinocalligraphy.data.gen;

import games.moegirl.sinocraft.sinocalligraphy.block.tree.SCATrees;
import games.moegirl.sinocraft.sinocalligraphy.item.SCAItems;
import games.moegirl.sinocraft.sinocore.crafting.block_ingredient.BlockIngredients;
import games.moegirl.sinocraft.sinocore.data.gen.AbstractRecipeProvider;
import games.moegirl.sinocraft.sinocore.tree.TreeBlockType;
import games.moegirl.sinocraft.sinofoundation.crafting.knife.KnifeRecipe;
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
        KnifeRecipe.builder(SCATrees.GREEN_SANDALWOOD.getBlock(TreeBlockType.STRIPPED_LOG))
                .setBlock(BlockIngredients.block(SCATrees.GREEN_SANDALWOOD.getBlock(TreeBlockType.LOG)))
                .setOutput(new ItemStack(SCAItems.GREEN_SANDALWOOD_BARK.get()))
                .save(writer);
        KnifeRecipe.builder(SCATrees.GREEN_SANDALWOOD.getBlock(TreeBlockType.STRIPPED_LOG_WOOD))
                .setBlock(BlockIngredients.block(SCATrees.GREEN_SANDALWOOD.getBlock(TreeBlockType.LOG_WOOD)))
                .setOutput(new ItemStack(SCAItems.GREEN_SANDALWOOD_BARK.get()))
                .save(writer);
    }
}
