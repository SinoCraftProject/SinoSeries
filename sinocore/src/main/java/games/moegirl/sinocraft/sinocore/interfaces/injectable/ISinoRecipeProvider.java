package games.moegirl.sinocraft.sinocore.interfaces.injectable;

import net.minecraft.data.BlockFamily;
import net.minecraft.data.recipes.RecipeBuilder;
import net.minecraft.world.level.ItemLike;

import java.util.Map;
import java.util.function.BiFunction;

public interface ISinoRecipeProvider {

    default Map<BlockFamily.Variant, BiFunction<ItemLike, ItemLike, RecipeBuilder>> sino$getShapeBuilders() {
        throw new AssertionError();
    }
}
