package games.moegirl.sinocraft.sinocore.gui.widgets.entry;

import com.mojang.datafixers.util.Either;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public final class ButtonEntry extends AbstractWidgetEntry {

    public static final Codec<ButtonEntry> CODEC = RecordCodecBuilder.create(instance -> instance.group(
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
                    Codec.STRING.optionalFieldOf("tooltip", null).forGetter(ButtonEntry::tooltip))
            .apply(instance, ButtonEntry::new));

    private final int x, y;
    private final int w, h;
    private final List<String> texture;
    private final List<String> textureHover;
    private final List<String> texturePressed;
    private final List<String> textureDisable;
    private final @Nullable String tooltip;

    ButtonEntry(List<Integer> position, List<Integer> size, Either<String, List<String>> texture,
                Either<String, List<String>> textureHover, Either<String, List<String>> texturePressed,
                Either<String, List<String>> textureDisable, @Nullable String tooltip) {
        super("button");
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

    public Component getTooltip() {
        return tooltip == null ? CommonComponents.EMPTY : Component.translatable(tooltip);
    }

    @Nullable
    private String tooltip() {
        return tooltip;
    }
}
