package games.moegirl.sinocraft.sinofoundation.crafting.knife;

import games.moegirl.sinocraft.sinocore.crafting.recipe.SimpleRecipeBuilder;
import games.moegirl.sinocraft.sinocore.crafting.block_ingredient.BlockIngredient;
import games.moegirl.sinocraft.sinocore.crafting.block_ingredient.BlockIngredients;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;

public class KnifeRecipeBuilder extends SimpleRecipeBuilder<KnifeRecipe, KnifeRecipeBuilder> {

    private ItemStack output = ItemStack.EMPTY;
    private BlockIngredient<?> block = BlockIngredients.none();
    private Block blockResult = Blocks.AIR;
    private int count = 0;

    public KnifeRecipeBuilder(ResourceLocation id) {
        super(id);
    }

    public KnifeRecipeBuilder setBlock(BlockIngredient<?> block) {
        this.block = block;
        return this;
    }

    public KnifeRecipeBuilder setOutput(ItemStack output) {
        this.output = output.copy();
        this.count = output.getCount();
        return this;
    }

    public KnifeRecipeBuilder setOutput(Item output) {
        this.output = new ItemStack(output);
        return this;
    }

    public KnifeRecipeBuilder setCount(int count) {
        this.count = count;
        return this;
    }

    public KnifeRecipeBuilder setResultBlock(Block result) {
        this.blockResult = result;
        return this;
    }

    @Override
    public KnifeRecipe build() {
        return new KnifeRecipe(id, output, block, count, blockResult);
    }
}
