package games.moegirl.sinocraft.sinofoundation.block.plant;

import net.minecraft.world.level.block.state.properties.IntegerProperty;

/**
 * @deprecated 与 {@link games.moegirl.sinocraft.sinocore.block.Crop#getAgeProperties(int)} 重复
 */
@Deprecated(forRemoval = true)
public class StageProperty extends IntegerProperty {
    public static final StageProperty STAGE_0_7 = new StageProperty("stage", 0, 7);
    public static final StageProperty STAGE_0_3 = new StageProperty("stage", 0, 3);

    protected final int min;
    protected final int max;

    public StageProperty(String string, int min, int max) {
        super(string, min, max);
        this.min = min;
        this.max = max;
    }

    public int getMax() {
        return max;
    }

    public int getMin() {
        return min;
    }
}
