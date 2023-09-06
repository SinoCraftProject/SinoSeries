package games.moegirl.sinocraft.sinocore.crafting.container;

import net.minecraft.world.Container;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.IFluidTank;
import net.minecraftforge.fluids.capability.IFluidHandler;

/**
 * 只读的流体和物品容器，常用于合成表查询
 *
 * @author luqin2007
 */
public interface ReadonlyItemFluidContainer extends ReadonlyItemContainer {

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
}
