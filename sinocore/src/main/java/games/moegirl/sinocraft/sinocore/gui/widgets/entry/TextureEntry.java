package games.moegirl.sinocraft.sinocore.gui.widgets.entry;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

import java.util.List;

public final class TextureEntry extends AbstractWidgetEntry {

    public static final Codec<TextureEntry> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            Codec.INT.listOf().fieldOf("uv").forGetter(e -> List.of(e.getTextureX(), e.getTextureY())),
            Codec.INT.listOf().fieldOf("size").forGetter(e -> List.of(e.getWidth(), e.getHeight())),
            Codec.INT.listOf().optionalFieldOf("uv_size", List.of()).forGetter(e -> List.of(e.getTextureWidth(), e.getTextureHeight())),
            Codec.INT.listOf().optionalFieldOf("position", List.of()).forGetter(e -> List.of(e.getX(), e.getY()))
    ).apply(instance, TextureEntry::new));

    private final int x;
    private final int y;
    private final int w;
    private final int h;
    private final int tx;
    private final int ty;
    private final int tw;
    private final int th;

    TextureEntry(List<Integer> uv, List<Integer> size, List<Integer> uvSize, List<Integer> position) {
        super("texture");
        this.x = (position.isEmpty() ? uv : position).get(0);
        this.y = (position.isEmpty() ? uv : position).get(1);
        this.w = size.get(0);
        this.h = size.get(1);
        this.tx = uv.get(0);
        this.ty = uv.get(1);
        this.tw = (uvSize.isEmpty() ? size : uvSize).get(0);
        this.th = (uvSize.isEmpty() ? size : uvSize).get(1);
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
}
