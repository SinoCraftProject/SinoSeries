package games.moegirl.sinocraft.sinofoundation.world;

import games.moegirl.sinocraft.sinofoundation.block.SFDBlocks;
import net.minecraft.core.Holder;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.data.worldgen.features.FeatureUtils;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.OreFeature;
import net.minecraft.world.level.levelgen.feature.RandomPatchFeature;
import net.minecraft.world.level.levelgen.feature.configurations.OreConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.RandomPatchConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.SimpleBlockConfiguration;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.minecraft.world.level.levelgen.structure.templatesystem.RuleTest;
import net.minecraft.world.level.levelgen.structure.templatesystem.TagMatchTest;
import net.minecraftforge.registries.RegistryObject;

import static games.moegirl.sinocraft.sinofoundation.SinoFoundation.MODID;

/**
 * @author luqin2007
 */
public class SFDFeatures {

    private static final RuleTest ORE_REPLACEABLE = new TagMatchTest(BlockTags.DEEPSLATE_ORE_REPLACEABLES);

    public static final ResourceKey<ConfiguredFeature<?, ?>> JADE = FeatureUtils.createKey(MODID + ":jade");
    public static final ResourceKey<ConfiguredFeature<?, ?>> SULPHUR = FeatureUtils.createKey(MODID + ":sulphur");
    public static final ResourceKey<ConfiguredFeature<?, ?>> NITER = FeatureUtils.createKey(MODID + ":niter");
    public static final ResourceKey<ConfiguredFeature<?, ?>> RICE = FeatureUtils.createKey(MODID + ":rice");
    public static final ResourceKey<ConfiguredFeature<?, ?>> REHMANNIA = FeatureUtils.createKey(MODID + ":rehmannia");
    public static final ResourceKey<ConfiguredFeature<?, ?>> DRAGONLIVER_MELON = FeatureUtils.createKey(MODID + ":dragonliver_melon");

    public static void bootstrap(BootstapContext<ConfiguredFeature<?, ?>> context) {
        context.register(SFDFeatures.JADE, oreConfiguration(SFDBlocks.JADE_ORE, 16));
        context.register(SFDFeatures.SULPHUR, oreConfiguration(SFDBlocks.SULPHUR_ORE, 20));
        context.register(SFDFeatures.NITER, oreConfiguration(SFDBlocks.NITER_ORE, 20));
        context.register(SFDFeatures.RICE, cropConfiguration(SFDBlocks.RICE));
        context.register(SFDFeatures.REHMANNIA, cropConfiguration(SFDBlocks.REHMANNIA));
        context.register(SFDFeatures.DRAGONLIVER_MELON, cropConfiguration(SFDBlocks.DRAGONLIVER_MELON));
    }

    /**
     * 矿石每次生成的配置
     *
     * @param ore  矿物方块
     * @param size 每一组生成的矿石平均个数
     * @return 生成配置
     */
    private static ConfiguredFeature<OreConfiguration, OreFeature> oreConfiguration(RegistryObject<? extends Block> ore, int size) {
        OreConfiguration configuration = new OreConfiguration(ORE_REPLACEABLE, ore.get().defaultBlockState(), size);
        return new ConfiguredFeature<>((OreFeature) Feature.ORE, configuration);
    }

    /**
     * 作物每次生成的配置
     *
     * @param block 作物方块
     * @return 生成配置
     */
    private static ConfiguredFeature<RandomPatchConfiguration, RandomPatchFeature> cropConfiguration(RegistryObject<? extends Block> block) {
        Holder<PlacedFeature> placed = PlacementUtils.onlyWhenEmpty(Feature.SIMPLE_BLOCK, new SimpleBlockConfiguration(BlockStateProvider.simple(block.get())));
        RandomPatchConfiguration configuration = new RandomPatchConfiguration(30, 5, 2, placed);
        return new ConfiguredFeature<>((RandomPatchFeature) Feature.RANDOM_PATCH, configuration);
    }
}
