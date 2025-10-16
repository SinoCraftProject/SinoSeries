package games.moegirl.sinocraft.sinocore.gui.widgets.entry;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.client.gui.components.Tooltip;
import net.minecraft.network.chat.Component;

import java.util.List;
import java.util.Optional;

/**
 * @author luqin2007
 */
public final class EditBoxEntry extends AbstractWidgetEntry {

    public static final MapCodec<EditBoxEntry> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
                    Codec.INT.listOf().fieldOf("position").forGetter(b -> List.of(b.getX(), b.getY())),
                    Codec.INT.listOf().fieldOf("size").forGetter(b -> List.of(b.getWidth(), b.getHeight())),
                    Codec.STRING.optionalFieldOf("title").forGetter(EditBoxEntry::title),
                    Codec.STRING.optionalFieldOf("hint").forGetter(EditBoxEntry::hint),
                    Codec.INT.optionalFieldOf("max_length", 32).forGetter(EditBoxEntry::getMaxLength),
                    Codec.STRING.optionalFieldOf("suggestion").forGetter(EditBoxEntry::getSuggestion),
                    Codec.STRING.optionalFieldOf("default", "").forGetter(EditBoxEntry::getDefaultValue),
                    Codec.INT.optionalFieldOf("color", 0xE0E0E0).forGetter(EditBoxEntry::getColor),
                    Codec.INT.optionalFieldOf("color_uneditable", 0xE0E0E0).forGetter(EditBoxEntry::getUneditableColor),
                    Codec.floatRange(0.0F, 1.0F).optionalFieldOf("alpha", 1.0F).forGetter(EditBoxEntry::getAlpha),
                    Codec.STRING.optionalFieldOf("tooltip").forGetter(EditBoxEntry::tooltip),
                    Codec.BOOL.optionalFieldOf("bordered", true).forGetter(EditBoxEntry::getBordered))
            .apply(instance, EditBoxEntry::new));

    private final int x;
    private final int y;
    private final int w;
    private final int h;
    private final Optional<String> title;
    private final Optional<String> hint;
    private final int maxLength;
    private final Optional<String> suggestion;
    private final String defVal;
    private final int color;
    private final int uneditableColor;
    private final float alpha;
    private final Optional<String> tooltip;
    private final boolean bordered;

    EditBoxEntry(List<Integer> position, List<Integer> size, Optional<String> title,
                 Optional<String> hint, int maxLength, Optional<String> suggestion, String defVal, int color,
                 int uneditableColor, float alpha, Optional<String> tooltip, boolean bordered) {
        this.x = position.get(0);
        this.y = position.get(1);
        this.w = size.get(0);
        this.h = size.get(1);
        this.title = title;
        this.hint = hint;
        this.maxLength = maxLength;
        this.suggestion = suggestion;
        this.defVal = defVal;
        this.color = color;
        this.uneditableColor = uneditableColor;
        this.alpha = alpha;
        this.tooltip = tooltip;
        this.bordered = bordered;
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

    public int getMaxLength() {
        return maxLength;
    }

    public Optional<String> getSuggestion() {
        return suggestion;
    }

    public String getDefaultValue() {
        return defVal;
    }

    public int getColor() {
        return color;
    }

    public int getUneditableColor() {
        return uneditableColor;
    }

    public float getAlpha() {
        return alpha;
    }

    public boolean getBordered() {
        return bordered;
    }

    public Component getTitle() {
        return title.map(Component::translatable).orElseGet(Component::empty);
    }

    public Optional<Tooltip> getTooltip() {
        return tooltip.map(Component::translatable).map(Tooltip::create);
    }

    public Optional<Component> getHint() {
        return hint.map(Component::translatable);
    }

    private Optional<String> title() {
        return title;
    }

    private Optional<String> tooltip() {
        return tooltip;
    }

    private Optional<String> hint() {
        return hint;
    }
}
