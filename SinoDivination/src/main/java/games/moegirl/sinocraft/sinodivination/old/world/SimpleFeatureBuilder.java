package games.moegirl.sinocraft.sinodivination.old.world;

import games.moegirl.sinocraft.sinocore.old.world.BaseFeatureBuilder;
import net.minecraft.util.random.SimpleWeightedRandomList;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.SimpleBlockConfiguration;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.minecraft.world.level.levelgen.feature.stateproviders.SimpleStateProvider;
import net.minecraft.world.level.levelgen.feature.stateproviders.WeightedStateProvider;

import java.util.List;
import java.util.function.Consumer;
import java.util.function.Supplier;

/**
 * A feature to place a simple block
 */
public class SimpleFeatureBuilder extends BaseFeatureBuilder<SimpleBlockConfiguration, SimpleFeatureBuilder> {

    private BlockStateProvider provider;

    public SimpleFeatureBuilder() {
        super(Feature.SIMPLE_BLOCK);
    }

    public SimpleFeatureBuilder block(BlockStateProvider provider) {
        this.provider = provider;
        return this;
    }

    public SimpleFeatureBuilder block(BlockState state) {
        return block(SimpleStateProvider.simple(state));
    }

    public SimpleFeatureBuilder block(Block block) {
        return block(SimpleStateProvider.simple(block));
    }

    public SimpleFeatureBuilder block(Supplier<? extends Block> block) {
        return block(SimpleStateProvider.simple(block.get()));
    }

    public SimpleFeatureBuilder randomized(Consumer<SimpleWeightedRandomList.Builder<BlockState>> builder) {
        SimpleWeightedRandomList.Builder<BlockState> b = new SimpleWeightedRandomList.Builder<>();
        builder.accept(b);
        return block(new WeightedStateProvider(b));
    }

    public SimpleFeatureBuilder randomized(List<BlockState> blocks) {
        return randomized(builder -> blocks.forEach(bs -> builder.add(bs, 1)));
    }

    @Override
    public SimpleFeatureBuilder fromConfiguration(SimpleBlockConfiguration parent) {
        provider = parent.toPlace();
        return this;
    }

    @Override
    protected SimpleBlockConfiguration buildConfiguration() {
        return new SimpleBlockConfiguration(provider);
    }
}
