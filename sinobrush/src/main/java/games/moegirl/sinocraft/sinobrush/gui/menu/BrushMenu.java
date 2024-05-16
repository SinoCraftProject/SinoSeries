package games.moegirl.sinocraft.sinobrush.gui.menu;

import games.moegirl.sinocraft.sinobrush.gui.SBRMenu;
import games.moegirl.sinocraft.sinobrush.item.SBRItems;
import games.moegirl.sinocraft.sinocore.gui.WidgetMenuBase;
import games.moegirl.sinocraft.sinocore.gui.widgets.SlotStrategy;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.Container;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

public class BrushMenu extends WidgetMenuBase {

    public static final int INK_SLOT = 0;
    public static final int PAPER_SLOT = 1;
    public static final int DRAW_SLOT = 2;

    public final Container inkAndPaperContainer = new SimpleContainer(3);

    private int lastInkSlot = -1;
    private int lastPaperSlot = -1;

    public BrushMenu(int id, Inventory inventory, FriendlyByteBuf buf) {
        super(SBRMenu.BRUSH_PAPER.get(), id, new ResourceLocation("sinobrush", "textures/gui/brush"));

        addSlots(inventory, "slots_inventory", 9, SlotStrategy.simple());
        addSlots(inventory, "slot_items", 0, SlotStrategy.simple());
        addSlot(inkAndPaperContainer, "slot_ink", INK_SLOT, SlotStrategy.insertFilter(SBRItems.INK_BOTTLE));
        addSlot(inkAndPaperContainer, "slot_paper", PAPER_SLOT, SlotStrategy.insertFilter(SBRItems.XUAN_PAPER));
        addSlot(inkAndPaperContainer, "slot_drawing", DRAW_SLOT, SlotStrategy.onlyTake());
    }

    @Override
    public ItemStack quickMoveStack(Player player, int index) {
        ItemStack item = player.getInventory().getItem(index).copy();
        if (item.is(SBRItems.INK_BOTTLE.get())) {
            lastInkSlot = index;
            item = insertItemToContainer(item, INK_SLOT);
        } else if (item.is(SBRItems.XUAN_PAPER.get())) {
            lastPaperSlot = index;
            item = insertItemToContainer(item, PAPER_SLOT);
        }
        return item;
    }

    private ItemStack insertItemToContainer(ItemStack item, int slot) {
        ItemStack exist = inkAndPaperContainer.getItem(slot);
        if (exist.isEmpty()) {
            inkAndPaperContainer.setItem(slot, item);
            return ItemStack.EMPTY;
        } else if (exist.getCount() < exist.getItem().getMaxStackSize() && ItemStack.isSameItemSameTags(exist, item)) {
            int count = Math.min(exist.getItem().getMaxStackSize() - exist.getCount(), item.getCount());
            exist.grow(count);
            item.shrink(count);
            return item;
        } else {
            return item;
        }
    }

    @Override
    public boolean stillValid(Player player) {
        return true;
    }

    @Override
    public void removed(Player player) {
        super.removed(player);
        if (player instanceof ServerPlayer sp && sp.isAlive() && !sp.hasDisconnected()) {
            placeItemBackInInventory(sp, inkAndPaperContainer.getItem(INK_SLOT), lastInkSlot);
            placeItemBackInInventory(sp, inkAndPaperContainer.getItem(PAPER_SLOT), lastPaperSlot);
            placeItemBackInInventory(sp, inkAndPaperContainer.getItem(DRAW_SLOT), -1);
        }
    }

    private void placeItemBackInInventory(ServerPlayer player, ItemStack item, int slot) {
        if (item.isEmpty()) {
            return;
        }
        Inventory inventory = player.getInventory();
        if (slot == -1
                || !inventory.getItem(slot).isEmpty()
                || !inventory.add(slot, item)) {
            inventory.placeItemBackInInventory(item);
        }
    }
}
