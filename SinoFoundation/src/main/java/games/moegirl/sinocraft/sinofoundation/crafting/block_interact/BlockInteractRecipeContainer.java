package games.moegirl.sinocraft.sinofoundation.crafting.block_interact;

import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.BlockState;

/**
 * @author luqin2007
 */
public class BlockInteractRecipeContainer implements Container {

    final ItemStack itemStack;
    final BlockState blockState;

    public BlockInteractRecipeContainer(ItemStack itemStack, BlockState blockState) {
        this.itemStack = itemStack;
        this.blockState = blockState;
    }

    @Override
    public int getContainerSize() {
        return 2;
    }

    @Override
    public boolean isEmpty() {
        return false;
    }

    @Override
    public ItemStack getItem(int slot) {
        return slot == 0 ? itemStack.copy() : new ItemStack(blockState.getBlock());
    }

    // ban =============================================================================================================

    @Override
    public ItemStack removeItem(int index, int count) {
        return ItemStack.EMPTY;
    }

    @Override
    public ItemStack removeItemNoUpdate(int index) {
        return ItemStack.EMPTY;
    }

    @Override
    public void setItem(int index, ItemStack stack) {
    }

    @Override
    public void setChanged() {
    }

    @Override
    public boolean stillValid(Player player) {
        return true;
    }

    @Override
    public boolean canPlaceItem(int index, ItemStack stack) {
        return false;
    }

    @Override
    public void clearContent() {
    }
}
