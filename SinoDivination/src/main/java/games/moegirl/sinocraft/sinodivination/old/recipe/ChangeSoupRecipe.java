package games.moegirl.sinocraft.sinodivination.old.recipe;

import games.moegirl.sinocraft.sinocore.crafting.SimpleRecipe;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.Container;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.Optional;

public class ChangeSoupRecipe extends SimpleRecipe<Container, ChangeSoupRecipe, ChangeSoupRecipeSerializer> {

    public static ChangeSoupRecipeBuilder builder(ResourceLocation id) {
        return new ChangeSoupRecipeBuilder(id);
    }

    public static ChangeSoupRecipeBuilder builder(Block input, RegistryObject<? extends Block> output) {
        return new ChangeSoupRecipeBuilder(output.getId()).input(input).output(output);
    }

    public static ChangeSoupRecipeBuilder builder(Block input, Block output) {
        return new ChangeSoupRecipeBuilder(ForgeRegistries.BLOCKS.getKey(output)).input(input).output(output);
    }

    public static Optional<BlockState> findRecipeResult(Level level, BlockState bs) {
        SimpleContainer container = new SimpleContainer(new ItemStack(bs.getBlock()));
        return level.getRecipeManager().getRecipeFor(SDRecipes.CHANGE_SOUP.recipeType(), container, level)
                .map(ChangeSoupRecipe::getResult);
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
