package games.moegirl.sinocraft.sinocore.world.gen;

import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.valueproviders.ConstantInt;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.TreeConfiguration;
import net.minecraft.world.level.levelgen.feature.featuresize.TwoLayersFeatureSize;
import net.minecraft.world.level.levelgen.feature.foliageplacers.BlobFoliagePlacer;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.minecraft.world.level.levelgen.feature.trunkplacers.StraightTrunkPlacer;

public class ModConfiguredFeatures {

    public static ResourceKey<ConfiguredFeature<?, ?>> registerKey(ResourceLocation name) {
        return ResourceKey.create(Registries.CONFIGURED_FEATURE, name);
    }

    protected static <FC extends FeatureConfiguration, F extends Feature<FC>>
    void register(BootstapContext<ConfiguredFeature<?, ?>> context,
                  ResourceKey<ConfiguredFeature<?, ?>> key, F feature, FC configuration) {
        context.register(key, new ConfiguredFeature<>(feature, configuration));
    }

    public static void registerTree(BootstapContext<ConfiguredFeature<?, ?>> context,
                                       ResourceKey<ConfiguredFeature<?, ?>> key, TreeConfiguration configuration) {
        register(context, key, Feature.TREE, configuration);
    }

    public static TreeConfiguration defaultTree(Block trunk, Block leaves) {
        return tree(trunk, leaves, 4, 2, 0, 2).ignoreVines().build();
    }

    public static TreeConfiguration.TreeConfigurationBuilder tree(Block trunk, Block leaves,
                                                                  int baseHeight, int heightRandA, int heightRandB,
                                                                  int foliagePlacerIntA) {
        return new TreeConfiguration.TreeConfigurationBuilder(BlockStateProvider.simple(trunk),
                new StraightTrunkPlacer(baseHeight, heightRandA, heightRandB),
                BlockStateProvider.simple(leaves),
                new BlobFoliagePlacer(ConstantInt.of(foliagePlacerIntA), ConstantInt.of(0), 3),
                new TwoLayersFeatureSize(1, 0, 1));
    }
}
