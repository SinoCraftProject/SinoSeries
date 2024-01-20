package games.moegirl.sinocraft.sinocore.datagen;

import com.google.common.collect.ImmutableMap;
import games.moegirl.sinocraft.sinocore.mixin_interfaces.IRenamedProvider;
import net.minecraft.data.BlockFamily;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.RecipeBuilder;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ItemLike;

import java.util.Map;
import java.util.function.BiFunction;
import java.util.function.Consumer;

public abstract class AbstractRecipeProvider extends RecipeProvider implements IRenamedProvider {

    protected final String modId;

    public AbstractRecipeProvider(PackOutput output, String modId) {
        super(output);
        this.modId = modId;
    }

    protected abstract void buildRecipes(Consumer<FinishedRecipe> writer);

    /// <editor-fold desc="Methods from RecipeProvider.">

    private static final Map<BlockFamily.Variant, BiFunction<ItemLike, ItemLike, RecipeBuilder>> SHAPE_BUILDERS;

    static {
        SHAPE_BUILDERS = ImmutableMap.<BlockFamily.Variant, BiFunction<ItemLike, ItemLike, RecipeBuilder>>builder()
                .put(BlockFamily.Variant.BUTTON, (arg, arg2) -> buttonBuilder(arg, Ingredient.of(arg2)))
                .put(BlockFamily.Variant.CHISELED, (arg, arg2) -> chiseledBuilder(RecipeCategory.BUILDING_BLOCKS, arg, Ingredient.of(arg2)))
                .put(BlockFamily.Variant.CUT, (arg, arg2) -> cutBuilder(RecipeCategory.BUILDING_BLOCKS, arg, Ingredient.of(arg2)))
                .put(BlockFamily.Variant.DOOR, (arg, arg2) -> doorBuilder(arg, Ingredient.of(arg2)))
                .put(BlockFamily.Variant.CUSTOM_FENCE, (arg, arg2) -> fenceBuilder(arg, Ingredient.of(arg2)))
                .put(BlockFamily.Variant.FENCE, (arg, arg2) -> fenceBuilder(arg, Ingredient.of(arg2)))
                .put(BlockFamily.Variant.CUSTOM_FENCE_GATE, (arg, arg2) -> fenceGateBuilder(arg, Ingredient.of(arg2)))
                .put(BlockFamily.Variant.FENCE_GATE, (arg, arg2) -> fenceGateBuilder(arg, Ingredient.of(arg2)))
                .put(BlockFamily.Variant.SIGN, (arg, arg2) -> signBuilder(arg, Ingredient.of(arg2)))
                .put(BlockFamily.Variant.SLAB, (arg, arg2) -> slabBuilder(RecipeCategory.BUILDING_BLOCKS, arg, Ingredient.of(arg2)))
                .put(BlockFamily.Variant.STAIRS, (arg, arg2) -> stairBuilder(arg, Ingredient.of(arg2)))
                .put(BlockFamily.Variant.PRESSURE_PLATE, (arg, arg2) -> pressurePlateBuilder(RecipeCategory.REDSTONE, arg, Ingredient.of(arg2)))
                .put(BlockFamily.Variant.POLISHED, (arg, arg2) -> polishedBuilder(RecipeCategory.BUILDING_BLOCKS, arg, Ingredient.of(arg2)))
                .put(BlockFamily.Variant.TRAPDOOR, (arg, arg2) -> trapdoorBuilder(arg, Ingredient.of(arg2)))
                .put(BlockFamily.Variant.WALL, (arg, arg2) -> wallBuilder(RecipeCategory.DECORATIONS, arg, Ingredient.of(arg2))).build();
    }

    /// </editor-fold>

    @Override
    public String getNewName() {
        return "Recipes: " + modId;
    }
}
