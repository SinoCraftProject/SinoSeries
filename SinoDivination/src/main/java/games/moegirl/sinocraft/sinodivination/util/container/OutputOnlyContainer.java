package games.moegirl.sinocraft.sinodivination.util.container;

import net.minecraft.world.SimpleContainer;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;

/**
 * 只能输出的 Container
 */
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
    public boolean isItemValid(int slot, @NotNull ItemStack stack) {
        return false;
    }
}
