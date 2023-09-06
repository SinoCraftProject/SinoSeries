package games.moegirl.sinocraft.sinocore.crafting.recipe;

import games.moegirl.sinocraft.sinocore.crafting.serializer.AbstractRecipeSerializer;
import games.moegirl.sinocraft.sinocore.utility.Self;
import net.minecraft.core.RegistryAccess;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.Container;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraftforge.registries.RegistryObject;

/**
 * 适用于 Recipe 的简化实现
 *
 * @author luqin2007
 */
public abstract class SimpleRecipe<C extends Container, SELF extends Recipe<C>, S extends AbstractRecipeSerializer<SELF>>
        implements Recipe<C>, Self<SELF> {

    final RegistryObject<RecipeType<SELF>> type;
    final S serializer;
    final ResourceLocation id;
    final int size;
    final ItemStack output;

    public SimpleRecipe(RecipeHolder<C, SELF, S> type, ResourceLocation id, int size, ItemStack output) {
        this.type = type.recipeTypeObj();
        this.serializer = type.serializer();
        this.id = id;
        this.size = size;
        this.output = output;
    }

    @Override
    public ItemStack assemble(C arg, RegistryAccess access) {
        return getResultItem();
    }

    @Override
    public boolean canCraftInDimensions(int pWidth, int pHeight) {
        return pWidth * pHeight >= size;
    }

    @Override
    public final ItemStack getResultItem(RegistryAccess arg) {
        return getResultItem();
    }

    public ItemStack getResultItem() {
        return output.copy();
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
        return type.get();
    }

    public FinishedRecipe finished() {
        return new SimpleFinishedRecipe<>(serializer, self());
    }
}
