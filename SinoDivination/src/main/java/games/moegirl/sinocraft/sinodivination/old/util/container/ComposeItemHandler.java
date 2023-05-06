package games.moegirl.sinocraft.sinodivination.old.util.container;

import net.minecraft.world.item.ItemStack;
import net.minecraftforge.items.IItemHandler;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ComposeItemHandler implements IItemHandler {

    private final List<Handler> all = new ArrayList<>();
    private int slots = 0;

    public ComposeItemHandler append(IItemHandler handler) {
        int i = handler.getSlots();
        all.add(new Handler(handler, slots, slots + i));
        slots += i;
        return this;
    }

    @Override
    public int getSlots() {
        return slots;
    }

    @NotNull
    @Override
    public ItemStack getStackInSlot(int slot) {
        return find(slot, IItemHandler::getStackInSlot).orElse(ItemStack.EMPTY);
    }

    @NotNull
    @Override
    public ItemStack insertItem(int slot, @NotNull ItemStack stack, boolean simulate) {
        return find(slot, (h, s) -> h.insertItem(s, stack, simulate)).orElse(stack);
    }

    @NotNull
    @Override
    public ItemStack extractItem(int slot, int amount, boolean simulate) {
        return find(slot, (h, s) -> h.extractItem(s, amount, simulate)).orElse(ItemStack.EMPTY);
    }

    @Override
    public int getSlotLimit(int slot) {
        return find(slot, IItemHandler::getSlotLimit).orElse(0);
    }

    @Override
    public boolean isItemValid(int slot, @NotNull ItemStack stack) {
        return find(slot, (h, s) -> h.isItemValid(s, stack)).orElse(false);
    }

    private <T> Optional<T> find(int slot, Map<? extends T> map) {
        for (Handler handler : all) {
            if (handler.containsSlot(slot)) {
                return Optional.of(map.map(handler.handler, handler.realSlot(slot)));
            }
        }
        return Optional.empty();
    }

    record Handler(IItemHandler handler, int begin, int end) {

        boolean containsSlot(int index) {
            return index >= begin && index < end;
        }

        int realSlot(int slot) {
            return slot - begin;
        }
    }

    interface Map<T> {
        T map(IItemHandler handler, int slot);
    }
}
