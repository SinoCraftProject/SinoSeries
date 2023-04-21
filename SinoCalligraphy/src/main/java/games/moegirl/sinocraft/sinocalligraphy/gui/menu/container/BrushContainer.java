package games.moegirl.sinocraft.sinocalligraphy.gui.menu.container;

import games.moegirl.sinocraft.sinocalligraphy.data.SCAItemTags;
import games.moegirl.sinocraft.sinocalligraphy.gui.menu.BrushMenu;
import games.moegirl.sinocraft.sinocalligraphy.item.SCAItems;
import games.moegirl.sinocraft.sinocore.SinoCore;
import net.minecraft.core.NonNullList;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.Container;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;

public class BrushContainer implements Container {
    public static final int CONTAINER_SIZE = 3;

    public static final int XUAN_PAPER_SLOT = 0;
    public static final int INK_SLOT = 1;
    public static final int FILLED_XUAN_PAPER_SLOT = 2;

    public BrushMenu menu;

    public NonNullList<ItemStack> items = NonNullList.withSize(CONTAINER_SIZE, ItemStack.EMPTY);

    public BrushContainer(BrushMenu menu) {
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
    public @NotNull ItemStack getItem(int slot) {
        return items.get(slot);
    }

    @Override
    public @NotNull ItemStack removeItem(int slot, int amount) {
        return ContainerHelper.removeItem(items, slot, amount);
    }

    @Override
    public @NotNull ItemStack removeItemNoUpdate(int slot) {
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
        // qyl27: delegated to screen?
    }

    @Override
    public boolean stillValid(Player player) {
        return player.getMainHandItem().is(SCAItemTags.BRUSHES)
                || player.getOffhandItem().is(SCAItemTags.BRUSHES);
    }

    @Override
    public void clearContent() {
        items.clear();
    }

    public ItemStack getPaper() {
        return getItem(XUAN_PAPER_SLOT);
    }

    public ItemStack getInk() {
        return getItem(INK_SLOT);
    }

    public boolean hasPaper() {
        return !getPaper().isEmpty();
    }

    public boolean hasInk() {
        return !getInk().isEmpty();
    }

    public boolean isPaper(ItemStack stack) {
        return stack.is(SCAItemTags.PAPERS);
    }

    public boolean isInk(ItemStack stack) {
        return stack.is(SCAItemTags.INKS);
    }

    public void setResult(ItemStack stack) {
        setItem(FILLED_XUAN_PAPER_SLOT, stack);
    }

    public void dropAll(Player player) {
        if (!player.isAlive()
                || (player instanceof ServerPlayer && ((ServerPlayer) player).hasDisconnected())) {
            for (int j = 0; j < CONTAINER_SIZE - 1; ++j) {  // qyl27: No drop output slot.
                player.drop(removeItemNoUpdate(j), false);
            }
        } else {
            for (int i = 0; i < CONTAINER_SIZE - 1; ++i) {  // qyl27: No drop output slot.
                player.getInventory().placeItemBackInInventory(removeItemNoUpdate(i));
            }
        }
    }
}
