package games.moegirl.sinocraft.sinocore.old.api.utility.texture;

public record Rect(float x0, float y0, float z0,
                   float x1, float y1, float z1,
                   float x2, float y2, float z2,
                   float x3, float y3, float z3) {

    public static Rect of(float x0, float y0, float z0, float x1, float y1, float z1) {
        return new Rect(
                x0, y0, z0,
                x1, y1, z0,
                x1, y1, z1,
                x0, y0, z1);
    }

    public static Rect xz(float x, float y, float z, float xSize, float zSize) {
        return new Rect(
                x, y, z,
                x, y, z + zSize,
                x + xSize, y, z + zSize,
                x + xSize, y, z);
    }

    public static Rect xy(float x, float y, float z, float xSize, float ySize) {
        return new Rect(
                x, y, z,
                x, y + ySize, z,
                x + xSize, y + ySize, z,
                x + xSize, y, z);
    }

    public static Rect yz(float x, float y, float z, float ySize, float zSize) {
        return new Rect(
                x, y, z,
                x, y, z + zSize,
                x, y + ySize, z + zSize,
                x, y + ySize, z);
    }


}
