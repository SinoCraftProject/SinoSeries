package games.moegirl.sinocraft.sinodivination.recipe;

import games.moegirl.sinocraft.sinocore.crafting.recipe.SimpleRecipe;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.Container;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.registries.ForgeRegistries;

public class ChangeSoupRecipe extends SimpleRecipe<Container, ChangeSoupRecipe, ChangeSoupRecipeSerializer> {

    public static ChangeSoupRecipeBuilder builder(Block input, Block output) {
        return new ChangeSoupRecipeBuilder(ForgeRegistries.BLOCKS.getKey(output)).input(input).output(output);
    }

    public final Block input;
    public final Block result;
    public final Item inputItem;

    public ChangeSoupRecipe(ResourceLocation id, Block input, Block result) {
        super(SDRecipes.CHANGE_SOUP, id, 1, new ItemStack(result));
        this.input = input;
        this.result = result;

        inputItem = input.asItem();
    }

    @Override
    public boolean matches(Container pContainer, Level pLevel) {
        return pContainer.getContainerSize() > 0 && pContainer.getItem(0).is(inputItem);
    }

    public BlockState getResult() {
        return result.defaultBlockState();
    }
}
