package games.moegirl.sinocraft.sinodivination.old.util.container;

import net.minecraft.world.Container;
import net.minecraft.world.ContainerListener;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

import java.util.LinkedList;
import java.util.List;
import java.util.Set;

public class ContainerWrapper implements Container {

    final Container container;
    private final List<ContainerListener> listeners = new LinkedList<>();

    public ContainerWrapper(Container container) {
        this.container = container;
    }

    public void addListener(ContainerListener listener) {
        listeners.add(listener);
    }

    @Override
    public int getContainerSize() {
        return container.getContainerSize();
    }

    @Override
    public boolean isEmpty() {
        return container.isEmpty();
    }

    @Override
    public ItemStack getItem(int pIndex) {
        return container.getItem(pIndex);
    }

    @Override
    public ItemStack removeItem(int pIndex, int pCount) {
        ItemStack stack = container.removeItem(pIndex, pCount);
        if (!stack.isEmpty()) {
            notifyInvChanged();
        }
        return stack;
    }

    @Override
    public ItemStack removeItemNoUpdate(int pIndex) {
        return container.removeItemNoUpdate(pIndex);
    }

    @Override
    public void setItem(int pIndex, ItemStack pStack) {
        container.setItem(pIndex, pStack);
        notifyInvChanged();
    }

    @Override
    public int getMaxStackSize() {
        return container.getMaxStackSize();
    }

    @Override
    public void setChanged() {
        container.setChanged();
    }

    @Override
    public boolean stillValid(Player pPlayer) {
        return container.stillValid(pPlayer);
    }

    @Override
    public void startOpen(Player pPlayer) {
        container.startOpen(pPlayer);
    }

    @Override
    public void stopOpen(Player pPlayer) {
        container.stopOpen(pPlayer);
    }

    @Override
    public boolean canPlaceItem(int pIndex, ItemStack pStack) {
        return container.canPlaceItem(pIndex, pStack);
    }

    @Override
    public int countItem(Item pItem) {
        return container.countItem(pItem);
    }

    @Override
    public boolean hasAnyOf(Set<Item> pSet) {
        return container.hasAnyOf(pSet);
    }

    @Override
    public void clearContent() {
        container.clearContent();
        notifyInvChanged();
    }

    public void notifyInvChanged() {
        for (ContainerListener listener : listeners) {
            listener.containerChanged(container);
        }
    }
}
