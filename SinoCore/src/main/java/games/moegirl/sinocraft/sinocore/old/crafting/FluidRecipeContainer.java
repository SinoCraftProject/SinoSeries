package games.moegirl.sinocraft.sinocore.old.crafting;

import net.minecraft.world.Container;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.IFluidTank;

import java.util.Set;

public class FluidRecipeContainer implements RecipeContainer {

    private final Container items;
    private final IFluidTank fluid;

    public FluidRecipeContainer(Container items, IFluidTank fluid) {
        this.items = items;
        this.fluid = fluid;
    }

    @Override
    public int getContainerSize() {
        return items.getContainerSize();
    }

    @Override
    public boolean isEmpty() {
        return items.isEmpty();
    }

    @Override
    public ItemStack getItem(int index) {
        return items.getItem(index);
    }

    @Override
    public int countItem(Item item) {
        return items.countItem(item);
    }

    @Override
    public boolean hasAnyOf(Set<Item> set) {
        return items.hasAnyOf(set);
    }

    @Override
    public FluidStack getFluid(int index) {
        return index == 0 ? fluid.getFluid() : FluidStack.EMPTY;
    }

    @Override
    public int getFluidCount() {
        return 1;
    }
}
