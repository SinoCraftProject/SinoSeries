package games.moegirl.sinocraft.sinofoundation.world;

import games.moegirl.sinocraft.sinocore.tree.Tree;
import games.moegirl.sinocraft.sinofoundation.SFDTrees;
import games.moegirl.sinocraft.sinofoundation.block.SFDBlocks;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.data.worldgen.features.FeatureUtils;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.WeightedPlacedFeature;
import net.minecraft.world.level.levelgen.feature.configurations.*;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.minecraft.world.level.levelgen.structure.templatesystem.RuleTest;
import net.minecraft.world.level.levelgen.structure.templatesystem.TagMatchTest;
import net.minecraftforge.common.Tags;
import net.minecraftforge.registries.RegistryObject;

import java.util.Map;

import static games.moegirl.sinocraft.sinofoundation.SinoFoundation.MODID;

/**
 * @author luqin2007
 */
public class SFDFeatures {

    private static final RuleTest ORE_REPLACEABLE = new TagMatchTest(BlockTags.STONE_ORE_REPLACEABLES);
    private static final RuleTest DEEPSLATE_ORE_REPLACEABLE = new TagMatchTest(BlockTags.DEEPSLATE_ORE_REPLACEABLES);
    private static final RuleTest NETHER_ORE_REPLACEABLE = new TagMatchTest(Tags.Blocks.NETHERRACK);

    public static final ResourceKey<ConfiguredFeature<?, ?>> NO_OP = featureKey("no_op");

    public static final ResourceKey<ConfiguredFeature<?, ?>> SULPHUR = featureKey("sulphur");
    public static final ResourceKey<ConfiguredFeature<?, ?>> NETHER_SULPHUR = featureKey("nether_sulphur");
    public static final ResourceKey<ConfiguredFeature<?, ?>> DEEPSLATE_SULPHUR = featureKey("deepslate_sulphur");
    public static final ResourceKey<ConfiguredFeature<?, ?>> NITER = featureKey("niter");

    public static final ResourceKey<ConfiguredFeature<?, ?>> BLACK_JADE = featureKey("black_jade");
    public static final ResourceKey<ConfiguredFeature<?, ?>> DEEPSLATE_BLACK_JADE = featureKey("deepslate_black_jade");
    public static final ResourceKey<ConfiguredFeature<?, ?>> GREEN_JADE = featureKey("green_jade");
    public static final ResourceKey<ConfiguredFeature<?, ?>> DEEPSLATE_GREEN_JADE = featureKey("deepslate_green_jade");
    public static final ResourceKey<ConfiguredFeature<?, ?>> RED_JADE = featureKey("red_jade");
    public static final ResourceKey<ConfiguredFeature<?, ?>> DEEPSLATE_RED_JADE = featureKey("deepslate_red_jade");
    public static final ResourceKey<ConfiguredFeature<?, ?>> WHITE_JADE = featureKey("white_jade");
    public static final ResourceKey<ConfiguredFeature<?, ?>> DEEPSLATE_WHITE_JADE = featureKey("deepslate_white_jade");
    public static final ResourceKey<ConfiguredFeature<?, ?>> YELLOW_JADE = featureKey("yellow_jade");
    public static final ResourceKey<ConfiguredFeature<?, ?>> DEEPSLATE_YELLOW_JADE = featureKey("deepslate_yellow_jade");

    public static final ResourceKey<ConfiguredFeature<?, ?>> RICE = featureKey("rice");
    public static final ResourceKey<ConfiguredFeature<?, ?>> WORMWOOD = featureKey("wormwood");
    public static final ResourceKey<ConfiguredFeature<?, ?>> SESAME = featureKey("sesame");
    public static final ResourceKey<ConfiguredFeature<?, ?>> MULBERRY = featureKey("mulberry");
    public static final ResourceKey<ConfiguredFeature<?, ?>> MULBERRY_PLAIN = featureKey("mulberry_plain");

    public static Holder.Reference<ConfiguredFeature<?, ?>> HOLDER_NO_OP;

    public static Holder.Reference<ConfiguredFeature<?, ?>> HOLDER_SULPHUR;
    public static Holder.Reference<ConfiguredFeature<?, ?>> HOLDER_NETHER_SULPHUR;
    public static Holder.Reference<ConfiguredFeature<?, ?>> HOLDER_DEEPSLATE_SULPHUR;
    public static Holder.Reference<ConfiguredFeature<?, ?>> HOLDER_NITER;

    public static Holder.Reference<ConfiguredFeature<?, ?>> HOLDER_BLACK_JADE;
    public static Holder.Reference<ConfiguredFeature<?, ?>> HOLDER_DEEPSLATE_BLACK_JADE;
    public static Holder.Reference<ConfiguredFeature<?, ?>> HOLDER_GREEN_JADE;
    public static Holder.Reference<ConfiguredFeature<?, ?>> HOLDER_DEEPSLATE_GREEN_JADE;
    public static Holder.Reference<ConfiguredFeature<?, ?>> HOLDER_RED_JADE;
    public static Holder.Reference<ConfiguredFeature<?, ?>> HOLDER_DEEPSLATE_RED_JADE;
    public static Holder.Reference<ConfiguredFeature<?, ?>> HOLDER_WHITE_JADE;
    public static Holder.Reference<ConfiguredFeature<?, ?>> HOLDER_DEEPSLATE_WHITE_JADE;
    public static Holder.Reference<ConfiguredFeature<?, ?>> HOLDER_YELLOW_JADE;
    public static Holder.Reference<ConfiguredFeature<?, ?>> HOLDER_DEEPSLATE_YELLOW_JADE;

    public static Holder.Reference<ConfiguredFeature<?, ?>> HOLDER_WORMWOOD;
    public static Holder.Reference<ConfiguredFeature<?, ?>> HOLDER_RICE;
    public static Holder.Reference<ConfiguredFeature<?, ?>> HOLDER_SESAME;

    public static Holder.Reference<ConfiguredFeature<?, ?>> HOLDER_MULBERRY;
    public static Holder.Reference<ConfiguredFeature<?, ?>> HOLDER_MULBERRY_PLAIN;

    public static void bootstrap(BootstapContext<ConfiguredFeature<?, ?>> context) {
        HOLDER_NO_OP = context.register(NO_OP, new ConfiguredFeature<>(Feature.NO_OP, NoneFeatureConfiguration.INSTANCE));

        HOLDER_SULPHUR = context.register(SULPHUR, overworldStoneOre(SFDBlocks.SULPHUR_ORE, 6));
        HOLDER_NETHER_SULPHUR = context.register(NETHER_SULPHUR, netherrackOre(SFDBlocks.NETHER_SULPHUR_ORE, 6));
        HOLDER_DEEPSLATE_SULPHUR = context.register(DEEPSLATE_SULPHUR, overworldDeepslateOre(SFDBlocks.DEEPSLATE_SULPHUR_ORE, 5));
        HOLDER_NITER = context.register(NITER, overworldStoneOre(SFDBlocks.NITER_ORE, 8));

        HOLDER_BLACK_JADE = context.register(BLACK_JADE, overworldStoneOre(SFDBlocks.BLACK_JADE_ORE, 3));
        HOLDER_DEEPSLATE_BLACK_JADE = context.register(DEEPSLATE_BLACK_JADE, overworldDeepslateOre(SFDBlocks.DEEPSLATE_BLACK_JADE_ORE, 3));
        HOLDER_GREEN_JADE = context.register(GREEN_JADE, overworldStoneOre(SFDBlocks.GREEN_JADE_ORE, 3));
        HOLDER_DEEPSLATE_GREEN_JADE = context.register(DEEPSLATE_GREEN_JADE, overworldDeepslateOre(SFDBlocks.DEEPSLATE_GREEN_JADE_ORE, 3));
        HOLDER_RED_JADE = context.register(RED_JADE, overworldStoneOre(SFDBlocks.RED_JADE_ORE, 3));
        HOLDER_DEEPSLATE_RED_JADE = context.register(DEEPSLATE_RED_JADE, overworldDeepslateOre(SFDBlocks.DEEPSLATE_RED_JADE_ORE, 3));
        HOLDER_WHITE_JADE = context.register(WHITE_JADE, overworldStoneOre(SFDBlocks.WHITE_JADE_ORE, 3));
        HOLDER_DEEPSLATE_WHITE_JADE = context.register(DEEPSLATE_WHITE_JADE, overworldDeepslateOre(SFDBlocks.DEEPSLATE_WHITE_JADE_ORE, 3));
        HOLDER_YELLOW_JADE = context.register(YELLOW_JADE, overworldStoneOre(SFDBlocks.YELLOW_JADE_ORE, 3));
        HOLDER_DEEPSLATE_YELLOW_JADE = context.register(DEEPSLATE_YELLOW_JADE, overworldDeepslateOre(SFDBlocks.DEEPSLATE_YELLOW_JADE_ORE, 3));

        HOLDER_WORMWOOD = context.register(WORMWOOD, cropConfiguration(SFDBlocks.WORMWOOD));
        HOLDER_RICE = context.register(RICE, cropConfiguration(SFDBlocks.RICE));
        HOLDER_SESAME = context.register(SESAME, cropConfiguration(SFDBlocks.SESAME));
        HOLDER_MULBERRY = context.register(MULBERRY, tree(SFDTrees.MULBERRY));

        HolderGetter<PlacedFeature> lookup = context.lookup(Registries.PLACED_FEATURE);

        Holder.Reference<PlacedFeature> mulberry = lookup.get(SFDPlacements.MULBERRY).orElseThrow();
        Holder.Reference<PlacedFeature> mulberryChecked = lookup.get(SFDPlacements.MULBERRY_CHECKED).orElseThrow();
        HOLDER_MULBERRY_PLAIN = context.register(MULBERRY_PLAIN, random(Map.of(mulberry, 0.1f), mulberryChecked));
    }

    /**
     * 主世界石头矿石生成配置
     *
     * @param ore  矿物方块
     * @param size 每一组生成的矿石平均个数
     * @return 生成配置
     */
    private static ConfiguredFeature<OreConfiguration, Feature<OreConfiguration>>
    overworldStoneOre(RegistryObject<? extends Block> ore, int size) {
        OreConfiguration configuration = new OreConfiguration(ORE_REPLACEABLE, ore.get().defaultBlockState(), size);
        return new ConfiguredFeature<>(Feature.ORE, configuration);
    }

    /**
     * 主世界深板岩矿石生成配置
     *
     * @param ore  矿物方块
     * @param size 每一组生成的矿石平均个数
     * @return 生成配置
     */
    private static ConfiguredFeature<OreConfiguration, Feature<OreConfiguration>>
    overworldDeepslateOre(RegistryObject<? extends Block> ore, int size) {
        OreConfiguration configuration = new OreConfiguration(DEEPSLATE_ORE_REPLACEABLE, ore.get().defaultBlockState(), size);
        return new ConfiguredFeature<>(Feature.ORE, configuration);
    }

    /**
     * 下界地狱岩矿石生成配置
     *
     * @param ore  矿物方块
     * @param size 每一组生成的矿石平均个数
     * @return 生成配置
     */
    private static ConfiguredFeature<OreConfiguration, Feature<OreConfiguration>>
    netherrackOre(RegistryObject<? extends Block> ore, int size) {
        OreConfiguration configuration = new OreConfiguration(NETHER_ORE_REPLACEABLE, ore.get().defaultBlockState(), size);
        return new ConfiguredFeature<>(Feature.ORE, configuration);
    }

    /**
     * 作物每次生成的配置
     *
     * @param block 作物方块
     * @return 生成配置
     */
    private static ConfiguredFeature<RandomPatchConfiguration, Feature<RandomPatchConfiguration>>
    cropConfiguration(RegistryObject<? extends Block> block) {
        Holder<PlacedFeature> placed = PlacementUtils.onlyWhenEmpty(Feature.SIMPLE_BLOCK, new SimpleBlockConfiguration(BlockStateProvider.simple(block.get())));
        RandomPatchConfiguration configuration = new RandomPatchConfiguration(30, 5, 2, placed);
        return new ConfiguredFeature<>(Feature.RANDOM_PATCH, configuration);
    }

    /**
     * 从树的配置中生成 Feature
     *
     * @param tree 树
     * @return 生成配置
     */
    private static ConfiguredFeature<TreeConfiguration, Feature<TreeConfiguration>> tree(Tree tree) {
        return new ConfiguredFeature<>(Feature.TREE, tree.getFeaturedConfiguration().get());
    }

    /**
     * 将现有生成随机替换成其他生成
     *
     * @param list           替换配置和概率
     * @param defaultFeature 默认配置
     * @return 生成配置
     */
    private static ConfiguredFeature<RandomFeatureConfiguration, Feature<RandomFeatureConfiguration>>
    random(Map<Holder<PlacedFeature>, Float> list, Holder<PlacedFeature> defaultFeature) {
        return new ConfiguredFeature<>(Feature.RANDOM_SELECTOR, new RandomFeatureConfiguration(list.entrySet().stream()
                .map(entry -> new WeightedPlacedFeature(entry.getKey(), entry.getValue()))
                .toList(), defaultFeature));
    }

    private static ResourceKey<ConfiguredFeature<?, ?>> featureKey(String name) {
        return FeatureUtils.createKey(MODID + ":" + name);
    }
}
