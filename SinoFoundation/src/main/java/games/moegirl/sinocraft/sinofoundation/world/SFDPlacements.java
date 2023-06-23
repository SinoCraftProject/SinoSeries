package games.moegirl.sinocraft.sinofoundation.world;

import games.moegirl.sinocraft.sinofoundation.block.SFDBlockTags;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
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

    public static final ResourceKey<PlacedFeature> JADE = PlacementUtils.createKey(MODID + ":jade");
    public static final ResourceKey<PlacedFeature> SULPHUR = PlacementUtils.createKey(MODID + ":sulphur");
    public static final ResourceKey<PlacedFeature> NITER = PlacementUtils.createKey(MODID + ":niter");
    public static final ResourceKey<PlacedFeature> RICE = PlacementUtils.createKey(MODID + ":rice");
    public static final ResourceKey<PlacedFeature> REHMANNIA = PlacementUtils.createKey(MODID + ":rehmannia");
    public static final ResourceKey<PlacedFeature> DRAGONLIVER_MELON = PlacementUtils.createKey(MODID + ":dragonliver_melon");

    public static void bootstrap(BootstapContext<PlacedFeature> context) {
        HolderGetter<ConfiguredFeature<?, ?>> lookup = context.lookup(Registries.CONFIGURED_FEATURE);

        Holder.Reference<ConfiguredFeature<?, ?>> jade = lookup.getOrThrow(SFDFeatures.JADE);
        Holder.Reference<ConfiguredFeature<?, ?>> sulphur = lookup.getOrThrow(SFDFeatures.SULPHUR);
        Holder.Reference<ConfiguredFeature<?, ?>> niter = lookup.getOrThrow(SFDFeatures.NITER);
        Holder.Reference<ConfiguredFeature<?, ?>> rice = lookup.getOrThrow(SFDFeatures.RICE);
        Holder.Reference<ConfiguredFeature<?, ?>> rehmannia = lookup.getOrThrow(SFDFeatures.REHMANNIA);
        Holder.Reference<ConfiguredFeature<?, ?>> dragonliverMelon = lookup.getOrThrow(SFDFeatures.DRAGONLIVER_MELON);

        context.register(SFDPlacements.JADE, orePlaced(jade, 0, 64));
        context.register(SFDPlacements.SULPHUR, orePlaced(sulphur, -32, 8));
        context.register(SFDPlacements.NITER, orePlaced(niter, 8, 64));
        context.register(SFDPlacements.RICE, cropPlaced(rice, BlockPredicate.matchesFluids(Fluids.WATER)));
        context.register(SFDPlacements.REHMANNIA, cropPlaced(rehmannia, BlockPredicate.matchesTag(BlockTags.DIRT)));
        context.register(SFDPlacements.DRAGONLIVER_MELON, cropPlaced(dragonliverMelon, BlockPredicate.matchesTag(SFDBlockTags.SPAWN_DRAGONLIVER_MELON)));
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
