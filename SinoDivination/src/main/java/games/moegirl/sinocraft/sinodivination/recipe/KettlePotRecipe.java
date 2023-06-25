package games.moegirl.sinocraft.sinodivination.recipe;

import games.moegirl.sinocraft.sinocore.crafting.CraftHelper;
import games.moegirl.sinocraft.sinocore.crafting.fluid_ingredient.FluidIngredient;
import games.moegirl.sinocraft.sinocore.crafting.ReadonlyItemFluidContainer;
import games.moegirl.sinocraft.sinocore.crafting.SimpleRecipe;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import net.minecraft.core.NonNullList;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraftforge.registries.RegistryObject;

import javax.annotation.Nullable;

public class KettlePotRecipe extends SimpleRecipe<ReadonlyItemFluidContainer, KettlePotRecipe, KettlePotRecipeSerializer> {

    public static KettlePotRecipeBuilder builder(RegistryObject<? extends ItemLike> output, int count) {
        return new KettlePotRecipeBuilder(output.getId()).output(new ItemStack(output.get(), count));
    }

    final IngredientEntry[] inputs;
    final FluidIngredient fluid;
    final Ingredient container;

    public KettlePotRecipe(ResourceLocation id, IngredientEntry[] inputs, Ingredient container, FluidIngredient fluid, ItemStack output) {
        super(SDRecipes.KETTLE_POT, id, inputs.length, output);
        this.inputs = inputs;
        this.container = container;
        this.fluid = fluid;
    }

    @Override
    public NonNullList<Ingredient> getIngredients() {
        return NonNullList.of(inputs[0].ingredient, inputs[1].ingredient, inputs[2].ingredient);
    }

    @Override
    public boolean matches(ReadonlyItemFluidContainer pContainer, Level pLevel) {
        return fluid.test(pContainer.getFluid(0)) && CraftHelper.matchShapeless(pContainer, inputs, IngredientEntry::test) != null;
    }

    @Nullable
    public Int2ObjectMap<IngredientEntry> matches(ReadonlyItemFluidContainer pContainer) {
        return CraftHelper.matchShapeless(pContainer, inputs, IngredientEntry::test);
    }

    public Ingredient getContainer() {
        return container;
    }

    public FluidIngredient getFluid() {
        return fluid;
    }

    public IngredientEntry getInput(int index) {
        return inputs[index];
    }

    public record IngredientEntry(int count, Ingredient ingredient) {
        public static final IngredientEntry EMPTY = new IngredientEntry(0, Ingredient.EMPTY);

        public boolean test(ItemStack stack) {
            if (ingredient.isEmpty()) {
                return true;
            }
            return ingredient.test(stack) && stack.getCount() >= count;
        }

        public ItemStack[] getItems() {
            ItemStack[] stacks = ingredient.getItems();
            ItemStack[] s2 = new ItemStack[stacks.length];
            for (int i = 0; i < stacks.length; i++) {
                ItemStack stack = stacks[i].copy();
                stack.setCount(count);
                s2[i] = stack;
            }
            return s2;
        }
    }
}
