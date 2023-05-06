package games.moegirl.sinocraft.sinodivination.old.util.container;

import net.minecraft.world.Container;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;

public class OutputOnlyContainer extends LimitedContainer<OutputOnlyContainer> {

    public OutputOnlyContainer(SimpleContainer inv) {
        super(inv);
    }

    public OutputOnlyContainer(int count) {
        super(count);
    }

    public OutputOnlyContainer(int count, int stackSize) {
        super(count, stackSize);
    }

    @Override
    public void setStackInSlot(int slot, @NotNull ItemStack stack) {
    }

    @Override
    public boolean isItemValid(int slot, @NotNull ItemStack stack) {
        return false;
    }
}
