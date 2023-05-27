package games.moegirl.sinocraft.sinodivination.util.container;

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
import org.jetbrains.annotations.NotNull;

/**
 * 对修改有限制的 Container 包装类
 *
 * @param <T> 实际类型
 */
public class LimitedContainer<T extends LimitedContainer<T>> extends InvWrapper implements Self<T>, INBTSerializable<ListTag> {

    // 输入检查
    protected SlotChecker checker = SlotChecker.TRUE;
    // 保存为 nbt 时，是否保存空白物品
    protected boolean saveEmpty = false;
    // 内部实际 Container
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

    /**
     * 当物品发生变化时，标记对应 BlockEntity 内容发生变化
     *
     * @param entity BlockEntity
     * @return Self
     */
    public T bindEntityChange(BlockEntity entity) {
        return addChangeListener(__ -> entity.setChanged());
    }

    /**
     * 当物品发生变化时，标记对应 BlockEntity 内容发生变化并通知方块更新
     *
     * @param entity BlockEntity
     * @return Self
     */
    public T bindEntityChangeWithUpdate(BlockEntity entity) {
        return addChangeListener(__ -> {
            entity.setChanged();
            Level level = entity.getLevel();
            if (level != null && !level.isClientSide) {
                level.setBlockAndUpdate(entity.getBlockPos(), entity.getBlockState());
            }
        });
    }

    /**
     * 允许保存时保存空白物品
     *
     * @return Self
     */
    public T setSaveEmpty() {
        saveEmpty = true;
        return self();
    }

    @Override
    public boolean isItemValid(int slot, ItemStack stack) {
        return checker.check(slot, stack) && super.isItemValid(slot, stack);
    }

//    public boolean isItemValid2(int slot, ItemStack stack) {
//        return super.isItemValid(slot, stack);
//    }

    @Override
    public ItemStack insertItem(int slot, ItemStack stack, boolean simulate) {
        return isItemValid(slot, stack) ? super.insertItem(slot, stack, simulate) : stack;
    }

    public ItemStack insertItem2(int slot, ItemStack stack, boolean simulate) {
        return super.insertItem(slot, stack, simulate);
    }

    public ItemStack extractItem2(int slot, int amount, boolean simulate) {
        return super.extractItem(slot, amount, simulate);
    }

    public ItemStack setStackInSlot2(int slot, ItemStack stack) {
        ItemStack s = getStackInSlot(slot);
        super.setStackInSlot(slot, stack);
        setChanged();
        return s;
    }

    public void shrink(int slot, int count) {
        getStackInSlot(slot).shrink(count);
        setChanged();
    }

    public void setChanged() {
        container.setChanged();
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
            nbt.putByte("Slot", (byte) i);
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
}