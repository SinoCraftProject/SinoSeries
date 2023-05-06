package games.moegirl.sinocraft.sinodivination.old.util.container;

import games.moegirl.sinocraft.sinocore.utility.Self;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraft.world.ContainerListener;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.common.util.INBTSerializable;
import net.minecraftforge.items.wrapper.InvWrapper;

public class LimitedContainer<T extends LimitedContainer<T>> extends InvWrapper implements Self<T>, INBTSerializable<ListTag> {

    protected SlotChecker checker = (_1, _2) -> true;
    protected boolean saveEmpty = false;
    protected SimpleContainer container;

    public LimitedContainer(SimpleContainer inv) {
        super(inv);
        container = inv;
    }

    public LimitedContainer(int count) {
        this(new SimpleContainer(count));
    }

    public LimitedContainer(int count, int stackSize) {
        this(new SimpleContainer(count) {
            final int s = Math.min(64, stackSize);

            @Override
            public int getMaxStackSize() {
                return s;
            }
        });
    }

    public T setChecker(SlotChecker checker) {
        this.checker = checker;
        return self();
    }

    public T addChecker(SlotChecker checker) {
        this.checker = SlotChecker.and(checker, this.checker);
        return self();
    }

    public T addChangeListener(ContainerListener listener) {
        container.addListener(listener);
        return self();
    }

    public T bindEntityChange(BlockEntity entity) {
        return addChangeListener(__ -> entity.setChanged());
    }

    public T bindEntityChangeWithUpdate(BlockEntity entity) {
        return addChangeListener(__ -> {
            entity.setChanged();
            Level level = entity.getLevel();
            if (level != null && !level.isClientSide) {
                level.setBlockAndUpdate(entity.getBlockPos(), entity.getBlockState());
            }
        });
    }

    public T saveEmpty() {
        saveEmpty = true;
        return self();
    }

    @Override
    public ItemStack insertItem(int slot, ItemStack stack, boolean simulate) {
        return isItemValid(slot, stack) ? super.insertItem(slot, stack, simulate) : stack;
    }

    @Override
    public boolean isItemValid(int slot, ItemStack stack) {
        return checker.check(slot, stack) && super.isItemValid(slot, stack);
    }

    public ItemStack insertItem2(int slot, ItemStack stack, boolean simulate) {
        return super.insertItem(slot, stack, simulate);
    }

    public ItemStack extractItem2(int slot, int amount, boolean simulate) {
        return super.extractItem(slot, amount, simulate);
    }

    public boolean isItemValid2(int slot, ItemStack stack) {
        return super.isItemValid(slot, stack);
    }

    public ItemStack setStackInSlot2(int slot, ItemStack stack) {
        ItemStack s = getStackInSlot(slot);
        if (!ItemStack.isSameItemSameTags(s, stack)) {
            super.setStackInSlot(slot, stack);
            notifyInvChanged();
        }
        return s;
    }

    public void shrink(int slot, int count) {
        getStackInSlot(slot).shrink(count);
        notifyInvChanged();
    }

    @Override
    public ListTag serializeNBT() {
        ListTag list = new ListTag();
        for (int i = 0; i < getSlots(); i++) {
            ItemStack stack = getStackInSlot(i);
            if (stack.isEmpty() && !saveEmpty) {
                continue;
            }
            CompoundTag nbt = new CompoundTag();
            nbt.putByte("Slot", (byte)i);
            stack.save(nbt);
            list.add(nbt);
        }
        return list;
    }

    @Override
    public void deserializeNBT(ListTag nbt) {
        container.clearContent();
        for (Tag tag : nbt) {
            CompoundTag t = (CompoundTag) tag;
            int slot = t.getByte("Slot");
            setStackInSlot2(slot, ItemStack.of(t));
        }
    }

    public void save(CompoundTag tag, String name) {
        tag.put(name, serializeNBT());
    }

    public void load(CompoundTag nbt, String name) {
        deserializeNBT(nbt.getList(name, Tag.TAG_COMPOUND));
    }

    public void notifyInvChanged() {
        container.setChanged();
    }
}