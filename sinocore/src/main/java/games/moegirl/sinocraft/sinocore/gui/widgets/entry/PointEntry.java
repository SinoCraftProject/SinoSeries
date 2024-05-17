package games.moegirl.sinocraft.sinocore.gui.widgets.entry;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

import java.util.List;

public final class PointEntry extends AbstractWidgetEntry {

    public static final Codec<PointEntry> CODEC = RecordCodecBuilder.create(instance -> instance.group(
                    Codec.INT.listOf().fieldOf("position").forGetter(b -> List.of(b.getX(), b.getY())))
            .apply(instance, PointEntry::new));

    private final int x, y;

    PointEntry(List<Integer> xy) {
        this.x = xy.get(0);
        this.y = xy.get(1);
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
}
