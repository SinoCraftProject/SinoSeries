package games.moegirl.sinocraft.sinocore.gui.widgets.entry;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

import java.util.List;
import java.util.Optional;

public final class TextEntry extends AbstractWidgetEntry {

    public static final MapCodec<TextEntry> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
                    Codec.INT.listOf().fieldOf("position").forGetter(e -> List.of(e.getX(), e.getY())),
                    Codec.INT.optionalFieldOf("color", 0x808080).forGetter(TextEntry::getColor),
                    Codec.STRING.optionalFieldOf("text").forGetter(TextEntry::getText),
                    Codec.STRING.optionalFieldOf("rawText").forGetter(TextEntry::getRawText),
                    Codec.BOOL.optionalFieldOf("shadow", false).forGetter(TextEntry::hasShadow),
                    Codec.BOOL.optionalFieldOf("center", false).forGetter(TextEntry::isCenter))
            .apply(instance, TextEntry::new));

    private final int x;
    private final int y;
    private final int color;
    private final Optional<String> text;
    private final Optional<String> rawText;
    private final boolean shadow;
    private final boolean center;

    TextEntry(List<Integer> position, int color, Optional<String> text, Optional<String> rawText,
              boolean shadow, boolean center) {
        this.x = position.get(0);
        this.y = position.get(1);
        this.color = color;
        this.text = text;
        this.rawText = rawText;
        this.shadow = shadow;
        this.center = center;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getColor() {
        return color;
    }

    public Optional<String> getText() {
        return text;
    }

    public Optional<String> getRawText() {
        return rawText;
    }

    public boolean hasShadow() {
        return shadow;
    }

    public boolean isCenter() {
        return center;
    }
}
