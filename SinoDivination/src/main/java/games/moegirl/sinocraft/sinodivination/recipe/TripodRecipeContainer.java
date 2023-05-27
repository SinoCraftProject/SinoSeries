package games.moegirl.sinocraft.sinodivination.recipe;

import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

import java.util.Set;

public class TripodRecipeContainer implements Container {

    private final Item[] sacrificialvessels;
    private final ItemStack base;

    public TripodRecipeContainer(Item[] sacrificialvessels, ItemStack base) {
        this.sacrificialvessels = sacrificialvessels;
        this.base = base.copy();
    }

    public Item sacrificialVessel(int i) {
        return sacrificialvessels[i];
    }

    public ItemStack base() {
        return base;
    }

    @Override
    public int getContainerSize() {
        return 5;
    }

    // 以下方法无用 ======================================================================================================

    @Override
    public void clearContent() {
    }

    @Override
    public boolean isEmpty() {
        return true;
    }

    @Override
    public ItemStack getItem(int slot) {
        return new ItemStack(sacrificialVessel(slot));
    }

    @Override
    public ItemStack removeItem(int slot, int amount) {
        return ItemStack.EMPTY;
    }

    @Override
    public ItemStack removeItemNoUpdate(int slot) {
        return ItemStack.EMPTY;
    }

    @Override
    public void setItem(int slot, ItemStack itemStack) {
    }

    @Override
    public int getMaxStackSize() {
        return 64;
    }

    @Override
    public void setChanged() {
    }

    @Override
    public boolean stillValid(Player player) {
        return true;
    }

    @Override
    public void startOpen(Player player) {
    }

    @Override
    public void stopOpen(Player player) {
    }

    @Override
    public boolean canPlaceItem(int index, ItemStack itemStack) {
        return false;
    }

    @Override
    public int countItem(Item item) {
        return 0;
    }

    @Override
    public boolean hasAnyOf(Set<Item> items) {
        return false;
    }
}
