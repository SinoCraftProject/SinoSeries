package games.moegirl.sinocraft.sinofeast.taste;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import games.moegirl.sinocraft.sinofeast.SinoFeast;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;

public class TasteCodec {
    public static final Codec<Taste> TASTE_CODEC = RecordCodecBuilder.create(builder ->
            builder.group(
                    Codec.STRING.fieldOf("name").forGetter(Taste::key),
                    Codec.BOOL.fieldOf("is_advanced").forGetter(Taste::isAdvanced),
                    Codec.INT.fieldOf("like_weight").forGetter(Taste::likeWeight),
                    Codec.INT.fieldOf("dislike_weight").forGetter(Taste::dislikeWeight),
                    TagKey.codec(Registries.ITEM).fieldOf("taste_key").forGetter(Taste::tasteKey),
                    TagKey.codec(Registries.ITEM).fieldOf("taste_key_primary").forGetter(Taste::tasteKeyPrimary),
                    TagKey.codec(Registries.ITEM).fieldOf("taste_key_secondary").forGetter(Taste::tasteKeySecondary)
            ).apply(builder, Taste::new)
    );
    public static final ResourceKey<Registry<Taste>> TASTE_KEY = ResourceKey.createRegistryKey(
            new ResourceLocation(SinoFeast.MODID, "food_tastes"));
}
