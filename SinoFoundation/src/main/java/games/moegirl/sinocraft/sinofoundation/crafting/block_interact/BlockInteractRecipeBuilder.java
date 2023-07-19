package games.moegirl.sinocraft.sinofoundation.crafting.block_interact;

import games.moegirl.sinocraft.sinocore.crafting.SimpleRecipeBuilder;
import games.moegirl.sinocraft.sinocore.crafting.block_ingredient.BlockIngredient;
import games.moegirl.sinocraft.sinocore.crafting.block_ingredient.BlockIngredients;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.block.state.BlockState;

import javax.annotation.Nullable;

/**
 * @author luqin2007
 */
public class BlockInteractRecipeBuilder extends SimpleRecipeBuilder<BlockInteractRecipe, BlockInteractRecipeBuilder> {

    private Ingredient inputItem = Ingredient.EMPTY;
    private BlockIngredient<?> inputBlock = BlockIngredients.none();
    private int damage = 0;
    private int count = 0;

    @Nullable
    private BlockState outputBlock = null;
    private ItemStack outputItem = ItemStack.EMPTY;

    public BlockInteractRecipeBuilder(ResourceLocation id) {
        super(id);
    }

    public BlockInteractRecipeBuilder item(Ingredient input) {
        inputItem = input;
        count = 1;
        return this;
    }

    public BlockInteractRecipeBuilder block(BlockIngredient<?> input) {
        inputBlock = input;
        return this;
    }

    public BlockInteractRecipeBuilder damage(int damage) {
        this.damage = damage;
        this.count = 0;
        return this;
    }

    public BlockInteractRecipeBuilder count(int count) {
        this.count = count;
        this.damage = 0;
        return this;
    }

    public BlockInteractRecipeBuilder output(@Nullable BlockState block) {
        outputBlock = block;
        return this;
    }

    public BlockInteractRecipeBuilder output(ItemStack item) {
        outputItem = item.copy();
        return this;
    }

    @Override
    public BlockInteractRecipe build() {
        return new BlockInteractRecipe(id, outputItem, outputBlock, inputItem, inputBlock, damage, count);
    }
}
