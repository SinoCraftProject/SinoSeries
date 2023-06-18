package games.moegirl.sinocraft.sinodivination.data;

import games.moegirl.sinocraft.sinodivination.SinoDivination;
import games.moegirl.sinocraft.sinodivination.block.SDBlocks;
import games.moegirl.sinocraft.sinodivination.world.SDFeatures;
import net.minecraft.core.Holder;
import net.minecraft.core.RegistrySetBuilder;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.levelgen.blockpredicates.BlockPredicate;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.RandomPatchFeature;
import net.minecraft.world.level.levelgen.feature.configurations.RandomPatchConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.SimpleBlockConfiguration;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.minecraft.world.level.levelgen.placement.BiomeFilter;
import net.minecraft.world.level.levelgen.placement.BlockPredicateFilter;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
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

    private static Holder.Reference<ConfiguredFeature<?, ?>> rice;
    private static Holder.Reference<ConfiguredFeature<?, ?>> rehmannia;
    private static Holder.Reference<ConfiguredFeature<?, ?>> dragonliverMelon;

    public ProviderDatapack(GatherDataEvent event) {
        super(event.getGenerator().getPackOutput(), event.getLookupProvider(), new RegistrySetBuilder()
                .add(Registries.CONFIGURED_FEATURE, ProviderDatapack::addFeatures)
                .add(Registries.PLACED_FEATURE, ProviderDatapack::addPlacedFeatures), Set.of(SinoDivination.MODID));
    }

    private static void addFeatures(BootstapContext<ConfiguredFeature<?, ?>> context) {
        rice = context.register(SDFeatures.Features.RICE, cropConfiguration(SDBlocks.RICE));
        rehmannia = context.register(SDFeatures.Features.REHMANNIA, cropConfiguration(SDBlocks.REHMANNIA));
        dragonliverMelon = context.register(SDFeatures.Features.DRAGONLIVER_MELON, cropConfiguration(SDBlocks.DRAGONLIVER_MELON));
    }

    private static void addPlacedFeatures(BootstapContext<PlacedFeature> context) {
        context.register(SDFeatures.Placements.RICE, cropPlaced(rice, BlockPredicate.matchesFluids(Fluids.WATER)));
        context.register(SDFeatures.Placements.REHMANNIA, cropPlaced(rehmannia, BlockPredicate.matchesTag(BlockTags.DIRT)));
        context.register(SDFeatures.Placements.DRAGONLIVER_MELON, cropPlaced(dragonliverMelon, BlockPredicate.matchesTag(SDTags.SPAWN_DRAGONLIVER_MELON_BLOCK)));
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
