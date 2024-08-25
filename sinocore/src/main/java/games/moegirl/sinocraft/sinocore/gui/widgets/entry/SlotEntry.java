package games.moegirl.sinocraft.sinocore.gui.widgets.entry;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

import java.util.List;

public final class SlotEntry extends AbstractWidgetEntry {

    public static final MapCodec<SlotEntry> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
                    Codec.INT.optionalFieldOf("size", 18).forGetter(SlotEntry::getSize),
                    Codec.INT.listOf().fieldOf("position").forGetter(b -> List.of(b.getX(), b.getY())))
            .apply(instance, SlotEntry::new));

    private final int size, x, y;

    SlotEntry(int size, List<Integer> position) {
        this.size = size;
        this.x = position.get(0);
        this.y = position.get(1);
    }

    public int getSize() {
        return size;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
}
