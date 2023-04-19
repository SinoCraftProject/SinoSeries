package games.moegirl.sinocraft.sinocore.gui.menu.slot;

import net.minecraft.world.Container;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;

/**
 * Take only slot.
 * @author qyl27
 */
public class TakeOnlySlot extends Slot {
    /**
     * Constructor of this class.
     * @param container The inventory of the menu. It named IInventory in MCP.
     * @param index Slot in inventory index.
     * @param x X location in screen of the slot. (From left.)
     * @param y Y location in screen of the slot. (From up.)
     */
    public TakeOnlySlot(Container container, int index, int x, int y) {
        super(container, index, x, y);
    }

    @Override
    public boolean mayPlace(@NotNull ItemStack stack) {
        return false;
    }
}
