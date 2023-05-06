package games.moegirl.sinocraft.sinodivination.old.recipe;

import games.moegirl.sinocraft.sinocore.crafting.SimpleRecipeBuilder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;

import java.util.function.Supplier;

public class ChangeSoupRecipeBuilder extends SimpleRecipeBuilder<ChangeSoupRecipe, ChangeSoupRecipeBuilder> {

    private Block input = Blocks.AIR;
    private Block output = Blocks.AIR;

    public ChangeSoupRecipeBuilder(ResourceLocation id) {
        super(id);
    }

    public ChangeSoupRecipeBuilder input(Block block) {
        this.input = block;
        return this;
    }

    public ChangeSoupRecipeBuilder input(Supplier<? extends Block> block) {
        return input(block.get());
    }

    public ChangeSoupRecipeBuilder output(Block block) {
        this.output = block;
        return this;
    }

    public ChangeSoupRecipeBuilder output(Supplier<? extends Block> block) {
        return output(block.get());
    }

    @Override
    public ChangeSoupRecipe build() {
        return new ChangeSoupRecipe(id, input, output);
    }
}
