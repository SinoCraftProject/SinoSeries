package games.moegirl.sinocraft.sinofoundation.capability.block.combustible;

import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.util.INBTSerializable;

/**
 * It contains a fuel to burn and an ash to take. Usually use with IHeatSource.
 * @author qyl27
 */
public interface ICombustible extends INBTSerializable<CompoundTag> {
    boolean isBurning();
    int getBurnTime();
    void setBurnTime(int time);
    void resetBurnTime();
    void addBurnTime(int time);
    void subBurnTime(int time);

    void setBurningFuel(ItemStack fuel);
    boolean hasBurningFuel();
    ItemStack getBurningFuel();

    void addAsh(ItemStack ash);
    void setAsh(int index, ItemStack ash);

    /**
     * Take the first stack of ashes.
     * @return Ash
     */
    ItemStack takeAsh();

    NonNullList<ItemStack> takeAshes();

    int getAshesCapacity();
    void setAshesCapacity(int capacity);

    /**
     * Check if the block is full of ashes.
     * @return IsFullOfAshes
     */
    boolean isFullOfAshes();
    void clearAshes();
}
