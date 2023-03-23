package games.moegirl.sinocraft.sinocore.crafting;

import net.minecraft.world.Container;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.IFluidHandler;

/**
 * 同时存储物品和多种流体的只读容器
 *
 * @author luqin2007
 */
public class FluidsRecipeContainer implements ReadonlyItemFluidContainer {

    private final Container items;
    private final IFluidHandler fluids;

    public FluidsRecipeContainer(Container items, IFluidHandler fluids) {
        this.items = items;
        this.fluids = fluids;
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
        return fluids.getFluidInTank(index);
    }

    @Override
    public int getFluidCount() {
        return fluids.getTanks();
    }
}
