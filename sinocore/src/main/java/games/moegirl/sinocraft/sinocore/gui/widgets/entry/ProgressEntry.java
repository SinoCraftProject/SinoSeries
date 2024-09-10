package games.moegirl.sinocraft.sinocore.gui.widgets.entry;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

import java.util.List;
import java.util.Optional;

public final class ProgressEntry extends AbstractWidgetEntry {

    public static final MapCodec<ProgressEntry> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
                    Codec.INT.listOf().fieldOf("position").forGetter(b -> List.of(b.getX(), b.getY())),
                    Codec.INT.listOf().fieldOf("size").forGetter(b -> List.of(b.getWidth(), b.getHeight())),
                    Codec.STRING.optionalFieldOf("texture").forGetter(ProgressEntry::getTexture),
                    Codec.STRING.fieldOf("texture_filled").forGetter(ProgressEntry::getTextureFilled),
                    Codec.STRING.optionalFieldOf("direction", "horizontal").forGetter(ProgressEntry::direction))
            .apply(instance, ProgressEntry::new));

    private final int x, y, width, height;
    private final Optional<String> texture;
    private final String textureFilled;
    private final boolean isVertical;
    private final boolean isOpposite;

    ProgressEntry(List<Integer> pos, List<Integer> size, Optional<String> texture, String textureFilled, String direction) {
        this.x = pos.get(0);
        this.y = pos.get(1);
        this.width = size.get(0);
        this.height = size.get(1);
        this.texture = texture;
        this.textureFilled = textureFilled;

        if (direction != null) {
            isVertical = direction.endsWith("vertical");
            isOpposite = direction.startsWith("-");
        } else {
            isVertical = isOpposite = false;
        }
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public Optional<String> getTexture() {
        return texture;
    }

    public String getTextureFilled() {
        return textureFilled;
    }

    public boolean isVertical() {
        return isVertical;
    }

    public boolean isOpposite() {
        return isOpposite;
    }

    private String direction() {
        return isVertical ? isOpposite ? "-vertical" : "vertical" : isOpposite ? "-horizontal" : "horizontal";
    }
}
