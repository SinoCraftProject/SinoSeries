package games.moegirl.sinocraft.sinofoundation.world;

import games.moegirl.sinocraft.sinocore.world.features.FeaturesHelper;
import games.moegirl.sinocraft.sinocore.world.placement.PlacementsHelper;
import net.minecraft.core.Holder;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.levelgen.VerticalAnchor;
import net.minecraft.world.level.levelgen.blockpredicates.BlockPredicate;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.placement.*;

import java.util.List;

import static games.moegirl.sinocraft.sinofoundation.SinoFoundation.MODID;

/**
 * @author luqin2007
 */
public class SFDPlacements {

    public static final ResourceKey<PlacedFeature> SULPHUR = PlacementsHelper.createKey(new ResourceLocation(MODID, "sulphur"));
    public static final ResourceKey<PlacedFeature> NETHER_SULPHUR = PlacementsHelper.createKey(new ResourceLocation(MODID, "nether_sulphur"));
    public static final ResourceKey<PlacedFeature> DEEPSLATE_SULPHUR = PlacementsHelper.createKey(new ResourceLocation(MODID, "deepslate_sulphur"));
    public static final ResourceKey<PlacedFeature> NITER = PlacementsHelper.createKey(new ResourceLocation(MODID, "niter"));

    public static final ResourceKey<PlacedFeature> BLACK_JADE = PlacementsHelper.createKey(new ResourceLocation(MODID, "black_jade"));
    public static final ResourceKey<PlacedFeature> DEEPSLATE_BLACK_JADE = PlacementsHelper.createKey(new ResourceLocation(MODID, "deepslate_black_jade"));
    public static final ResourceKey<PlacedFeature> GREEN_JADE = PlacementsHelper.createKey(new ResourceLocation(MODID, "green_jade"));
    public static final ResourceKey<PlacedFeature> DEEPSLATE_GREEN_JADE = PlacementsHelper.createKey(new ResourceLocation(MODID, "deepslate_green_jade"));
    public static final ResourceKey<PlacedFeature> RED_JADE = PlacementsHelper.createKey(new ResourceLocation(MODID, "red_jade"));
    public static final ResourceKey<PlacedFeature> DEEPSLATE_RED_JADE = PlacementsHelper.createKey(new ResourceLocation(MODID, "deepslate_red_jade"));
    public static final ResourceKey<PlacedFeature> WHITE_JADE = PlacementsHelper.createKey(new ResourceLocation(MODID, "white_jade"));
    public static final ResourceKey<PlacedFeature> DEEPSLATE_WHITE_JADE = PlacementsHelper.createKey(new ResourceLocation(MODID, "deepslate_white_jade"));
    public static final ResourceKey<PlacedFeature> YELLOW_JADE = PlacementsHelper.createKey(new ResourceLocation(MODID, "yellow_jade"));
    public static final ResourceKey<PlacedFeature> DEEPSLATE_YELLOW_JADE = PlacementsHelper.createKey(new ResourceLocation(MODID, "deepslate_yellow_jade"));

//    public static final ResourceKey<PlacedFeature> RICE = PlacementUtils.createKey(MODID + ":rice");
//    public static final ResourceKey<PlacedFeature> REHMANNIA = PlacementUtils.createKey(MODID + ":rehmannia");
//    public static final ResourceKey<PlacedFeature> DRAGONLIVER_MELON = PlacementUtils.createKey(MODID + ":dragonliver_melon");

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

    public static void bootstrap(BootstapContext<PlacedFeature> context) {
        HOLDER_SULPHUR = context.register(SFDPlacements.SULPHUR, orePlaced(SFDFeatures.HOLDER_SULPHUR, 0, 48));
        HOLDER_NETHER_SULPHUR = context.register(SFDPlacements.NETHER_SULPHUR, orePlaced(SFDFeatures.HOLDER_NETHER_SULPHUR, 0, 128));
        HOLDER_DEEPSLATE_SULPHUR = context.register(SFDPlacements.DEEPSLATE_SULPHUR, orePlaced(SFDFeatures.HOLDER_DEEPSLATE_SULPHUR, -32, 8));
        HOLDER_NITER = context.register(SFDPlacements.NITER, orePlaced(SFDFeatures.HOLDER_NITER, 8, 64));

        HOLDER_BLACK_JADE = context.register(SFDPlacements.BLACK_JADE, orePlaced(SFDFeatures.HOLDER_BLACK_JADE, 0, 64));
        HOLDER_DEEPSLATE_BLACK_JADE = context.register(SFDPlacements.DEEPSLATE_BLACK_JADE, orePlaced(SFDFeatures.HOLDER_DEEPSLATE_BLACK_JADE, -20, 4));
        HOLDER_GREEN_JADE = context.register(SFDPlacements.GREEN_JADE, orePlaced(SFDFeatures.HOLDER_GREEN_JADE, 0, 5));
        HOLDER_DEEPSLATE_GREEN_JADE = context.register(SFDPlacements.DEEPSLATE_GREEN_JADE, orePlaced(SFDFeatures.HOLDER_DEEPSLATE_GREEN_JADE, -48, 4));
        HOLDER_RED_JADE = context.register(SFDPlacements.RED_JADE, orePlaced(SFDFeatures.HOLDER_RED_JADE, 0, 20));
        HOLDER_DEEPSLATE_RED_JADE = context.register(SFDPlacements.DEEPSLATE_RED_JADE, orePlaced(SFDFeatures.HOLDER_DEEPSLATE_RED_JADE, -48, 4));
        HOLDER_WHITE_JADE = context.register(SFDPlacements.WHITE_JADE, orePlaced(SFDFeatures.HOLDER_WHITE_JADE, 0, 48));
        HOLDER_DEEPSLATE_WHITE_JADE = context.register(SFDPlacements.DEEPSLATE_WHITE_JADE, orePlaced(SFDFeatures.HOLDER_DEEPSLATE_WHITE_JADE, 0, 4));
        HOLDER_YELLOW_JADE = context.register(SFDPlacements.YELLOW_JADE, orePlaced(SFDFeatures.HOLDER_YELLOW_JADE, 0, 5));
        HOLDER_DEEPSLATE_YELLOW_JADE = context.register(SFDPlacements.DEEPSLATE_YELLOW_JADE, orePlaced(SFDFeatures.HOLDER_DEEPSLATE_YELLOW_JADE, -48, 4));

//        Ref.RICE = context.register(SFDPlacements.RICE, cropPlaced(SFDFeatures.Ref.RICE, BlockPredicate.matchesFluids(Fluids.WATER)));
//        Ref.REHMANNIA = context.register(SFDPlacements.REHMANNIA, cropPlaced(SFDFeatures.Ref.REHMANNIA, BlockPredicate.matchesTag(BlockTags.DIRT)));
//        Ref.DRAGONLIVER_MELON = context.register(SFDPlacements.DRAGONLIVER_MELON, cropPlaced(SFDFeatures.Ref.DRAGONLIVER_MELON, BlockPredicate.matchesTag(SFDBlockTags.SPAWN_DRAGONLIVER_MELON)));
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
                InSquarePlacement.spread(), // ??
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
}
