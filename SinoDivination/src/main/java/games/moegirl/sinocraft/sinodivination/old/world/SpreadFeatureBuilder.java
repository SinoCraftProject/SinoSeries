package games.moegirl.sinocraft.sinodivination.old.world;

import games.moegirl.sinocraft.sinocore.old.world.BaseFeatureBuilder;
import net.minecraft.core.Holder;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.RandomPatchConfiguration;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;

/**
 * A feature to create vegetation, like flower
 */
public class SpreadFeatureBuilder extends BaseFeatureBuilder<RandomPatchConfiguration, SpreadFeatureBuilder> {

    private int tries = 96;
    private int xz = 7, y = 3;
    private Holder<PlacedFeature> supplier;

    public SpreadFeatureBuilder() {
        super(Feature.RANDOM_PATCH);
    }

    /**
     * try times
     *
     * @param tries tries
     * @return this builder
     */
    public SpreadFeatureBuilder tries(int tries) {
        this.tries = tries;
        return this;
    }

    /**
     * spread x, y
     *
     * @param xzSpread x, y
     * @return this builder
     */
    public SpreadFeatureBuilder spreadXZ(int xzSpread) {
        this.xz = xzSpread;
        return this;
    }

    /**
     * spread y
     *
     * @param ySpread y
     * @return this builder
     */
    public SpreadFeatureBuilder spreadY(int ySpread) {
        this.y = ySpread;
        return this;
    }

    /**
     * A feature to generate vegetation
     *
     * @param feature feature
     * @return this builder
     */
    public SpreadFeatureBuilder feature(Holder<PlacedFeature> feature) {
        this.supplier = feature;
        return this;
    }

    /**
     * A feature to generate vegetation
     *
     * @param feature feature
     * @return this builder
     */
    public SpreadFeatureBuilder feature(PlacedFeature feature) {
        this.supplier = Holder.direct(feature);
        return this;
    }

    @Override
    public SpreadFeatureBuilder fromConfiguration(RandomPatchConfiguration parent) {
        tries = parent.tries();
        xz = parent.xzSpread();
        y = parent.ySpread();
        supplier = parent.feature();
        return this;
    }

    @Override
    protected RandomPatchConfiguration buildConfiguration() {
        return new RandomPatchConfiguration(tries, xz, y, supplier);
    }
}
