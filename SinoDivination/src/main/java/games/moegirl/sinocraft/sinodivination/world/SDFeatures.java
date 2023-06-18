package games.moegirl.sinocraft.sinodivination.world;

import net.minecraft.data.worldgen.features.FeatureUtils;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;

import static games.moegirl.sinocraft.sinodivination.SinoDivination.MODID;

/**
 * 世界生成相关
 *
 * @author luqin2007
 */
public class SDFeatures {

    public static class Features {
        public static final ResourceKey<ConfiguredFeature<?, ?>> RICE = FeatureUtils.createKey(MODID + ":rice");
        public static final ResourceKey<ConfiguredFeature<?, ?>> REHMANNIA = FeatureUtils.createKey(MODID + ":rehmannia");
        public static final ResourceKey<ConfiguredFeature<?, ?>> DRAGONLIVER_MELON = FeatureUtils.createKey(MODID + ":dragonliver_melon");
    }

    public static class Placements {
        public static final ResourceKey<PlacedFeature> RICE = PlacementUtils.createKey(MODID + ":rice");
        public static final ResourceKey<PlacedFeature> REHMANNIA = PlacementUtils.createKey(MODID + ":rehmannia");
        public static final ResourceKey<PlacedFeature> DRAGONLIVER_MELON = PlacementUtils.createKey(MODID + ":dragonliver_melon");
    }
}
