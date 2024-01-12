package games.moegirl.sinocraft.sinobrush.drawing;

public enum DrawingVersion {
    V1(1),
    ;

    private final int version;

    DrawingVersion(int version) {
        this.version = version;
    }

    public int getVersion() {
        return version;
    }

    public static DrawingVersion from(int version) {
        for (var value : values()) {
            if (value.getVersion() == version) {
                return value;
            }
        }

        return latest();
    }

    public static DrawingVersion latest() {
        return V1;
    }
}
