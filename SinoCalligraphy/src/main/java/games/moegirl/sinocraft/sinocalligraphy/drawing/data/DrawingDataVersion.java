package games.moegirl.sinocraft.sinocalligraphy.drawing.data;

import games.moegirl.sinocraft.sinocalligraphy.drawing.simple.ISimpleDrawing;
import games.moegirl.sinocraft.sinocalligraphy.drawing.simple.SimpleDrawing;
import net.minecraft.nbt.CompoundTag;

import java.util.function.Function;

public enum DrawingDataVersion {
    V1(1, true, SimpleDrawing::from),
    ;

    private final int version;
    private final boolean latest;
    private final Function<CompoundTag, ISimpleDrawing> fromTag;

    private DrawingDataVersion(int version, boolean latest, Function<CompoundTag, ISimpleDrawing> fromTag) {
        this.version = version;
        this.latest = latest;

        this.fromTag = fromTag;
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
        return fromTag.apply(tag);
    }
}
