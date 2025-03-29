package games.moegirl.sinocraft.sinotest.datagen.gen;

import games.moegirl.sinocraft.sinocore.data.gen.AbstractDatapackBuiltinEntriesProvider;
import games.moegirl.sinocraft.sinocore.data.gen.DataGenContext;
import games.moegirl.sinocraft.sinocore.data.gen.delegate.DatapackProviderDelegateBase;
import net.minecraft.core.Holder;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;

public class TestFeatureProvider extends AbstractDatapackBuiltinEntriesProvider {

    private Holder<ConfiguredFeature<?, ?>> TEST_FEATURE;

    public TestFeatureProvider(DataGenContext context) {
        super(context);
    }

    @Override
    public void generateData(DatapackProviderDelegateBase delegate) {
//        delegate.add(Registries.CONFIGURED_FEATURE, this::configuredFeatures);
//        delegate.add(Registries.PLACED_FEATURE, this::placedFeatures);
    }

//    private void configuredFeatures(BootstapContext<ConfiguredFeature<?, ?>> context) {
//        TEST_FEATURE = context.register(TestKeys.TEST_FEATURE, new ConfiguredFeature<>(Feature.ORE,
//                new OreConfiguration(new RandomBlockMatchTest(Blocks.GRASS_BLOCK, 1),
//                        TestRegistry.TEST_BLOCK.get().defaultBlockState(), 5, 1)));
//    }
//
//    private void placedFeatures(BootstapContext<PlacedFeature> context) {
//        context.register(ResourceKey.create(Registries.PLACED_FEATURE, TestKeys.TEST_FEATURE.location()),
//                new PlacedFeature(TEST_FEATURE, List.of(
//                        CountPlacement.of(5),
//                        BlockPredicateFilter.forPredicate(BlockPredicate.matchesBlocks(Blocks.GRASS_BLOCK)),
//                        BiomeFilter.biome(),
//                        HeightmapPlacement.onHeightmap(Heightmap.Types.WORLD_SURFACE),
//                        InSquarePlacement.spread())));
//    }
}
