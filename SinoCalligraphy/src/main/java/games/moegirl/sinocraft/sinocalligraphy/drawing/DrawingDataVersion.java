package games.moegirl.sinocraft.sinocalligraphy.drawing;

import games.moegirl.sinocraft.sinocalligraphy.drawing.simple.ISimpleDrawing;
import games.moegirl.sinocraft.sinocalligraphy.drawing.simple.SimpleDrawing;
import net.minecraft.nbt.CompoundTag;

import java.util.function.Function;
import java.util.function.Supplier;

public enum DrawingDataVersion {
    V1(1, true, () -> new SimpleDrawing(32), SimpleDrawing::from),
    ;

    private final int version;
    private final boolean latest;
    private final Supplier<ISimpleDrawing> createFunction;
    private final Function<CompoundTag, ISimpleDrawing> fromTagFunction;

    private DrawingDataVersion(int version, boolean latest,
                               Supplier<ISimpleDrawing> createFunction,
                               Function<CompoundTag, ISimpleDrawing> fromTagFunction) {
        this.version = version;
        this.latest = latest;

        this.createFunction = createFunction;
        this.fromTagFunction = fromTagFunction;
    }

    public int getVersionNumber() {
        return version;
    }

    public boolean isLatest() {
        return latest;
    }

    public static DrawingDataVersion fromVersion(int version) {
        for (var v : values()) {
            if (v.getVersionNumber() == version) {
                return v;
            }
        }
        return getLatest();
    }

    public static DrawingDataVersion getLatest() {
        return V1;
    }

    public ISimpleDrawing fromTag(CompoundTag tag) {
        return fromTagFunction.apply(tag);
    }

    public ISimpleDrawing create() {
        return createFunction.get();
    }
}
