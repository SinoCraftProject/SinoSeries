package games.moegirl.sinocraft.sinocore.old.utility;

import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.Slot;

/**
 * Helper for adding slot to menu.
 * @author qyl27
 */
public class SlotHelper {
    public static final int SLOT_SIZE_BY_PIXEL = 18;

    /**
     * Show players' inventory in menu.
     *
     * @param menu Menu to add slot.
     * @param inventory Inventory source.
     * @param startX Start X location in screen.
     * @param startY Start Y location in screen.
     * @param deltaX Delta X to startX.
     * @param deltaY Delta Y to startY.
     */
    public static void addPlayerInventory(AbstractContainerMenu menu, Inventory inventory,
                                          int startX, int startY, int deltaX, int deltaY) {
        addInventorySquareByRow(menu, inventory, 9, 27, 9, startX, startY, deltaX, deltaY);
        addSlotLine(menu, inventory, 0, 9, startX, startY + 58, deltaX, 0);
    }

    /**
     * Add slots as a square by column.
     * Left to right.
     *
     * @param menu Menu to add slot.
     * @param inventory Inventory source.
     * @param startIndex Start index of inv.
     * @param amount Amount of a line.
     * @param height Row count(height).
     * @param startX Start X location in screen.
     * @param startY Start Y location in screen.
     * @param deltaX Delta X to startX.
     * @param deltaY Delta Y to startY.
     */
    public static void addInventorySquareByColumn(AbstractContainerMenu menu, Container inventory,
                                               int startIndex, int amount, int height,
                                               int startX, int startY, int deltaX, int deltaY) {
        int width = amount / height;
        int remainder = amount % height;
        int index = startIndex;
        int x = startX;
        int y = startY;

        for (int i = 0; i < width; i++) {
            addSlotLine(menu, inventory, index, width, x, y, 0, deltaY);
            index += height;
            x += deltaX;
        }

        if (remainder > 0) {
            addSlotLine(menu, inventory, index, remainder, x, y, deltaX, 0);
        }
    }


    /**
     * Add slots as a square by row.
     * Up to bottom.
     *
     * @param menu Menu to add slot.
     * @param inventory Inventory source.
     * @param startIndex Start index of inv.
     * @param amount Amount of a line.
     * @param width Column count(width).
     * @param startX Start X location in screen.
     * @param startY Start Y location in screen.
     * @param deltaX Delta X to startX.
     * @param deltaY Delta Y to startY.
     */
    public static void addInventorySquareByRow(AbstractContainerMenu menu, Container inventory,
                                               int startIndex, int amount, int width,
                                               int startX, int startY, int deltaX, int deltaY) {
        int height = amount / width;
        int remainder = amount % width;
        int index = startIndex;
        int x = startX;
        int y = startY;

        for (int i = 0; i < height; i++) {
            addSlotLine(menu, inventory, index, width, x, y, deltaX, 0);
            index += width;
            y += deltaY;
        }

        if (remainder > 0) {
            addSlotLine(menu, inventory, index, remainder, x, y, deltaX, 0);
        }
    }

    /**
     * Add slots as a line.
     *
     * @param menu Menu to add slot.
     * @param inventory Inventory source.
     * @param startIndex Start index of inv.
     * @param amount Amount of a line.
     * @param startX Start X location in screen.
     * @param startY Start Y location in screen.
     * @param deltaX Delta X to startX.
     * @param deltaY Delta Y to startY.
     */
    public static void addSlotLine(AbstractContainerMenu menu, Container inventory, int startIndex, int amount,
                                   int startX, int startY, int deltaX, int deltaY) {
        int x = startX;
        int y = startY;
        for (int i = 0; i < amount; i++) {
            addSlotToContainer(menu, new Slot(inventory, startIndex + i, x, y));
            x += deltaX;
            y += deltaY;
        }
    }

    public static void addSlotToContainer(AbstractContainerMenu menu, Slot slot) {
        menu.addSlot(slot);
    }
}
