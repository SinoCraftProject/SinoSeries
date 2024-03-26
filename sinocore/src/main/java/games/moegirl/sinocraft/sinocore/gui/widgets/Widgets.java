package games.moegirl.sinocraft.sinocore.gui.widgets;

import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import games.moegirl.sinocraft.sinocore.gui.widgets.entry.AbstractWidgetEntry;
import net.minecraft.resources.ResourceLocation;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class Widgets {

    public static final Codec<Widgets> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            Codec.INT.optionalFieldOf("width", 512).forGetter(Widgets::getWidth),
            Codec.INT.optionalFieldOf("height", 512).forGetter(Widgets::getHeight),
            Codec.compoundList(Codec.STRING, WidgetLoader.WIDGET_CODEC).fieldOf("widgets").forGetter(Widgets::widgets)
    ).apply(instance, Widgets::new));

    private final int width, height;
    private final List<Pair<String, AbstractWidgetEntry>> widgets;
    private final Map<String, AbstractWidgetEntry> widgetMap = new HashMap<>();

    private ResourceLocation texture;

    Widgets(int width, int height, List<Pair<String, AbstractWidgetEntry>> widgets) {
        this.width = width;
        this.height = height;
        this.widgets = List.copyOf(widgets);

        for (Pair<String, AbstractWidgetEntry> nameWidgetPair : widgets) {
            String name = nameWidgetPair.getFirst();
            AbstractWidgetEntry widget = nameWidgetPair.getSecond();
            widget.setName(name);
            widgetMap.put(name, widget);
        }
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public ResourceLocation getTexture() {
        return texture;
    }

    public void setTexture(ResourceLocation texture) {
        this.texture = texture;
    }

    public AbstractWidgetEntry getWidget(String name) {
        return Objects.requireNonNull(widgetMap.get(name));
    }

    public boolean containsWidget(String name) {
        return widgetMap.containsKey(name);
    }

    private List<Pair<String, AbstractWidgetEntry>> widgets() {
        return widgets;
    }
}
