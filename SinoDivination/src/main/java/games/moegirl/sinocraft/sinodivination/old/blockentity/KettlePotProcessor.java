package games.moegirl.sinocraft.sinodivination.old.blockentity;

import games.moegirl.sinocraft.sinocore.crafting.FluidIngredient;
import games.moegirl.sinocraft.sinocore.crafting.ReadonlyItemFluidContainer;
import games.moegirl.sinocraft.sinocore.crafting.RecipeHolder;
import games.moegirl.sinocraft.sinocore.utility.OptionalTag;
import games.moegirl.sinocraft.sinodivination.old.recipe.KettlePotRecipe;
import games.moegirl.sinocraft.sinodivination.old.util.RecipeProcessor;
import games.moegirl.sinocraft.sinodivination.old.util.container.InputOnlyContainer;
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
    private final KettlePotEntity entity;

    public KettlePotProcessor(RecipeHolder<ReadonlyItemFluidContainer, KettlePotRecipe, ?> holder, KettlePotEntity entity) {
        super(holder, false);
        this.entity = entity;
    }

    @Override
    protected void loadProcessor(CompoundTag tags, CompoundTag processorTag) {
        result = OptionalTag.of(processorTag)
                .getCompound("Result")
                .mapToObj(ItemStack::of)
                .orElse(ItemStack.EMPTY);
    }

    @Override
    protected void saveProcessor(CompoundTag tags, CompoundTag processorTag) {
        if (!result.isEmpty()) {
            processorTag.put("Result", result.save(new CompoundTag()));
        }
    }

    @Override
    protected void resumeRecipe(KettlePotRecipe recipe) {
        container = recipe.getContainer();
        result = recipe.getResultItem();
    }

    @Override
    protected void updateTags(CompoundTag tags) {
        if (tags.contains("Recipe", Tag.TAG_STRING)) {
            CompoundTag processor = new CompoundTag();
            processor.putString("RecipeId", tags.getString("Recipe"));
            processor.putInt("Progress", tags.getInt("Progress"));
            processor.putInt("ItemCount", tags.getInt("RemainStack"));

            tags.remove("Recipe");
            tags.remove("Progress");
            tags.remove("RemainStack");
        }
    }

    @Override
    protected Optional<KettlePotRecipe> trySetRecipe() {
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
    protected void recipeFinished(KettlePotRecipe recipe) {
        container = recipe.getContainer();
        result = recipe.getResultItem().copy();
    }

    @Override
    protected boolean hasRemainItems() {
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
