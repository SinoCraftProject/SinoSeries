package games.moegirl.sinocraft.sinocore.old.crafting;

import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.IFluidTank;
import net.minecraftforge.fluids.capability.IFluidHandler;

/**
 * A readonly container for recipe, allowed items and fluids
 *
 * <p>Warning: The Container is read-only!!!</p>
 */
public interface RecipeContainer extends Container {

    static RecipeContainer create(Container items, IFluidHandler fluids) {
        return new FluidsRecipeContainer(items, fluids);
    }

    static RecipeContainer createTank(Container items, IFluidTank tank) {
        return new FluidRecipeContainer(items, tank);
    }

    /**
     * Get input fluid by index.
     * Use for the item handler that contains inputs and outputs
     */
    FluidStack getFluid(int index);

    /**
     * Get the input fluids count.
     */
    int getFluidCount();

    // ban =============================================================================================================

    @Override
    default ItemStack removeItem(int index, int count) {
        return ItemStack.EMPTY;
    }

    @Override
    default ItemStack removeItemNoUpdate(int index) {
        return ItemStack.EMPTY;
    }

    @Override
    default void setItem(int index, ItemStack stack) {
    }

    @Override
    default void setChanged() {
    }

    @Override
    default boolean stillValid(Player player) {
        return true;
    }

    @Override
    default boolean canPlaceItem(int index, ItemStack stack) {
        return false;
    }

    @Override
    default void clearContent() {
    }
}
