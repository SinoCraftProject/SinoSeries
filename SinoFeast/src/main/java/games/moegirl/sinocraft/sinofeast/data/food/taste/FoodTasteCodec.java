package games.moegirl.sinocraft.sinofeast.data.food.taste;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;

public class FoodTasteCodec {
    public static final Codec<FoodTaste> TASTE_CODEC = RecordCodecBuilder.create(builder ->
            builder.group(
                    ResourceLocation.CODEC.fieldOf("name").forGetter(FoodTaste::key),
                    Codec.BOOL.fieldOf("is_advanced").forGetter(FoodTaste::isAdvanced),
                    Codec.INT.fieldOf("like_weight").forGetter(FoodTaste::likeWeight),
                    Codec.INT.fieldOf("dislike_weight").forGetter(FoodTaste::dislikeWeight),
                    TagKey.codec(Registries.ITEM).fieldOf("taste_key").forGetter(FoodTaste::tasteKey),
                    TagKey.codec(Registries.ITEM).fieldOf("taste_key_primary").forGetter(FoodTaste::tasteKeyPrimary),
                    TagKey.codec(Registries.ITEM).fieldOf("taste_key_secondary").forGetter(FoodTaste::tasteKeySecondary)
            ).apply(builder, FoodTaste::new)
    );
}
