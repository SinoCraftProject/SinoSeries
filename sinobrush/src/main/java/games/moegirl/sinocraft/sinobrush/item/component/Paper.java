package games.moegirl.sinocraft.sinobrush.item.component;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;

public record Paper(int expands) {
    public static final Codec<Paper> CODEC = RecordCodecBuilder.create(
            instance -> instance.group(
                    Codec.INT.optionalFieldOf("expands", 0).forGetter(Paper::expands)
            ).apply(instance, Paper::new)
    );

    public static final StreamCodec<RegistryFriendlyByteBuf, Paper> STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.VAR_INT, Paper::expands,
            Paper::new
    );

    public Paper() {
        this(0);
    }
}
