package games.moegirl.sinocraft.sinobrush.item.component;

import com.google.common.primitives.Bytes;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import games.moegirl.sinocraft.sinobrush.SBRConstants;
import games.moegirl.sinocraft.sinobrush.drawing.DrawingVersion;
import games.moegirl.sinocraft.sinobrush.drawing.MutableDrawing;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.ComponentSerialization;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import org.jetbrains.annotations.NotNull;

import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;

public record Drawing(int version, Component title, Component author,
                      long date, byte[] pixels,
                      int width, int height,
                      int paperColor, int inkColor) {
    public Drawing() {
        this(DrawingVersion.latest().getVersion(),
                Component.translatable(SBRConstants.Translation.DRAWING_TITLE_UNKNOWN),
                Component.translatable(SBRConstants.Translation.DRAWING_AUTHOR_UNKNOWN),
                1680220800, new byte[0], 16, 16, SBRConstants.COLOR_WHITE, SBRConstants.COLOR_BLACK);
    }

    public Drawing(int version, Component title, Component author,
                   long date, List<Byte> pixels,
                   int width, int height,
                   int paperColor, int inkColor) {
        this(version, title, author, date, Bytes.toArray(pixels), width, height, paperColor, inkColor);
    }

    public static final Codec<Drawing> CODEC = RecordCodecBuilder.create(
            instance -> instance.group(
                    Codec.INT.fieldOf("version").forGetter(Drawing::version),
                    ComponentSerialization.CODEC.fieldOf("title").forGetter(Drawing::title),
                    ComponentSerialization.CODEC.fieldOf("author").forGetter(Drawing::author),
                    Codec.LONG.fieldOf("date").forGetter(Drawing::date),
                    Codec.BYTE.listOf().fieldOf("pixels").forGetter(d -> Bytes.asList(d.pixels())),
                    Codec.INT.fieldOf("width").forGetter(Drawing::width),
                    Codec.INT.fieldOf("height").forGetter(Drawing::height),
                    Codec.INT.fieldOf("paperColor").forGetter(Drawing::paperColor),
                    Codec.INT.fieldOf("inkColor").forGetter(Drawing::inkColor)
            ).apply(instance, Drawing::new)
    );

    public static final StreamCodec<RegistryFriendlyByteBuf, Drawing> STREAM_CODEC = new StreamCodec<>() {
        @Override
        public @NotNull Drawing decode(RegistryFriendlyByteBuf buf) {
            return new Drawing(ByteBufCodecs.VAR_INT.decode(buf),
                    ComponentSerialization.STREAM_CODEC.decode(buf),
                    ComponentSerialization.STREAM_CODEC.decode(buf),
                    ByteBufCodecs.VAR_LONG.decode(buf),
                    ByteBufCodecs.BYTE_ARRAY.decode(buf),
                    ByteBufCodecs.VAR_INT.decode(buf),
                    ByteBufCodecs.VAR_INT.decode(buf),
                    ByteBufCodecs.VAR_INT.decode(buf),
                    ByteBufCodecs.VAR_INT.decode(buf));
        }

        @Override
        public void encode(RegistryFriendlyByteBuf buf, Drawing drawing) {
            ByteBufCodecs.VAR_INT.encode(buf, drawing.version());
            ComponentSerialization.STREAM_CODEC.encode(buf, drawing.title());
            ComponentSerialization.STREAM_CODEC.encode(buf, drawing.author());
            ByteBufCodecs.VAR_LONG.encode(buf, drawing.date());
            ByteBufCodecs.BYTE_ARRAY.encode(buf, drawing.pixels());
            ByteBufCodecs.VAR_INT.encode(buf, drawing.width());
            ByteBufCodecs.VAR_INT.encode(buf, drawing.height());
            ByteBufCodecs.VAR_INT.encode(buf, drawing.paperColor());
            ByteBufCodecs.VAR_INT.encode(buf, drawing.inkColor());
        }
    };

    public MutableDrawing toMutable() {
        return new MutableDrawing();
    }

    public ZonedDateTime getZonedDate() {
        var instant = Instant.ofEpochSecond(date());
        return ZonedDateTime.ofInstant(instant, ZoneId.systemDefault());
    }

    public boolean isEmpty() {
        for (var l : pixels) {
            if (l != 0) {
                return false;
            }
        }

        return true;
    }

    public byte getPixel(int index) {
        if (pixels().length <= index) {
            return 0;
        }

        return pixels()[index];
    }

    public byte getPixel(int x, int y) {
        return getPixel(x * width() + y);
    }
}
