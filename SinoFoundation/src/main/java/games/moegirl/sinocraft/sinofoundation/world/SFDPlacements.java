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

    public static class Ref {
        public static Holder.Reference<PlacedFeature> JADE;
        public static Holder.Reference<PlacedFeature> SULPHUR;
        public static Holder.Reference<PlacedFeature> NITER;
        public static Holder.Reference<PlacedFeature> RICE;
        public static Holder.Reference<PlacedFeature> REHMANNIA;
        public static Holder.Reference<PlacedFeature> DRAGONLIVER_MELON;
    }

    public static void bootstrap(BootstapContext<PlacedFeature> context) {
        HolderGetter<ConfiguredFeature<?, ?>> lookup = context.lookup(Registries.CONFIGURED_FEATURE);

        Ref.JADE = context.register(SFDPlacements.JADE, orePlaced(SFDFeatures.Ref.JADE, 0, 64));
        Ref.SULPHUR = context.register(SFDPlacements.SULPHUR, orePlaced(SFDFeatures.Ref.SULPHUR, -32, 8));
        Ref.NITER = context.register(SFDPlacements.NITER, orePlaced(SFDFeatures.Ref.NITER, 8, 64));
        Ref.RICE = context.register(SFDPlacements.RICE, cropPlaced(SFDFeatures.Ref.RICE, BlockPredicate.matchesFluids(Fluids.WATER)));
        Ref.REHMANNIA = context.register(SFDPlacements.REHMANNIA, cropPlaced(SFDFeatures.Ref.REHMANNIA, BlockPredicate.matchesTag(BlockTags.DIRT)));
        Ref.DRAGONLIVER_MELON = context.register(SFDPlacements.DRAGONLIVER_MELON, cropPlaced(SFDFeatures.Ref.DRAGONLIVER_MELON, BlockPredicate.matchesTag(SFDBlockTags.SPAWN_DRAGONLIVER_MELON)));
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
