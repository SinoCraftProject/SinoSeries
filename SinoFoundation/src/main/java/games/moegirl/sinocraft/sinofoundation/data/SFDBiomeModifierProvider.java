package games.moegirl.sinocraft.sinofoundation.data;

import com.mojang.serialization.JsonOps;
import games.moegirl.sinocraft.sinofoundation.SinoFoundation;
import games.moegirl.sinocraft.sinofoundation.world.SFDFeatures;
import games.moegirl.sinocraft.sinocore.world.gen.TaggedBiomeFeatureModifier;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.HolderSet;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.PackType;
import net.minecraft.tags.BiomeTags;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.common.data.JsonCodecProvider;
import net.minecraftforge.common.world.BiomeModifier;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;


/**
 * 用于生成 BiomeModifier，必须在 {@link SFDDatapackProvider} 之后调用
 *
 * @author luqin2007
 */
class SFDBiomeModifierProvider extends JsonCodecProvider<BiomeModifier> {

    public SFDBiomeModifierProvider(PackOutput output, ExistingFileHelper helper, CompletableFuture<HolderLookup.Provider> provider) {
        super(output, helper, SinoFoundation.MODID, JsonOps.INSTANCE, PackType.SERVER_DATA,
                "forge/biome_modifier", BiomeModifier.DIRECT_CODEC, buildModifiers(provider));
    }

    private static Map<ResourceLocation, BiomeModifier> buildModifiers(CompletableFuture<HolderLookup.Provider> lookupProvider) {
        Map<ResourceLocation, BiomeModifier> map = new HashMap<>();
        try {
            map.put(SFDFeatures.Placements.JADE.location(), createOverworldOre(SFDDatapackProvider.placedJade));
            map.put(SFDFeatures.Placements.NITER.location(), createOverworldOre(SFDDatapackProvider.placedNiter));
            map.put(SFDFeatures.Placements.SULPHUR.location(), createOverworldOre(SFDDatapackProvider.placedSulphur));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return map;
    }

    private static BiomeModifier createOverworldOre(Holder<PlacedFeature> feature) {
        return new TaggedBiomeFeatureModifier(BiomeTags.IS_OVERWORLD, HolderSet.direct(feature), GenerationStep.Decoration.UNDERGROUND_ORES);
    }
}
