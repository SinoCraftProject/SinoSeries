package games.moegirl.sinocraft.sinocore.utility.texture;

import javax.annotation.Nullable;

public record ProgressEntry(String name, int x, int y, @Nullable String texture, String textureFilled, boolean isVertical, boolean isOpposite) {
}
