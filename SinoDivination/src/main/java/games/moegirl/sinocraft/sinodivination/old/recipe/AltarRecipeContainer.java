package games.moegirl.sinocraft.sinodivination.old.recipe;

import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

import java.util.Set;

public interface AltarRecipeContainer extends Container {

    Item sacrificialVessel(int i);

    ItemStack base();

    @Override
    default void clearContent() {
    }

    @Override
    default int getContainerSize() {
        return 5;
    }

    @Override
    default boolean isEmpty() {
        return true;
    }

    @Override
    default ItemStack getItem(int pIndex) {
        return new ItemStack(sacrificialVessel(pIndex));
    }

    @Override
    default ItemStack removeItem(int pIndex, int pCount) {
        return ItemStack.EMPTY;
    }

    @Override
    default ItemStack removeItemNoUpdate(int pIndex) {
        return ItemStack.EMPTY;
    }

    @Override
    default void setItem(int pIndex, ItemStack pStack) {
    }

    @Override
    default int getMaxStackSize() {
        return 64;
    }

    @Override
    default void setChanged() {
    }

    @Override
    default boolean stillValid(Player pPlayer) {
        return true;
    }

    @Override
    default void startOpen(Player pPlayer) {
    }

    @Override
    default void stopOpen(Player pPlayer) {
    }

    @Override
    default boolean canPlaceItem(int pIndex, ItemStack pStack) {
        return false;
    }

    @Override
    default int countItem(Item pItem) {
        return 0;
    }

    @Override
    default boolean hasAnyOf(Set<Item> pSet) {
        return false;
    }

    static AltarRecipeContainer of(Item[] sacrificialVessels, ItemStack base) {
        return new AltarRecipeContainer() {
            final ItemStack t = base.copy();

            @Override
            public Item sacrificialVessel(int i) {
                return sacrificialVessels[i];
            }

            @Override
            public ItemStack base() {
                return t;
            }
        };
    }
}
