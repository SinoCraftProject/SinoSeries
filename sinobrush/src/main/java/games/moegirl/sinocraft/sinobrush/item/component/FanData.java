package games.moegirl.sinocraft.sinobrush.item.component;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.ComponentSerialization;
import net.minecraft.network.codec.StreamCodec;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public record FanData(List<Component> lines) {
    public static final Codec<FanData> CODEC = RecordCodecBuilder.create(
            instance -> instance.group(
                    ComponentSerialization.CODEC.listOf().fieldOf("lines").forGetter(FanData::lines)
            ).apply(instance, FanData::new)
    );

    public static final StreamCodec<RegistryFriendlyByteBuf, FanData> STREAM_CODEC = new StreamCodec<>() {
        @Override
        public @NotNull FanData decode(RegistryFriendlyByteBuf buf) {
            var length = buf.readVarInt();
            var lines = new ArrayList<Component>();
            for (var i = 0; i < length; i++) {
                lines.add(ComponentSerialization.STREAM_CODEC.decode(buf));
            }
            return new FanData(lines);
        }

        @Override
        public void encode(RegistryFriendlyByteBuf buf, FanData data) {
            buf.writeVarInt(data.lines().size());
            for (var l : data.lines()) {
                ComponentSerialization.STREAM_CODEC.encode(buf, l);
            }
        }
    };

    public FanData() {
        this(List.of());
    }
}
