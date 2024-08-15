package games.moegirl.sinocraft.sinocore.mixin.data;

import games.moegirl.sinocraft.sinocore.interfaces.injectable.ISinoRecipeProvider;
import net.minecraft.data.BlockFamily;
import net.minecraft.data.recipes.RecipeBuilder;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.world.level.ItemLike;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

import java.util.Map;
import java.util.function.BiFunction;

@Mixin(RecipeProvider.class)
public class RecipeProviderExtension implements ISinoRecipeProvider {

    @Shadow @Final private static Map<BlockFamily.Variant, BiFunction<ItemLike, ItemLike, RecipeBuilder>> SHAPE_BUILDERS;

    @Override
    public Map<BlockFamily.Variant, BiFunction<ItemLike, ItemLike, RecipeBuilder>> sino$getShapeBuilders() {
        return SHAPE_BUILDERS;
    }
}
