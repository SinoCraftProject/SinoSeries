package games.moegirl.sinocraft.sinocore.gui.widgets.entry;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.client.gui.components.Tooltip;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import org.jetbrains.annotations.Nullable;

import java.util.List;

/**
 * @author luqin2007
 */
public final class EditBoxEntry extends AbstractWidgetEntry {

    public static final Codec<EditBoxEntry> CODEC = RecordCodecBuilder.create(instance -> instance.group(
                    Codec.INT.listOf().fieldOf("position").forGetter(b -> List.of(b.getX(), b.getY())),
                    Codec.INT.listOf().fieldOf("size").forGetter(b -> List.of(b.getWidth(), b.getHeight())),
                    Codec.STRING.optionalFieldOf("title", null).forGetter(EditBoxEntry::title),
                    Codec.STRING.optionalFieldOf("hint", null).forGetter(EditBoxEntry::hint),
                    Codec.INT.optionalFieldOf("max_length", 32).forGetter(EditBoxEntry::getMaxLength),
                    Codec.STRING.optionalFieldOf("suggestion", null).forGetter(EditBoxEntry::getSuggestion),
                    Codec.STRING.optionalFieldOf("default", "").forGetter(EditBoxEntry::getDefaultValue),
                    Codec.INT.optionalFieldOf("color", 0xE0E0E0).forGetter(EditBoxEntry::getColor),
                    Codec.INT.optionalFieldOf("color_uneditable", 0xE0E0E0).forGetter(EditBoxEntry::getUneditableColor),
                    Codec.floatRange(0.0F, 1.0F).optionalFieldOf("alpha", 1.0F).forGetter(EditBoxEntry::getAlpha),
                    Codec.STRING.optionalFieldOf("tooltip", null).forGetter(EditBoxEntry::tooltip),
                    Codec.BOOL.optionalFieldOf("bordered", true).forGetter(EditBoxEntry::getBordered))
            .apply(instance, EditBoxEntry::new));

    private final int x;
    private final int y;
    private final int w;
    private final int h;
    private final @Nullable String title;
    private final @Nullable String hint;
    private final int maxLength;
    private final @Nullable String suggestion;
    private final String defVal;
    private final int color;
    private final int uneditableColor;
    private final float alpha;
    private final @Nullable String tooltip;
    private final boolean bordered;

    EditBoxEntry(List<Integer> position, List<Integer> size, @Nullable String title,
                 @Nullable String hint, int maxLength, @Nullable String suggestion, String defVal, int color,
                 int uneditableColor, float alpha, @Nullable String tooltip, boolean bordered) {
        super("editbox");
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

    @Nullable
    public String getSuggestion() {
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
        return title == null ? CommonComponents.EMPTY : Component.translatable(title);
    }

    @Nullable
    public Tooltip getTooltip() {
        return tooltip == null ? null : Tooltip.create(Component.translatable(tooltip));
    }

    @Nullable
    public Component getHint() {
        return hint == null ? null : Component.translatable(hint);
    }

    @Nullable
    private String title() {
        return title;
    }

    @Nullable
    private String tooltip() {
        return tooltip;
    }

    @Nullable
    private String hint() {
        return hint;
    }
}
