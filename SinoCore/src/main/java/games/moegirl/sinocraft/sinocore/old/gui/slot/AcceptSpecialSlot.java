package games.moegirl.sinocraft.sinocore.old.gui.slot;

import net.minecraft.tags.TagKey;
import net.minecraft.world.Container;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;
import org.jetbrains.annotations.NotNull;

/**
 * Accept special slot.
 * @author qyl27
 */
public class AcceptSpecialSlot extends Slot {
    protected TagKey<Item> tagKey = null;
    protected ItemLike item = null;

    /**
     * Constructor of this class.
     * @param container The inventory of the menu. It named IInventory in MCP.
     * @param index Slot in inventory index.
     * @param x X location in screen of the slot. (From left.)
     * @param y Y location in screen of the slot. (From up.)
     * @param itemIn The item will accept.
     */
    public AcceptSpecialSlot(Container container, int index, int x, int y, ItemLike itemIn) {
        super(container, index, x, y);

        item = itemIn;
    }

    /**
     * Constructor of this class.
     * @param container The inventory of the menu. It named IInventory in MCP.
     * @param index Slot in inventory index.
     * @param x X location in screen of the slot. (From left.)
     * @param y Y location in screen of the slot. (From up.)
     * @param tag The tag will accept.
     */
    public AcceptSpecialSlot(Container container, int index, int x, int y, TagKey<Item> tag) {
        super(container, index, x, y);

        tagKey = tag;
    }


    @Override
    public boolean mayPlace(@NotNull ItemStack stack) {
        if (tagKey == null) {
            return stack.is(item.asItem());
        } else {
            return stack.is(tagKey);
        }
    }
}
