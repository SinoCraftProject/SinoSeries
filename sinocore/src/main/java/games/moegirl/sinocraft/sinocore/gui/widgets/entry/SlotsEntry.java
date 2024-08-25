package games.moegirl.sinocraft.sinocore.gui.widgets.entry;

import com.google.common.collect.ImmutableList;
import com.mojang.datafixers.util.Either;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

import java.util.Arrays;
import java.util.List;

public final class SlotsEntry extends AbstractWidgetEntry {

    public static final MapCodec<SlotsEntry> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
            Codec.STRING.optionalFieldOf("direction", "horizontal").forGetter(SlotsEntry::direction),
            Codec.INT.optionalFieldOf("size", 18).forGetter(SlotsEntry::getSize),
            Codec.either(Codec.INT, Codec.INT.listOf())
                    .optionalFieldOf("count", Either.left(1))
                    .forGetter(e -> Either.right(Arrays.stream(e.getCounts()).boxed().toList())),
            Codec.INT.listOf().fieldOf("position").forGetter(e -> List.of(e.getX(), e.getY())),
            Codec.either(Codec.INT, Codec.INT.listOf())
                    .optionalFieldOf("offset", Either.left(0))
                    .forGetter(e -> Either.right(Arrays.stream(e.getOffsets()).boxed().toList())))
            .apply(instance, SlotsEntry::new));

    private final boolean isVertical;
    private final int size, x, y;
    private final int[] counts;
    private final int[] offsets;
    private final List<SlotEntry> slots;

    SlotsEntry(String direction, int size, Either<Integer, List<Integer>> count, List<Integer> position,
               Either<Integer, List<Integer>> offset) {
        this.isVertical = "vertical".equals(direction);
        this.size = size;
        this.counts = count.map(i -> new int[] {1, i}, list -> list.stream().mapToInt(i -> i).toArray());
        this.x = position.get(0);
        this.y = position.get(1);
        this.offsets = offset.map(i -> new int[] {0, i}, list -> list.stream().mapToInt(i -> i).toArray());

        ImmutableList.Builder<SlotEntry> slots = ImmutableList.builder();
        for (int i = 0; i < counts[0]; i++) {
            for (int j = 0; j < counts[1]; j++) {
                if (isVertical) {
                    slots.add(new SlotEntry(size, List.of(x + i * size + i * offsets[0], y + j * size + j * offsets[1])));
                } else {
                    slots.add(new SlotEntry(size, List.of(x + j * size + j * offsets[0], y + i * size + i * offsets[1])));
                }
            }
        }
        this.slots = slots.build();
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

    public int[] getCounts() {
        return counts;
    }

    public int[] getOffsets() {
        return offsets;
    }

    public SlotEntry getSlot(int index) {
        return slots.get(index);
    }

    public int slotCount() {
        return slots.size();
    }

    private String direction() {
        return isVertical ? "vertical" : "horizontal";
    }
}
