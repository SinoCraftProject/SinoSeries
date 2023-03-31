package games.moegirl.sinocraft.sinocalligraphy.drawing.data;

public enum DrawingDataVersion {
    V1(1, true),
    ;

    private final int version;
    private final boolean latest;

    private DrawingDataVersion(int version, boolean latest) {
        this.version = version;
        this.latest = latest;
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
}
