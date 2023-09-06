package games.moegirl.sinocraft.sinocore.crafting.container;

import net.minecraft.world.Container;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.IFluidTank;

/**
 * 同时存储物品和一种流体的只读容器
 *
 * @author luqin2007
 */
public class FluidRecipeContainer implements ReadonlyItemFluidContainer {

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
    public FluidStack getFluid(int index) {
        return index == 0 ? fluid.getFluid() : FluidStack.EMPTY;
    }

    @Override
    public int getFluidCount() {
        return 1;
    }

    @Override
    public boolean isFluidEmpty() {
        return fluid.getFluid().isEmpty();
    }
}
