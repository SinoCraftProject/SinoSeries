package games.moegirl.sinocraft.sinodivination.data;

import com.mojang.serialization.JsonOps;
import games.moegirl.sinocraft.sinodivination.SinoDivination;
import games.moegirl.sinocraft.sinodivination.block.SDBlocks;
import games.moegirl.sinocraft.sinodivination.world.SDFeatures;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.HolderSet;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.PackType;
import net.minecraft.tags.BiomeTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.minecraftforge.common.data.JsonCodecProvider;
import net.minecraftforge.common.world.BiomeModifier;
import net.minecraftforge.common.world.ForgeBiomeModifiers;
import net.minecraftforge.data.event.GatherDataEvent;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;


/**
 * 用于生成 BiomeModifier
 *
 * @author luqin2007
 */
class ProviderBiomeModifier extends JsonCodecProvider<BiomeModifier> {

    public ProviderBiomeModifier(GatherDataEvent event) {
        super(event.getGenerator().getPackOutput(), event.getExistingFileHelper(), SinoDivination.MODID,
                JsonOps.INSTANCE, PackType.SERVER_DATA, "forge/biome_modifier", BiomeModifier.DIRECT_CODEC,
                buildModifiers(event.getLookupProvider()));
    }

    private static Map<ResourceLocation, BiomeModifier> buildModifiers(CompletableFuture<HolderLookup.Provider> lookupProvider) {
        Map<ResourceLocation, BiomeModifier> map = new HashMap<>();
        try {
            HolderLookup.Provider provider = lookupProvider.get();
            HolderLookup.RegistryLookup<Biome> biomeLookup = provider.lookup(Registries.BIOME).orElseThrow();
            HolderLookup.RegistryLookup<PlacedFeature> featureLookup = provider.lookup(Registries.PLACED_FEATURE).orElseThrow();
            map.put(SDFeatures.Placements.JADE.location(), createOverworldOre(biomeLookup, featureLookup, SDFeatures.Placements.JADE));
            map.put(SDFeatures.Placements.NITER.location(), createOverworldOre(biomeLookup, featureLookup, SDFeatures.Placements.NITER));
            map.put(SDFeatures.Placements.SULPHUR.location(), createOverworldOre(biomeLookup, featureLookup, SDFeatures.Placements.SULPHUR));
            map.put(SDFeatures.Placements.RICE.location(), createCrop(biomeLookup, featureLookup, SDFeatures.Placements.RICE, SDTags.SPAWN_RICE));
            map.put(SDFeatures.Placements.REHMANNIA.location(), createCrop(biomeLookup, featureLookup, SDFeatures.Placements.REHMANNIA, SDTags.SPAWN_REHMANNIA));
            map.put(SDFeatures.Placements.DRAGONLIVER_MELON.location(), createCrop(biomeLookup, featureLookup, SDFeatures.Placements.DRAGONLIVER_MELON, SDTags.SPAWN_DRAGONLIVER_MELON));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return map;
    }

    private static BiomeModifier createOverworldOre(HolderLookup.RegistryLookup<Biome> biomeLookup,
                                                    HolderLookup.RegistryLookup<PlacedFeature> featureLookup,
                                                    ResourceKey<PlacedFeature> name) {
        return new ForgeBiomeModifiers.AddFeaturesBiomeModifier(
                biomeLookup.getOrThrow(BiomeTags.IS_OVERWORLD),
                HolderSet.direct(featureLookup.getOrThrow(name)),
                GenerationStep.Decoration.UNDERGROUND_ORES);
    }

    private static BiomeModifier createCrop(HolderLookup.RegistryLookup<Biome> biomeLookup,
                                            HolderLookup.RegistryLookup<PlacedFeature> featureLookup,
                                            ResourceKey<PlacedFeature> name, TagKey<Biome> biome) {
        return new ForgeBiomeModifiers.AddFeaturesBiomeModifier(
                biomeLookup.getOrThrow(biome),
                HolderSet.direct(featureLookup.getOrThrow(name)),
                GenerationStep.Decoration.VEGETAL_DECORATION);
    }
}
