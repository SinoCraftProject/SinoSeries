package games.moegirl.sinocraft.sinocore.old.api.utility.texture;

public record TextureEntry(String name, int x, int y, int w, int h, float u, float v, int tw, int th) {

    // [u0, u1, v0, v1]
    public float[] normalized(float width, float height) {
        return new float[]{u / width, (u + tw) / width, v / height, (v + th) / height};
    }
}
