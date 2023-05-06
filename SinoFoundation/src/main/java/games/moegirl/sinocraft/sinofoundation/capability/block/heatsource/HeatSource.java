package games.moegirl.sinocraft.sinofoundation.capability.block.heatsource;

import net.minecraft.nbt.CompoundTag;

public class HeatSource implements IHeatSource {
    public static final String TAG_HEAT_VALUE_NAME = "heatValue";
    public static final String TAG_TARGET_HEAT_VALUE_NAME = "targetHeatValue";

    private int heatValue = 0;

    private int targetHeatValue = 0;

    @Override
    public int getHeatValue() {
        return heatValue;
    }

    @Override
    public void setHeatValue(int heat) {
        heatValue = heat;
    }

    @Override
    public void resetHeatValue() {
        setHeatValue(0);
        changeHeatTo(0);
    }

    @Override
    public void warmUp() {
        setHeatValue(getHeatValue() + 1);
    }

    @Override
    public void coolDown() {
        setHeatValue(getHeatValue() - 1);
    }

    @Override
    public int getTargetHeatValue() {
        return targetHeatValue;
    }

    @Override
    public boolean isWarmingUp() {
        return targetHeatValue > heatValue;
    }

    @Override
    public boolean isCoolingDown() {
        return targetHeatValue < heatValue;
    }

    @Override
    public void changeHeat(int heatDifference) {
        targetHeatValue = heatValue + heatDifference;
    }

    @Override
    public void changeHeatTo(int heat) {
        targetHeatValue = heat;
    }

    @Override
    public CompoundTag serializeNBT() {
        var tag = new CompoundTag();
        tag.putInt(TAG_HEAT_VALUE_NAME, heatValue);
        tag.putInt(TAG_TARGET_HEAT_VALUE_NAME, targetHeatValue);
        return tag;
    }

    @Override
    public void deserializeNBT(CompoundTag tag) {
        heatValue = tag.getInt(TAG_HEAT_VALUE_NAME);
        targetHeatValue = tag.getInt(TAG_TARGET_HEAT_VALUE_NAME);
    }
}
