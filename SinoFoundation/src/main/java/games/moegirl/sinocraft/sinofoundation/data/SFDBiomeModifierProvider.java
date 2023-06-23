package games.moegirl.sinocraft.sinofoundation.data;

import com.mojang.serialization.JsonOps;
import games.moegirl.sinocraft.sinocore.world.gen.TaggedBiomeFeatureModifier;
import games.moegirl.sinocraft.sinofoundation.SinoFoundation;
import games.moegirl.sinocraft.sinofoundation.biome.SFDBiomeTags;
import games.moegirl.sinocraft.sinofoundation.world.SFDPlacements;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.HolderSet;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.PackType;
import net.minecraft.tags.BiomeTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.common.data.JsonCodecProvider;
import net.minecraftforge.common.world.BiomeModifier;
import net.minecraftforge.data.event.GatherDataEvent;

import java.util.HashMap;
import java.util.Map;


/**
 * 用于生成 BiomeModifier，应当在所有 {@link net.minecraft.data.DataProvider} 之后调用
 *
 * @author luqin2007
 */
class SFDBiomeModifierProvider extends JsonCodecProvider<BiomeModifier> {

    public SFDBiomeModifierProvider(PackOutput output, ExistingFileHelper helper, GatherDataEvent event) {
        super(output, helper, SinoFoundation.MODID, JsonOps.INSTANCE, PackType.SERVER_DATA,
                "forge/biome_modifier", BiomeModifier.DIRECT_CODEC, buildModifiers(event));
    }

    private static Map<ResourceLocation, BiomeModifier> buildModifiers(GatherDataEvent event) {
        Map<ResourceLocation, BiomeModifier> map = new HashMap<>();
        try {
            HolderLookup.RegistryLookup<PlacedFeature> lookup = event.getLookupProvider().get().lookupOrThrow(Registries.PLACED_FEATURE);

            Holder.Reference<PlacedFeature> jade = lookup.getOrThrow(SFDPlacements.JADE);
            Holder.Reference<PlacedFeature> niter = lookup.getOrThrow(SFDPlacements.NITER);
            Holder.Reference<PlacedFeature> sulphur = lookup.getOrThrow(SFDPlacements.SULPHUR);
            Holder.Reference<PlacedFeature> rice = lookup.getOrThrow(SFDPlacements.RICE);
            Holder.Reference<PlacedFeature> rehmannia = lookup.getOrThrow(SFDPlacements.REHMANNIA);
            Holder.Reference<PlacedFeature> dragonliverMelon = lookup.getOrThrow(SFDPlacements.DRAGONLIVER_MELON);
HolderSet.Named
            map.put(SFDPlacements.JADE.location(), createOverworldOre(jade));
            map.put(SFDPlacements.NITER.location(), createOverworldOre(niter));
            map.put(SFDPlacements.SULPHUR.location(), createOverworldOre(sulphur));
            map.put(SFDPlacements.RICE.location(), createVegetal(SFDBiomeTags.SPAWN_RICE, rice));
            map.put(SFDPlacements.REHMANNIA.location(), createVegetal(SFDBiomeTags.SPAWN_REHMANNIA, rehmannia));
            map.put(SFDPlacements.DRAGONLIVER_MELON.location(), createVegetal(SFDBiomeTags.SPAWN_DRAGONLIVER_MELON, dragonliverMelon));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return map;
    }

    private static BiomeModifier createOverworldOre(Holder<PlacedFeature> feature) {
        return new TaggedBiomeFeatureModifier(BiomeTags.IS_OVERWORLD, HolderSet.direct(feature), GenerationStep.Decoration.UNDERGROUND_ORES);
    }

    private static BiomeModifier createVegetal(TagKey<Biome> tag, Holder<PlacedFeature> feature) {
        return new TaggedBiomeFeatureModifier(tag, HolderSet.direct(feature), GenerationStep.Decoration.VEGETAL_DECORATION);
    }
}
