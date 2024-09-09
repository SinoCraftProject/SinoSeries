package games.moegirl.sinocraft.sinobrush.utility;

import net.minecraft.core.component.DataComponents;
import net.minecraft.util.FastColor;
import net.minecraft.world.item.ItemStack;

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

    public static int getColor(ItemStack stack) {
        var component = stack.get(DataComponents.DYED_COLOR);
        if (component != null) {
            return component.rgb();
        }

        return -1;
    }
}
