package games.moegirl.sinocraft.sinodivination.old.world;

import games.moegirl.sinocraft.sinocore.old.world.BaseFeatureBuilder;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;

import java.util.function.Supplier;

public class WrappedFeatureBuilder<C extends FeatureConfiguration> extends BaseFeatureBuilder<C, WrappedFeatureBuilder<C>> {

    private final Supplier<C> configuration;

    public WrappedFeatureBuilder(Feature<C> feature, Supplier<C> configuration) {
        super(feature);
        this.configuration = configuration;
    }

    @Override
    public WrappedFeatureBuilder<C> fromConfiguration(C c) {
        return this;
    }

    @Override
    protected C buildConfiguration() {
        return configuration.get();
    }
}
