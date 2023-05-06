package games.moegirl.sinocraft.sinofoundation.capability.block.combustible;

import games.moegirl.sinocraft.sinocore.utility.ItemStackHelper;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.items.ItemStackHandler;

public class Combustible implements ICombustible {

    public static final String TAG_BURN_TIME_NAME = "burnTime";
    public static final String TAG_BURN_FUEL_NAME = "fuel";
    public static final String TAG_ASHES_CAPACITY_NAME = "ashesCapacity";
    public static final String TAG_ASHES_NAME = "ashes";

    private int burnTime = 0;
    private ItemStack burnFuel = ItemStack.EMPTY;
    private int ashesCapacity = 3;
    private NonNullList<ItemStack> ashes = NonNullList.create();

    @Override
    public boolean isBurning() {
        return getBurnTime() > 0;
    }

    @Override
    public int getBurnTime() {
        return burnTime;
    }

    @Override
    public void setBurnTime(int time) {
        burnTime = time;
    }

    @Override
    public void resetBurnTime() {
        setBurnTime(0);
    }

    @Override
    public void addBurnTime(int time) {
        setBurnTime(getBurnTime() + time);
    }

    @Override
    public void subBurnTime(int time) {
        setBurnTime(getBurnTime() - time);
    }

    @Override
    public void setBurningFuel(ItemStack fuel) {
        burnFuel = fuel;
    }

    @Override
    public boolean hasBurningFuel() {
        return burnFuel.isEmpty();
    }

    @Override
    public ItemStack getBurningFuel() {
        return burnFuel;
    }

    @Override
    public void addAsh(ItemStack ash) {
        if (!isFullOfAshes()) {
            for (var stack : ashes) {
                if (stack.sameItem(ash)) {
                    ash = ItemStackHelper.mergeStack(stack, ash);
                    if (ash.isEmpty()) {
                        break;
                    }
                }
            }
            if (!ash.isEmpty()) {
                ashes.add(ash);
            }
        } else {
            throw new IllegalStateException("There are full of ashes. Amount: " + ashes.size());
        }
    }

    @Override
    public void setAsh(int index, ItemStack ash) {
        ashes.set(index, ash);
    }

    @Override
    public ItemStack takeAsh() {
        if (ashes.isEmpty()) {
            return ItemStack.EMPTY;
        }
        var ash = ashes.get(0);
        ashes.remove(0);
        return ash;
    }

    @Override
    public NonNullList<ItemStack> takeAshes() {
        return NonNullList.of(ItemStack.EMPTY, ashes.toArray(ItemStack[]::new));
    }

    @Override
    public int getAshesCapacity() {
        return ashesCapacity;
    }

    @Override
    public void setAshesCapacity(int capacity) {
        ashesCapacity = capacity;
    }

    @Override
    public boolean isFullOfAshes() {
        if (ashes.size() > getAshesCapacity()) {
            return true;
        } else if (ashes.size() < getAshesCapacity()) {
            return false;
        } else {
            for (var ash : ashes) {
                if (ash.getCount() < ash.getMaxStackSize()) {
                    return false;
                }
            }
            return true;
        }
    }

    @Override
    public void clearAshes() {
        ashes.clear();
    }

    @Override
    public CompoundTag serializeNBT() {
        var tag = new CompoundTag();
        tag.putInt(TAG_BURN_TIME_NAME, burnTime);
        tag.put(TAG_BURN_FUEL_NAME, burnFuel.serializeNBT());   // Fixme: qyl27: it has not been saved.
        tag.putInt(TAG_ASHES_CAPACITY_NAME, ashesCapacity);

        // Fixme: qyl27: it has not been saved.
        var ashesTag = ContainerHelper.saveAllItems(new CompoundTag(), ashes);
        tag.put(TAG_ASHES_NAME, ashesTag);

        return tag;
    }

    @Override
    public void deserializeNBT(CompoundTag tag) {
        burnTime = tag.getInt(TAG_BURN_TIME_NAME);
        burnFuel = ItemStack.of(tag.getCompound(TAG_BURN_FUEL_NAME));
        ashesCapacity = tag.getInt(TAG_ASHES_CAPACITY_NAME);

        ContainerHelper.loadAllItems(tag.getCompound(TAG_ASHES_NAME), ashes);
    }
}
