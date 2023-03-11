package games.moegirl.sinocraft.sinocore.old.utility.texture;

public record PointEntry(String name, int x, int y) {

    public int u() {
        return x;
    }

    public int v() {
        return y;
    }

    public int w() {
        return x;
    }

    public int h() {
        return y;
    }
}
