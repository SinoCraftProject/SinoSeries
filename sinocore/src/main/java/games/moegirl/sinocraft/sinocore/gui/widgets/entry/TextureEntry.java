package games.moegirl.sinocraft.sinocore.gui.widgets.entry;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

import java.util.List;
import java.util.Optional;

public final class TextureEntry extends AbstractWidgetEntry {

    public static final MapCodec<TextureEntry> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
            Codec.INT.listOf().fieldOf("uv").forGetter(e -> List.of(e.getTextureX(), e.getTextureY())),
            Codec.INT.listOf().fieldOf("size").forGetter(e -> List.of(e.getWidth(), e.getHeight())),
            Codec.INT.listOf().optionalFieldOf("uv_size").forGetter(TextureEntry::uvSize),
            Codec.INT.listOf().optionalFieldOf("position").forGetter(TextureEntry::position)
    ).apply(instance, TextureEntry::new));

    private final int x;
    private final int y;
    private final int w;
    private final int h;
    private final int tx;
    private final int ty;
    private final int tw;
    private final int th;

    TextureEntry(List<Integer> uv, List<Integer> size, Optional<List<Integer>> uvSize, Optional<List<Integer>> position) {
        this.x = position.orElse(uv).get(0);
        this.y = position.orElse(uv).get(1);
        this.w = size.get(0);
        this.h = size.get(1);
        this.tx = uv.get(0);
        this.ty = uv.get(1);
        this.tw = uvSize.orElse(size).get(0);
        this.th = uvSize.orElse(size).get(1);
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getWidth() {
        return w;
    }

    public int getHeight() {
        return h;
    }

    public int getTextureX() {
        return tx;
    }

    public int getTextureY() {
        return ty;
    }

    public int getTextureWidth() {
        return tw;
    }

    public int getTextureHeight() {
        return th;
    }

    // [u0, u1, v0, v1]
    public float[] normalized(float width, float height) {
        return new float[]{(float) tx / width, (float) (tx + tw) / width, (float) ty / height, (float) (ty + th) / height};
    }

    private Optional<List<Integer>> uvSize() {
        return tw == w && th == h ? Optional.empty() : Optional.of(List.of(tw, th));
    }

    private Optional<List<Integer>> position() {
        return x == tx && y == ty ? Optional.empty() : Optional.of(List.of(x, y));
    }
}
