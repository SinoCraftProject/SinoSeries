package games.moegirl.sinocraft.sinofoundation.world;

import games.moegirl.sinocraft.sinocore.tree.TreeBlockType;
import games.moegirl.sinocraft.sinofoundation.SFDTrees;
import net.minecraft.core.Holder;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.levelgen.VerticalAnchor;
import net.minecraft.world.level.levelgen.blockpredicates.BlockPredicate;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.placement.*;
import net.minecraft.world.level.material.Fluids;

import java.util.List;

import static games.moegirl.sinocraft.sinofoundation.SinoFoundation.MODID;

/**
 * @author luqin2007
 */
public class SFDPlacements {

    public static final ResourceKey<PlacedFeature> SULPHUR = placementKey("sulphur");
    public static final ResourceKey<PlacedFeature> NETHER_SULPHUR = placementKey("nether_sulphur");
    public static final ResourceKey<PlacedFeature> DEEPSLATE_SULPHUR = placementKey("deepslate_sulphur");
    public static final ResourceKey<PlacedFeature> NITER = placementKey("niter");
    public static final ResourceKey<PlacedFeature> BLACK_JADE = placementKey("black_jade");
    public static final ResourceKey<PlacedFeature> DEEPSLATE_BLACK_JADE = placementKey("deepslate_black_jade");
    public static final ResourceKey<PlacedFeature> GREEN_JADE = placementKey("green_jade");
    public static final ResourceKey<PlacedFeature> DEEPSLATE_GREEN_JADE = placementKey("deepslate_green_jade");
    public static final ResourceKey<PlacedFeature> RED_JADE = placementKey("red_jade");
    public static final ResourceKey<PlacedFeature> DEEPSLATE_RED_JADE = placementKey("deepslate_red_jade");
    public static final ResourceKey<PlacedFeature> WHITE_JADE = placementKey("white_jade");
    public static final ResourceKey<PlacedFeature> DEEPSLATE_WHITE_JADE = placementKey("deepslate_white_jade");
    public static final ResourceKey<PlacedFeature> YELLOW_JADE = placementKey("yellow_jade");
    public static final ResourceKey<PlacedFeature> DEEPSLATE_YELLOW_JADE = placementKey("deepslate_yellow_jade");

    public static final ResourceKey<PlacedFeature> WORMWOOD = placementKey("wormwood");
    public static final ResourceKey<PlacedFeature> RICE = placementKey("rice");
    public static final ResourceKey<PlacedFeature> SESAME = placementKey("sesame");
    public static final ResourceKey<PlacedFeature> MULBERRY = placementKey("mulberry");
    public static final ResourceKey<PlacedFeature> MULBERRY_CHECKED = placementKey("mulberry_checked");
    public static final ResourceKey<PlacedFeature> MULBERRY_PLAIN = placementKey("mulberry_plain");

    public static Holder.Reference<PlacedFeature> HOLDER_SULPHUR;
    public static Holder.Reference<PlacedFeature> HOLDER_NETHER_SULPHUR;
    public static Holder.Reference<PlacedFeature> HOLDER_DEEPSLATE_SULPHUR;
    public static Holder.Reference<PlacedFeature> HOLDER_NITER;
    public static Holder.Reference<PlacedFeature> HOLDER_BLACK_JADE;
    public static Holder.Reference<PlacedFeature> HOLDER_DEEPSLATE_BLACK_JADE;
    public static Holder.Reference<PlacedFeature> HOLDER_GREEN_JADE;
    public static Holder.Reference<PlacedFeature> HOLDER_DEEPSLATE_GREEN_JADE;
    public static Holder.Reference<PlacedFeature> HOLDER_RED_JADE;
    public static Holder.Reference<PlacedFeature> HOLDER_DEEPSLATE_RED_JADE;
    public static Holder.Reference<PlacedFeature> HOLDER_WHITE_JADE;
    public static Holder.Reference<PlacedFeature> HOLDER_DEEPSLATE_WHITE_JADE;
    public static Holder.Reference<PlacedFeature> HOLDER_YELLOW_JADE;
    public static Holder.Reference<PlacedFeature> HOLDER_DEEPSLATE_YELLOW_JADE;
    public static Holder.Reference<PlacedFeature> HOLDER_RICE;
    public static Holder.Reference<PlacedFeature> HOLDER_WORMWOOD;
    public static Holder.Reference<PlacedFeature> HOLDER_SESAME;
    public static Holder.Reference<PlacedFeature> HOLDER_MULBERRY;
    public static Holder.Reference<PlacedFeature> HOLDER_MULBERRY_CHECKED;
    public static Holder.Reference<PlacedFeature> HOLDER_MULBERRY_PLAIN;

    public static void bootstrap(BootstapContext<PlacedFeature> context) {
        HOLDER_SULPHUR = context.register(SULPHUR, orePlaced(SFDFeatures.HOLDER_SULPHUR, 0, 48));
        HOLDER_NETHER_SULPHUR = context.register(NETHER_SULPHUR, orePlaced(SFDFeatures.HOLDER_NETHER_SULPHUR, 0, 128));
        HOLDER_DEEPSLATE_SULPHUR = context.register(DEEPSLATE_SULPHUR, orePlaced(SFDFeatures.HOLDER_DEEPSLATE_SULPHUR, -32, 8));
        HOLDER_NITER = context.register(NITER, orePlaced(SFDFeatures.HOLDER_NITER, 8, 64));

        HOLDER_BLACK_JADE = context.register(BLACK_JADE, orePlaced(SFDFeatures.HOLDER_BLACK_JADE, 0, 64));
        HOLDER_DEEPSLATE_BLACK_JADE = context.register(DEEPSLATE_BLACK_JADE, orePlaced(SFDFeatures.HOLDER_DEEPSLATE_BLACK_JADE, -20, 4));
        HOLDER_GREEN_JADE = context.register(GREEN_JADE, orePlaced(SFDFeatures.HOLDER_GREEN_JADE, 0, 5));
        HOLDER_DEEPSLATE_GREEN_JADE = context.register(DEEPSLATE_GREEN_JADE, orePlaced(SFDFeatures.HOLDER_DEEPSLATE_GREEN_JADE, -48, 4));
        HOLDER_RED_JADE = context.register(RED_JADE, orePlaced(SFDFeatures.HOLDER_RED_JADE, 0, 20));
        HOLDER_DEEPSLATE_RED_JADE = context.register(DEEPSLATE_RED_JADE, orePlaced(SFDFeatures.HOLDER_DEEPSLATE_RED_JADE, -48, 4));
        HOLDER_WHITE_JADE = context.register(WHITE_JADE, orePlaced(SFDFeatures.HOLDER_WHITE_JADE, 0, 48));
        HOLDER_DEEPSLATE_WHITE_JADE = context.register(DEEPSLATE_WHITE_JADE, orePlaced(SFDFeatures.HOLDER_DEEPSLATE_WHITE_JADE, 0, 4));
        HOLDER_YELLOW_JADE = context.register(YELLOW_JADE, orePlaced(SFDFeatures.HOLDER_YELLOW_JADE, 0, 5));
        HOLDER_DEEPSLATE_YELLOW_JADE = context.register(DEEPSLATE_YELLOW_JADE, orePlaced(SFDFeatures.HOLDER_DEEPSLATE_YELLOW_JADE, -48, 4));

        HOLDER_RICE = context.register(RICE, cropPlaced(SFDFeatures.HOLDER_RICE, BlockPredicate.matchesFluids(Fluids.WATER)));
        HOLDER_WORMWOOD = context.register(WORMWOOD, cropPlaced(SFDFeatures.HOLDER_WORMWOOD, BlockPredicate.matchesTag(BlockTags.DIRT)));
        HOLDER_SESAME = context.register(SESAME, cropPlaced(SFDFeatures.HOLDER_SESAME, BlockPredicate.matchesTag(BlockTags.DIRT)));

        HOLDER_MULBERRY = context.register(MULBERRY, new PlacedFeature(SFDFeatures.HOLDER_MULBERRY, List.of()));
        HOLDER_MULBERRY_CHECKED = context.register(MULBERRY_CHECKED, new PlacedFeature(SFDFeatures.HOLDER_NO_OP, List.of(
                PlacementUtils.filteredByBlockSurvival(SFDTrees.MULBERRY.getBlock(TreeBlockType.SAPLING)),
                BiomeFilter.biome())));
        HOLDER_MULBERRY_PLAIN = context.register(MULBERRY_PLAIN, new PlacedFeature(SFDFeatures.HOLDER_MULBERRY_PLAIN, List.of()));
    }

    /**
     * 矿石的生成配置
     *
     * @param hMin 最低高度
     * @param hMax 最高高度
     * @return 配置
     */
    private static PlacedFeature orePlaced(Holder.Reference<ConfiguredFeature<?, ?>> configuration, int hMin, int hMax) {
        return new PlacedFeature(configuration, List.of(
                CountPlacement.of(9), // 生成次数
                InSquarePlacement.spread(),
                HeightRangePlacement.uniform(VerticalAnchor.absolute(hMin), VerticalAnchor.absolute(hMax)), // 高度区间
                BiomeFilter.biome() // 根据生物群系过滤
        ));
    }

    /**
     * 作物的生成配置
     *
     * @param forPredicate 底部方块
     * @return 配置
     */
    private static PlacedFeature cropPlaced(Holder.Reference<ConfiguredFeature<?, ?>> configuration, BlockPredicate forPredicate) {
        return new PlacedFeature(configuration, List.of(BiomeFilter.biome(), BlockPredicateFilter.forPredicate(forPredicate)));
    }

    private static ResourceKey<PlacedFeature> placementKey(String name) {
        return PlacementUtils.createKey(MODID + ":" + name);
    }
}
