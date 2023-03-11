package games.moegirl.sinocraft.sinocore.old.crafting;

import games.moegirl.sinocraft.sinocore.old.utility.Self;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.Container;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeType;

public abstract class SimpleRecipe<C extends Container, SELF extends Recipe<C>, S extends AbstractRecipeSerializer<SELF>> implements Recipe<C>, Self<SELF> {

    final RecipeType<SELF> type;
    final S serializer;
    final ResourceLocation id;
    final int size;
    final ItemStack output;

    public SimpleRecipe(RecipeHolder<C, SELF, S> type, ResourceLocation id, int size, ItemStack output) {
        this.type = type.recipeType();
        this.serializer = type.serializer();
        this.id = id;
        this.size = size;
        this.output = output;
    }

    @Override
    public ItemStack assemble(C pContainer) {
        return output.copy();
    }

    @Override
    public boolean canCraftInDimensions(int pWidth, int pHeight) {
        return pWidth * pHeight >= size;
    }

    @Override
    public ItemStack getResultItem() {
        return output;
    }

    @Override
    public ResourceLocation getId() {
        return id;
    }

    @Override
    public S getSerializer() {
        return serializer;
    }

    @Override
    public RecipeType<SELF> getType() {
        return type;
    }

    public FinishedRecipe finished() {
        return new SimpleFinishedRecipe<>(serializer, self());
    }
}
