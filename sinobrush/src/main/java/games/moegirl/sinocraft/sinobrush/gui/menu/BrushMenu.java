package games.moegirl.sinocraft.sinobrush.gui.menu;

import games.moegirl.sinocraft.sinobrush.gui.SBRMenu;
import games.moegirl.sinocraft.sinobrush.item.SBRItems;
import games.moegirl.sinocraft.sinocore.gui.AbstractWidgetMenu;
import games.moegirl.sinocraft.sinocore.gui.widgets.SlotStrategy;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class BrushMenu extends AbstractWidgetMenu {

    public static final int INK_SLOT = 0;
    public static final int PAPER_SLOT = 1;
    public static final int DRAW_SLOT = 2;

    public final SimpleContainer container = new SimpleContainer(3);

    public final int brushSlotId;

    public BrushMenu(int id, Inventory inventory, int slotId) {
        super(SBRMenu.BRUSH_PAPER.get(), id, ResourceLocation.fromNamespaceAndPath("sinobrush", "textures/gui/brush"));

        // qyl27: notice! index of quick slot in player inventory is 0 ~ 8, so it should 0 ~ 8 in container also.
        brushSlotId = slotId;
        addSlotsWithSlotBlocked(inventory, "slot_items", 0, SlotStrategy.simple(), List.of(brushSlotId));
        addSlots(inventory, "slots_inventory", 9, SlotStrategy.simple());
        addSlot(container, "slot_ink", INK_SLOT, SlotStrategy.insertFilter(SBRItems.INK_BOTTLE));
        addSlot(container, "slot_paper", PAPER_SLOT, SlotStrategy.insertFilter(SBRItems.XUAN_PAPER));
        addSlot(container, "slot_drawing", DRAW_SLOT, SlotStrategy.onlyTake());
    }

    public BrushMenu(int id, Inventory inventory, FriendlyByteBuf buf) {
        this(id, inventory, buf.readVarInt());
    }

    public boolean isDrawable() {
        return !container.getItem(INK_SLOT).isEmpty() && !container.getItem(PAPER_SLOT).isEmpty();
    }

    @Override
    public @NotNull ItemStack quickMoveStack(Player player, int index) {
        var slot = slots.get(index);
        var stack = slot.getItem();
        var changed = false;

        if (index >= 0 && index < 36) {
            if (stack.is(SBRItems.INK_BOTTLE.get())) {
                changed = insertItemToContainer(stack, INK_SLOT);
            } else if (stack.is(SBRItems.XUAN_PAPER.get())) {
                changed = insertItemToContainer(stack, PAPER_SLOT);
            }
        } else if (index >= 36 && index < 39) {
            changed = moveItemStackTo(stack, 0, 36, false);
        }

        if (changed) {
            slot.setChanged();
            slot.onTake(player, stack);
        }

        if (index >= 0 && index < 36 && !changed) {
            // qyl27: Fix for something stu**d brute in vanilla.
            return ItemStack.EMPTY;
        }

        return stack;
    }

    private boolean insertItemToContainer(ItemStack stack, int slot) {
        ItemStack exist = container.getItem(slot);

        if (exist.isEmpty()) {
            container.setItem(slot, stack.copy());
            stack.setCount(0);
            return true;
        }

        if (ItemStack.isSameItemSameComponents(exist, stack)) {
            var sum = exist.getCount() + stack.getCount();

            if (sum > exist.getItem().getDefaultMaxStackSize()) {
                var moved = sum - exist.getItem().getDefaultMaxStackSize();
                exist.grow(moved);
                stack.shrink(moved);
            } else {
                exist.grow(stack.getCount());
                stack.setCount(0);
            }

            return true;
        }

        return false;
    }

    @Override
    public boolean stillValid(Player player) {
        return player.isAlive() && player.getInventory().getItem(brushSlotId).is(SBRItems.BRUSH.get());
    }

    @Override
    public void removed(Player player) {
        super.removed(player);

        if (!player.level().isClientSide()) {
            this.clearContainer(player, container);
        }
    }
}
