package games.moegirl.sinocraft.sinodivination.recipe;

import games.moegirl.sinocraft.sinocore.crafting.abstracted.fluid_ingredient.FluidIngredient;
import games.moegirl.sinocraft.sinocore.crafting.abstracted.SimpleRecipeBuilder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.Fluids;
import net.minecraftforge.registries.RegistryObject;

import java.util.ArrayList;
import java.util.List;

public class KettlePotRecipeBuilder extends SimpleRecipeBuilder<KettlePotRecipe, KettlePotRecipeBuilder> {

    private final List<KettlePotRecipe.IngredientEntry> inputs = new ArrayList<>();
    private Ingredient container = Ingredient.of(Items.BOWL);
    private FluidIngredient fluid = new FluidIngredient(Fluids.WATER, 1000);
    private ItemStack output = ItemStack.EMPTY;

    public KettlePotRecipeBuilder(ResourceLocation id) {
        super(id);
    }

    public KettlePotRecipeBuilder input(Ingredient item) {
        return input(1, item);
    }

    public KettlePotRecipeBuilder input(int count, Ingredient item) {
        inputs.add(new KettlePotRecipe.IngredientEntry(count, item));
        return this;
    }

    public KettlePotRecipeBuilder input(ItemLike... items) {
        return input(Ingredient.of(items));
    }

    public KettlePotRecipeBuilder input(int count, ItemLike... items) {
        return input(count, Ingredient.of(items));
    }

    public KettlePotRecipeBuilder input(TagKey<Item> item) {
        return input(Ingredient.of(item));
    }

    public KettlePotRecipeBuilder input(int count, TagKey<Item> item) {
        return input(count, Ingredient.of(item));
    }

    public KettlePotRecipeBuilder input(RegistryObject<? extends ItemLike> item) {
        return input(Ingredient.of(item.get()));
    }

    public KettlePotRecipeBuilder input(int count, RegistryObject<? extends ItemLike> item) {
        return input(count, Ingredient.of(item.get()));
    }

    public KettlePotRecipeBuilder container(Ingredient ingredient) {
        container = ingredient;
        return this;
    }

    public KettlePotRecipeBuilder fluid(Fluid fluid, int amount) {
        this.fluid = new FluidIngredient(fluid, amount);
        return this;
    }

    public KettlePotRecipeBuilder fluid(Fluid fluid) {
        return fluid(fluid, 1000);
    }

    public KettlePotRecipeBuilder fluid(TagKey<Fluid> fluid, int amount) {
        this.fluid = new FluidIngredient(fluid, amount);
        return this;
    }

    public KettlePotRecipeBuilder fluid(TagKey<Fluid> fluid) {
        return fluid(fluid, 1000);
    }

    public KettlePotRecipeBuilder output(ItemStack item) {
        this.output = item;
        return this;
    }

    @Override
    public KettlePotRecipe build() {
        return new KettlePotRecipe(id, inputs.toArray(KettlePotRecipe.IngredientEntry[]::new), container, fluid, output);
    }
}
