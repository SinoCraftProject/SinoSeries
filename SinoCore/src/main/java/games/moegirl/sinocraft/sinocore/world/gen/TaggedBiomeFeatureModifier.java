package games.moegirl.sinocraft.sinocore.world.gen;

import com.mojang.serialization.Codec;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderSet;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.minecraftforge.common.world.BiomeGenerationSettingsBuilder;
import net.minecraftforge.common.world.BiomeModifier;
import net.minecraftforge.common.world.ModifiableBiomeInfo;

/**
 * Forge 的 AddFeaturesBiomeModifier 只能对已知 Biome 添加，但我要根据 Tag 添加
 *
 * @author luqin2007
 */
public record TaggedBiomeFeatureModifier(TagKey<Biome> tag, HolderSet<PlacedFeature> features, GenerationStep.Decoration step) implements BiomeModifier {

    @Override
    public void modify(Holder<Biome> biome, Phase phase, ModifiableBiomeInfo.BiomeInfo.Builder builder) {
        if (phase == Phase.ADD && biome.is(tag)) {
            BiomeGenerationSettingsBuilder settings = builder.getGenerationSettings();
            features.forEach(holder -> settings.addFeature(step, holder));
        }
    }

    @Override
    public Codec<? extends BiomeModifier> codec() {
        return SCBiomeModifiers.TAGGED_BIOME_FEATURE_MODIFIER_CODEC.get();
    }
}
