package games.moegirl.sinocraft.sinocore.world.gen;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import games.moegirl.sinocraft.sinocore.SinoCore;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.minecraftforge.common.world.BiomeModifier;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

/**
 * @author luqin2007
 */
public class SFDBiomeModifiers {

    public static final DeferredRegister<Codec<? extends BiomeModifier>> BIOME_MODIFIER_SERIALIZERS =
            DeferredRegister.create(ForgeRegistries.Keys.BIOME_MODIFIER_SERIALIZERS, SinoCore.MODID);

    public static final RegistryObject<Codec<TaggedBiomeFeatureModifier>> TAGGED_BIOME_FEATURE_MODIFIER_CODEC =
            BIOME_MODIFIER_SERIALIZERS.register("tagged_biome_feature", () -> RecordCodecBuilder.create(builder -> builder.group(
                    TagKey.codec(ForgeRegistries.Keys.BIOMES).fieldOf("tag").forGetter(TaggedBiomeFeatureModifier::tag),
                    PlacedFeature.LIST_CODEC.fieldOf("features").forGetter(TaggedBiomeFeatureModifier::features),
                    GenerationStep.Decoration.CODEC.fieldOf("step").forGetter(TaggedBiomeFeatureModifier::step)
            ).apply(builder, TaggedBiomeFeatureModifier::new)));

    public static void register(IEventBus bus) {
        BIOME_MODIFIER_SERIALIZERS.register(bus);
    }
}
