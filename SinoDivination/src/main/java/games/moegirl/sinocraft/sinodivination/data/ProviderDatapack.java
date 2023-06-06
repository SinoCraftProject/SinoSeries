package games.moegirl.sinocraft.sinodivination.data;

import games.moegirl.sinocraft.sinodivination.SinoDivination;
import games.moegirl.sinocraft.sinodivination.block.SDBlocks;
import games.moegirl.sinocraft.sinodivination.entity.SDDamages;
import games.moegirl.sinocraft.sinodivination.world.SDFeatures;
import net.minecraft.core.Holder;
import net.minecraft.core.RegistrySetBuilder;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.damagesource.DamageScaling;
import net.minecraft.world.damagesource.DamageType;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.levelgen.VerticalAnchor;
import net.minecraft.world.level.levelgen.blockpredicates.BlockPredicate;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.OreFeature;
import net.minecraft.world.level.levelgen.feature.RandomPatchFeature;
import net.minecraft.world.level.levelgen.feature.configurations.OreConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.RandomPatchConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.SimpleBlockConfiguration;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.minecraft.world.level.levelgen.placement.*;
import net.minecraft.world.level.levelgen.structure.templatesystem.RuleTest;
import net.minecraft.world.level.levelgen.structure.templatesystem.TagMatchTest;
import net.minecraft.world.level.material.Fluids;
import net.minecraftforge.common.data.DatapackBuiltinEntriesProvider;
import net.minecraftforge.data.event.GatherDataEvent;
import net.minecraftforge.registries.RegistryObject;

import java.util.List;
import java.util.Set;


/**
 * 世界生成
 *
 * @author luqin2007
 */
@SuppressWarnings("NotNullFieldNotInitialized")
class ProviderDatapack extends DatapackBuiltinEntriesProvider {

    private static final RuleTest ORE_REPLACEABLE = new TagMatchTest(BlockTags.DEEPSLATE_ORE_REPLACEABLES);

    private static Holder.Reference<ConfiguredFeature<?, ?>> jade;
    private static Holder.Reference<ConfiguredFeature<?, ?>> sulphur;
    private static Holder.Reference<ConfiguredFeature<?, ?>> niter;
    private static Holder.Reference<ConfiguredFeature<?, ?>> rice;
    private static Holder.Reference<ConfiguredFeature<?, ?>> rehmannia;
    private static Holder.Reference<ConfiguredFeature<?, ?>> dragonliverMelon;

    public ProviderDatapack(GatherDataEvent event) {
        super(event.getGenerator().getPackOutput(), event.getLookupProvider(), new RegistrySetBuilder()
                .add(Registries.CONFIGURED_FEATURE, ProviderDatapack::addFeatures)
                .add(Registries.PLACED_FEATURE, ProviderDatapack::addPlacedFeatures)
                .add(Registries.DAMAGE_TYPE, ProviderDatapack::addDamageTypes), Set.of(SinoDivination.MODID));
    }

    private static void addFeatures(BootstapContext<ConfiguredFeature<?, ?>> context) {
        rice = context.register(SDFeatures.Features.RICE, cropConfiguration(SDBlocks.RICE));
        rehmannia = context.register(SDFeatures.Features.REHMANNIA, cropConfiguration(SDBlocks.REHMANNIA));
        dragonliverMelon = context.register(SDFeatures.Features.DRAGONLIVER_MELON, cropConfiguration(SDBlocks.DRAGONLIVER_MELON));
    }

    private static void addPlacedFeatures(BootstapContext<PlacedFeature> context) {
        context.register(SDFeatures.Placements.JADE, orePlaced(jade, 0, 64));
        context.register(SDFeatures.Placements.SULPHUR, orePlaced(sulphur, -32, 8));
        context.register(SDFeatures.Placements.NITER, orePlaced(niter, 8, 64));
        context.register(SDFeatures.Placements.RICE, cropPlaced(rice, BlockPredicate.matchesFluids(Fluids.WATER)));
        context.register(SDFeatures.Placements.REHMANNIA, cropPlaced(rehmannia, BlockPredicate.matchesTag(BlockTags.DIRT)));
        context.register(SDFeatures.Placements.DRAGONLIVER_MELON, cropPlaced(dragonliverMelon, BlockPredicate.matchesTag(SDTags.SPAWN_DRAGONLIVER_MELON_BLOCK)));
    }

    private static void addDamageTypes(BootstapContext<DamageType> context) {
        context.register(SDDamages.SOPHORA_DOOR, new DamageType(SinoDivination.MODID + ".damage.sophora_door", DamageScaling.ALWAYS, 0));
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

    @Override
    public String getName() {
        return super.getName() + ": " + SinoDivination.MODID;
    }
}
