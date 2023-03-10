package games.moegirl.sinocraft.sinocore.old.crafting;

import games.moegirl.sinocraft.sinocore.api.crafting.RecipeContainer;
import net.minecraft.world.Container;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.IFluidHandler;

import java.util.Set;

public class FluidsRecipeContainer implements RecipeContainer {

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
    public int countItem(Item item) {
        return items.countItem(item);
    }

    @Override
    public boolean hasAnyOf(Set<Item> set) {
        return items.hasAnyOf(set);
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
