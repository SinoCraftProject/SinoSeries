package games.moegirl.sinocraft.sinodivination.old.util.container;

import net.minecraft.world.SimpleContainer;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;

public class InputOnlyContainer extends LimitedContainer<InputOnlyContainer> {

    public InputOnlyContainer(SimpleContainer inv) {
        super(inv);
    }

    public InputOnlyContainer(int count) {
        super(count);
    }

    public InputOnlyContainer(int count, int stackSize) {
        super(count, stackSize);
    }

    @NotNull
    @Override
    public ItemStack extractItem(int slot, int amount, boolean simulate) {
        return ItemStack.EMPTY;
    }
}