package games.moegirl.sinocraft.sinocore.gui.widgets.entry;

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
                    Codec.STRING.listOf().optionalFieldOf("texture", List.of()).forGetter(ButtonEntry::getTexture),
                    Codec.STRING.listOf().optionalFieldOf("texture_hover", List.of()).forGetter(ButtonEntry::getTextureHover),
                    Codec.STRING.listOf().optionalFieldOf("texture_pressed", List.of()).forGetter(ButtonEntry::getTexturePressed),
                    Codec.STRING.listOf().optionalFieldOf("texture_disable", List.of()).forGetter(ButtonEntry::getTextureDisable),
                    Codec.STRING.optionalFieldOf("tooltip", null).forGetter(ButtonEntry::tooltip))
            .apply(instance, ButtonEntry::new));

    private final int x, y;
    private final int w, h;
    private final List<String> texture;
    private final List<String> textureHover;
    private final List<String> texturePressed;
    private final List<String> textureDisable;
    private final @Nullable String tooltip;

    ButtonEntry(List<Integer> position, List<Integer> size, List<String> texture, List<String> textureHover,
                List<String> texturePressed, List<String> textureDisable, @Nullable String tooltip) {
        super("button");
        this.x = position.get(0);
        this.y = position.get(1);
        this.w = size.get(0);
        this.h = size.get(1);
        this.texture = List.copyOf(texture);
        this.textureHover = List.copyOf(textureHover);
        this.texturePressed = List.copyOf(texturePressed);
        this.textureDisable = List.copyOf(textureDisable);
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
