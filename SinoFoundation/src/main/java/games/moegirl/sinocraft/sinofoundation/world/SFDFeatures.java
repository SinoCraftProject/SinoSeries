package games.moegirl.sinocraft.sinofoundation.world;

import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.data.worldgen.features.FeatureUtils;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;

import static games.moegirl.sinocraft.sinofoundation.SinoFoundation.MODID;

/**
 * @author luqin2007
 */
public class SFDFeatures {

    public static final ResourceKey<ConfiguredFeature<?, ?>> JADE = FeatureUtils.createKey(MODID + ":jade");
    public static final ResourceKey<ConfiguredFeature<?, ?>> SULPHUR = FeatureUtils.createKey(MODID + ":sulphur");
    public static final ResourceKey<ConfiguredFeature<?, ?>> NITER = FeatureUtils.createKey(MODID + ":niter");
    public static final ResourceKey<ConfiguredFeature<?, ?>> RICE = FeatureUtils.createKey(MODID + ":rice");
    public static final ResourceKey<ConfiguredFeature<?, ?>> REHMANNIA = FeatureUtils.createKey(MODID + ":rehmannia");
    public static final ResourceKey<ConfiguredFeature<?, ?>> DRAGONLIVER_MELON = FeatureUtils.createKey(MODID + ":dragonliver_melon");

    public static void bootstrap(BootstapContext<ConfiguredFeature<?, ?>> context) {

    }
}
