package games.moegirl.sinocraft.sinofoundation.block.plant;

import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;

public enum PlantType {
    WHITE_RADISH("white_radish", 1, StageProperty.STAGE_0_3, 0, 2),
    SUMMER_RADISH("summer_radish", 1, StageProperty.STAGE_0_3, 0, 2),
    GREEN_RADISH("green_radish", 1, StageProperty.STAGE_0_3, 0, 2),
    CHILI_PEPPER("chili_pepper", 1, StageProperty.STAGE_0_7, 2, 5),
    GREEN_PEPPER("green_pepper", 1, StageProperty.STAGE_0_7, 2, 5),
    EGGPLANT("eggplant", 1, StageProperty.STAGE_0_7, 2, 5),
    CABBAGE("cabbage", 1, StageProperty.STAGE_0_3, 0, 2),
    RICE("rice", 2, StageProperty.STAGE_0_7, 2, 5),
    MILLET("millet", 1, StageProperty.STAGE_0_7, 2, 5),
    SOYBEAN("soybean", 1, StageProperty.STAGE_0_3, 0, 2),
    SORGHUM("sorghum", 2, StageProperty.STAGE_0_3, 0, 2),
    GARLIC("garlic", 1, StageProperty.STAGE_0_3, 0, 2),
    ;

    private final String name;
    private final int maxHeightCount;
    private final StageProperty stage;
    private final int bonemealBonusMin;
    private final int bonemealBonusMax;

    PlantType(String name, int maxHeightCount, StageProperty stage,
              int bonemealBonusMin, int bonemealBonusMax) {
        this.name = name;
        this.maxHeightCount = maxHeightCount;
        this.stage = stage;
        this.bonemealBonusMin = bonemealBonusMin;
        this.bonemealBonusMax = bonemealBonusMax;
    }

    public String getName() {
        return name;
    }

    public int getMaxHeightCount() {
        return maxHeightCount;
    }

    public StageProperty getStageProperty() {
        return stage;
    }

    public int randomGrowBonus(RandomSource random) {
        return Mth.nextInt(random, bonemealBonusMin, bonemealBonusMax);
    }
}
