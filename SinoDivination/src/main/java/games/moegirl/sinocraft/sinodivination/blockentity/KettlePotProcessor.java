package games.moegirl.sinocraft.sinodivination.blockentity;

import games.moegirl.sinocraft.sinocore.crafting.fluid_ingredient.FluidIngredient;
import games.moegirl.sinocraft.sinocore.crafting.container.ReadonlyItemFluidContainer;
import games.moegirl.sinocraft.sinocore.crafting.recipe.RecipeHolder;
import games.moegirl.sinocraft.sinodivination.recipe.KettlePotRecipe;
import games.moegirl.sinocraft.sinodivination.util.RecipeProcessor;
import games.moegirl.sinocraft.sinodivination.util.container.InputOnlyContainer;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraftforge.fluids.capability.IFluidHandler;

import java.util.Optional;

public class KettlePotProcessor extends RecipeProcessor<KettlePotEntity, ReadonlyItemFluidContainer, KettlePotRecipe> {

    private Ingredient container = Ingredient.EMPTY;
    private ItemStack result = ItemStack.EMPTY;

    public KettlePotProcessor(RecipeHolder<ReadonlyItemFluidContainer, KettlePotRecipe, ?> holder) {
        super(holder, false);
    }

    @Override
    protected void loadProcessor(CompoundTag nbt) {
        if (nbt.contains("Result", Tag.TAG_COMPOUND)) {
            result = ItemStack.of(nbt.getCompound("Result"));
        }
    }

    @Override
    protected void saveProcessor(CompoundTag nbt) {
        if (!result.isEmpty()) {
            nbt.put("Result", result.save(new CompoundTag()));
        }
    }

    @Override
    protected void onResume(KettlePotEntity entity, KettlePotRecipe recipe) {
        container = recipe.getContainer();
        result = recipe.getResultItem();
    }

    @Override
    protected Optional<KettlePotRecipe> onFindRecipe(KettlePotEntity entity) {
        if (entity.getLevel() == null) return Optional.empty();
        InputOnlyContainer input = (InputOnlyContainer) entity.getInput();
        ReadonlyItemFluidContainer container = ReadonlyItemFluidContainer.create(input.getInv(), entity.getTank());
        Optional<KettlePotRecipe> optional = holder.match(entity.getLevel(), container);
        if (optional.isPresent()) {
            KettlePotRecipe recipe = optional.get();
            Int2ObjectMap<KettlePotRecipe.IngredientEntry> matches = recipe.matches(container);
            assert matches != null;
            for (Int2ObjectMap.Entry<KettlePotRecipe.IngredientEntry> entry : matches.int2ObjectEntrySet()) {
                input.extractItem2(entry.getIntKey(), entry.getValue().count(), false);
            }
            FluidIngredient fluid = recipe.getFluid();
            entity.getTank().drain(fluid.amount(), IFluidHandler.FluidAction.EXECUTE);
        }
        return optional;
    }

    @Override
    protected boolean isRecipeEffective(KettlePotEntity entity, KettlePotRecipe recipe) {
        InputOnlyContainer input = (InputOnlyContainer) entity.getInput();
        ReadonlyItemFluidContainer container = ReadonlyItemFluidContainer.create(input.getInv(), entity.getTank());
        return recipe.matches(container, entity.getLevel());
    }

    @Override
    protected void onFinished(KettlePotEntity entity, KettlePotRecipe recipe) {
        container = recipe.getContainer();
        result = recipe.getResultItem();
        isDataChanged = true;
    }

    @Override
    protected boolean onBlocking(KettlePotEntity entity) {
        return !result.isEmpty();
    }

    @Override
    protected int getProcessStep() {
        return 5;
    }

    public ItemStack getResult() {
        return result;
    }

    public Optional<ItemStack> takeItem(ItemStack container) {
        if (!result.isEmpty() && this.container.test(container)) {
            container.shrink(1);
            ItemStack copy = result.copy();
            copy.setCount(1);
            Optional<ItemStack> optional = Optional.of(copy);
            result.shrink(1);
            return optional;
        }
        return Optional.empty();
    }
}
