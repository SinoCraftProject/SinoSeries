package games.moegirl.sinocraft.sinocalligraphy.gui.menu.container;

import net.minecraft.core.NonNullList;
import net.minecraft.world.Container;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;

public class BrushContainer implements Container {
    public static final int CONTAINER_SIZE = 3;

    public static final int XUAN_PAPER_SLOT = 0;
    public static final int INK_SLOT = 1;
    public static final int FILLED_XUAN_PAPER_SLOT = 2;

    public AbstractContainerMenu menu;

    public NonNullList<ItemStack> items = NonNullList.withSize(CONTAINER_SIZE, ItemStack.EMPTY);

    public BrushContainer(AbstractContainerMenu menu) {
        this.menu = menu;
    }

    @Override
    public int getContainerSize() {
        return CONTAINER_SIZE;
    }

    @Override
    public boolean isEmpty() {
        return items.isEmpty();
    }

    @Override
    public ItemStack getItem(int slot) {
        return items.get(slot);
    }

    @Override
    public ItemStack removeItem(int slot, int amount) {
        return ContainerHelper.removeItem(items, slot, amount);
    }

    @Override
    public ItemStack removeItemNoUpdate(int slot) {
        return ContainerHelper.takeItem(items, slot);
    }

    @Override
    public void setItem(int slot, ItemStack stack) {
        items.set(slot, stack);
    }

    /**
     * Mark dirty.
     */
    @Override
    public void setChanged() {
        menu.broadcastChanges();

        // Todo: qyl27.
    }

    @Override
    public boolean stillValid(Player player) {
        // Todo: qyl27.
        return false;
    }

    @Override
    public void clearContent() {
        items.clear();
    }
}
