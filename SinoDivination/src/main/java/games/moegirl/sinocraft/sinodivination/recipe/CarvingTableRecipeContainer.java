package games.moegirl.sinocraft.sinodivination.recipe;

import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;

import java.util.Arrays;
import java.util.function.Consumer;

public class CarvingTableRecipeContainer implements Container {

    private final ItemStack[][] inputs = new ItemStack[][]{
            new ItemStack[]{ItemStack.EMPTY, ItemStack.EMPTY, ItemStack.EMPTY, ItemStack.EMPTY},
            new ItemStack[]{ItemStack.EMPTY, ItemStack.EMPTY, ItemStack.EMPTY, ItemStack.EMPTY},
            new ItemStack[]{ItemStack.EMPTY, ItemStack.EMPTY, ItemStack.EMPTY, ItemStack.EMPTY},
            new ItemStack[]{ItemStack.EMPTY, ItemStack.EMPTY, ItemStack.EMPTY, ItemStack.EMPTY}};
    private ItemStack output = ItemStack.EMPTY;
    private ItemStack dye = ItemStack.EMPTY;

    private final AbstractContainerMenu menu;

    public CarvingTableRecipeContainer(AbstractContainerMenu menu) {
        this.menu = menu;
    }

    @Override
    public int getContainerSize() {
        return 18;
    }

    @Override
    public boolean isEmpty() {
        if (!dye.isEmpty()) {
            return false;
        }
        if (!output.isEmpty()) {
            return false;
        }
        for (ItemStack[] input : inputs) {
            for (ItemStack stack : input) {
                if (!stack.isEmpty()) {
                    return false;
                }
            }
        }
        return true;
    }

    @Override
    public ItemStack getItem(int index) {
        if (index < 16) {
            int row = index / 4;
            int column = index % 4;
            return inputs[row][column];
        } else if (index == 16) {
            return dye;
        } else {
            return output;
        }
    }

    @Override
    public ItemStack removeItem(int index, int count) {
        ItemStack stack;
        if (index < 16) {
            int row = index / 4;
            int column = index % 4;
            stack = shrink(inputs[row][column], count);
            inputs[row][column].shrink(count);
        } else if (index == 16) {
            stack = shrink(dye, count);
            dye.shrink(count);
        } else {
            stack = shrink(output, count);
            output.shrink(count);
        }
        setChanged();
        return stack;
    }

    private ItemStack shrink(ItemStack stack, int count) {
        if (stack.isEmpty()) {
            return ItemStack.EMPTY;
        }
        int c = stack.getCount();
        if (count >= c) {
            return stack.copy();
        } else {
            ItemStack itemStack = stack.copy();
            itemStack.setCount(count);
            return itemStack;
        }
    }

    @Override
    public ItemStack removeItemNoUpdate(int index) {
        ItemStack stack;
        if (index < 16) {
            int row = index / 4;
            int column = index % 4;
            stack = inputs[row][column];
            inputs[row][column] = ItemStack.EMPTY;
        } else if (index == 16) {
            stack = dye;
            dye = ItemStack.EMPTY;
        } else {
            stack = output;
            output = ItemStack.EMPTY;
        }
        return stack;
    }

    @Override
    public void setItem(int index, ItemStack stack) {
        if (index < 16) {
            int row = index / 4;
            int column = index % 4;
            inputs[row][column] = stack.copy();
        } else if (index == 16) {
            dye = stack.copy();
        } else {
            output = stack.copy();
        }
        setChanged();
    }

    @Override
    public void setChanged() {
        menu.slotsChanged(this);
    }

    @Override
    public boolean stillValid(Player player) {
        return true;
    }

    @Override
    public void clearContent() {
        for (ItemStack[] input : inputs) {
            Arrays.fill(input, ItemStack.EMPTY);
        }
        output = ItemStack.EMPTY;
        dye = ItemStack.EMPTY;
    }

    public ItemStack getDye() {
        return dye;
    }

    public void setDye(ItemStack dye) {
        this.dye = dye;
    }

    public ItemStack getOutput() {
        return output;
    }

    public void setOutput(ItemStack output) {
        this.output = output;
    }

    public ItemStack getInput(int row, int column) {
        return inputs[row][column];
    }

    public void forInputs(Consumer<ItemStack> consumer) {
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                if (!inputs[i][j].isEmpty()) {
                    consumer.accept(inputs[i][j]);
                }
            }
        }
        if (!dye.isEmpty()) {
            consumer.accept(dye);
        }
    }
}
