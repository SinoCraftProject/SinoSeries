package games.moegirl.sinocraft.sinofoundation.capability.block.heatsource;

import net.minecraft.nbt.CompoundTag;
import net.minecraftforge.common.util.INBTSerializable;

/**
 * Heat source block. It can warm up or cool down tick by tick.
 * @author qyl27
 */
public interface IHeatSource extends INBTSerializable<CompoundTag> {
    int getHeatValue();
    void setHeatValue(int heat);
    void resetHeatValue();

    /**
     * HeatValue += 1.
     */
    void warmUp();

    /**
     * HeatValue -= 1.
     */
    void coolDown();

    int getTargetHeatValue();
    boolean isWarmingUp();
    boolean isCoolingDown();


    /**
     * Warm up or cool down a heat difference.
     * @param heatDifference Heat difference
     */
    void changeHeat(int heatDifference);

    /**
     * Warm up or cool down to a heat value
     * @param heat Heat value
     */
    void changeHeatTo(int heat);
}
