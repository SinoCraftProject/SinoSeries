package games.moegirl.sinocraft.sinobrush.utility;

import net.minecraft.util.FastColor;

public class ColorHelper {
    public static int rgbToARGB(int rgb) {
        var a = 255;
        var r = FastColor.ARGB32.red(rgb);
        var g = FastColor.ARGB32.green(rgb);
        var b = FastColor.ARGB32.blue(rgb);
        return FastColor.ARGB32.color(a, r, g, b);
    }

    public static int pixelColorToARGB(byte color, int inkColor) {
        var a = color * 17;
        var r = FastColor.ARGB32.red(inkColor);
        var g = FastColor.ARGB32.green(inkColor);
        var b = FastColor.ARGB32.blue(inkColor);
        return FastColor.ARGB32.color(a, r, g, b);
    }
}
