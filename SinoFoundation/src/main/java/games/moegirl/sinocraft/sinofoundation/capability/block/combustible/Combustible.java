package games.moegirl.sinocraft.sinofoundation.capability.block.combustible;

import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.item.ItemStack;

public class Combustible implements ICombustible {

    public static final String TAG_BURN_TIME_NAME = "burnTime";
    public static final String TAG_BURN_FUEL_NAME = "fuel";
    public static final String TAG_ASHES_CAPACITY_NAME = "ashesCapacity";
    public static final String TAG_ASHES_NAME = "ashes";

    private int burnTime = 0;
    private ItemStack burnFuel = ItemStack.EMPTY;
    private int ashesCapacity = 0;
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
            ashes.add(ash);
        } else {
            throw new IllegalStateException("There are full of ashes. Amount: " + ashes.size());
        }
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
        return ashes.size() >= getAshesCapacity();
    }

    @Override
    public void clearAshes() {
        ashes.clear();
    }

    @Override
    public CompoundTag serializeNBT() {
        var tag = new CompoundTag();
        tag.putInt(TAG_BURN_TIME_NAME, burnTime);
        tag.put(TAG_BURN_FUEL_NAME, burnFuel.serializeNBT());
        tag.putInt(TAG_ASHES_CAPACITY_NAME, ashesCapacity);

        var ashesTag = ContainerHelper.saveAllItems(new CompoundTag(), ashes);
        tag.put(TAG_ASHES_NAME, ashesTag);

        return tag;
    }

    @Override
    public void deserializeNBT(CompoundTag tag) {
        burnTime = tag.getInt(TAG_BURN_TIME_NAME);
        burnFuel.deserializeNBT(tag.getCompound(TAG_BURN_FUEL_NAME));
        ashesCapacity = tag.getInt(TAG_ASHES_CAPACITY_NAME);

        ContainerHelper.loadAllItems(tag.getCompound(TAG_ASHES_NAME), ashes);
    }
}
