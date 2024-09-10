package games.moegirl.sinocraft.sinocore.gui.widgets.entry;

import com.mojang.datafixers.util.Either;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

import java.util.List;
import java.util.Optional;

public final class ButtonEntry extends AbstractWidgetEntry {

    public static final MapCodec<ButtonEntry> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
                    Codec.INT.listOf().fieldOf("position").forGetter(b -> List.of(b.getX(), b.getY())),
                    Codec.INT.listOf().fieldOf("size").forGetter(b -> List.of(b.getWidth(), b.getHeight())),
                    Codec.either(Codec.STRING, Codec.STRING.listOf())
                            .optionalFieldOf("texture", Either.right(List.of()))
                            .forGetter(e -> Either.right(e.getTexture())),
                    Codec.either(Codec.STRING, Codec.STRING.listOf())
                            .optionalFieldOf("texture_hover", Either.right(List.of()))
                            .forGetter(e -> Either.right(e.getTextureHover())),
                    Codec.either(Codec.STRING, Codec.STRING.listOf())
                            .optionalFieldOf("texture_pressed", Either.right(List.of()))
                            .forGetter(e -> Either.right(e.getTexturePressed())),
                    Codec.either(Codec.STRING, Codec.STRING.listOf())
                            .optionalFieldOf("texture_disable", Either.right(List.of()))
                            .forGetter(e -> Either.right(e.getTextureDisable())),
                    Codec.STRING.optionalFieldOf("tooltip").forGetter(ButtonEntry::getTooltip))
            .apply(instance, ButtonEntry::new));

    private final int x, y;
    private final int w, h;
    private final List<String> texture;
    private final List<String> textureHover;
    private final List<String> texturePressed;
    private final List<String> textureDisable;
    private final Optional<String> tooltip;

    ButtonEntry(List<Integer> position, List<Integer> size, Either<String, List<String>> texture,
                Either<String, List<String>> textureHover, Either<String, List<String>> texturePressed,
                Either<String, List<String>> textureDisable, Optional<String> tooltip) {
        this.x = position.get(0);
        this.y = position.get(1);
        this.w = size.get(0);
        this.h = size.get(1);
        this.texture = texture.map(List::of, List::copyOf);
        this.textureHover = textureHover.map(List::of, List::copyOf);
        this.texturePressed = texturePressed.map(List::of, List::copyOf);
        this.textureDisable = textureDisable.map(List::of, List::copyOf);
        this.tooltip = tooltip;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getWidth() {
        return w;
    }

    public int getHeight() {
        return h;
    }

    public List<String> getTexture() {
        return texture;
    }

    public List<String> getTextureHover() {
        return textureHover;
    }

    public List<String> getTexturePressed() {
        return texturePressed;
    }

    public List<String> getTextureDisable() {
        return textureDisable;
    }

    public Optional<String> getTooltip() {
        return tooltip;
    }
}
