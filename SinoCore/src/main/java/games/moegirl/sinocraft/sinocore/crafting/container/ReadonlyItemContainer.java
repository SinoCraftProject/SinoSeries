package games.moegirl.sinocraft.sinocore.crafting.container;

import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

/**
 * 只读的物品容器，常用于合成表查询
 *
 * @author luqin2007
 */
public interface ReadonlyItemContainer extends Container {

    // ban =============================================================================================================

    @Override
    default ItemStack removeItem(int index, int count) {
        return ItemStack.EMPTY;
    }

    @Override
    default ItemStack removeItemNoUpdate(int index) {
        return ItemStack.EMPTY;
    }

    @Override
    default void setItem(int index, ItemStack stack) {
    }

    @Override
    default void setChanged() {
    }

    @Override
    default boolean stillValid(Player player) {
        return true;
    }

    @Override
    default boolean canPlaceItem(int index, ItemStack stack) {
        return false;
    }

    @Override
    default void clearContent() {
    }

    /**
     * 空物品容器
     *
     * @author luqin2007
     */
    interface EmptyContainer extends ReadonlyItemContainer {

        @Override
        default int getContainerSize() {
            return 0;
        }

        @Override
        default boolean isEmpty() {
            return true;
        }

        @Override
        default ItemStack getItem(int slot) {
            return ItemStack.EMPTY;
        }
    }
}
