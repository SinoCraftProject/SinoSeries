package games.moegirl.sinocraft.sinocore.crafting;

import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.IFluidTank;
import net.minecraftforge.fluids.capability.IFluidHandler;

/**
 * 只读的流体和物品容器，常用于合成表查询
 */
public interface ReadonlyItemFluidContainer extends Container {

    static ReadonlyItemFluidContainer create(Container items, IFluidHandler fluids) {
        return new FluidsRecipeContainer(items, fluids);
    }

    static ReadonlyItemFluidContainer create(IFluidTank tank, Container items) {
        return new FluidRecipeContainer(items, tank);
    }

    // fluid ===========================================================================================================

    FluidStack getFluid(int index);

    int getFluidCount();

    default boolean isFluidEmpty() {
        return getFluidCount() == 0;
    }

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
