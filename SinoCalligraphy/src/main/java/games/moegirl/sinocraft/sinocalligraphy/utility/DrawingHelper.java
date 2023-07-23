package games.moegirl.sinocraft.sinocalligraphy.utility;

import com.mojang.blaze3d.platform.NativeImage;
import games.moegirl.sinocraft.sinocalligraphy.drawing.InkType;
import games.moegirl.sinocraft.sinocalligraphy.drawing.simple.ISimpleDrawing;
import games.moegirl.sinocraft.sinocalligraphy.drawing.simple.traits.IHasInkType;
import games.moegirl.sinocraft.sinocalligraphy.drawing.simple.traits.IHasPaperColor;
import net.minecraft.util.FastColor;

public class DrawingHelper {

    public static int appendAlpha(int background) {
        return FastColor.ARGB32.color(255, FastColor.ARGB32.red(background), FastColor.ARGB32.green(background), FastColor.ARGB32.blue(background));
    }

    /// <editor-fold desc="Don't touch it, the algorithm which nobody understood.">

    public static int mix(int foreground, int background, int alpha) {
        var a = alpha * 0.00390625;

        return innerMix(foreground, background, (float) a);
    }

    private static int innerMix(int foreground, int background, float alpha) {
        return innerMix(foreground, background, 1 - alpha, alpha);
    }

    private static int innerMix(int foreground, int background, float fAlpha, float bAlpha) {
        var a = fAlpha + bAlpha - fAlpha * bAlpha;

        var aRed = (FastColor.ARGB32.red(foreground) * fAlpha) + FastColor.ARGB32.red(background) * (bAlpha);
        var aGreen = (FastColor.ARGB32.green(foreground) * fAlpha) + FastColor.ARGB32.green(background) * (bAlpha);
        var aBlue = (FastColor.ARGB32.blue(foreground) * fAlpha) + FastColor.ARGB32.blue(background) * (bAlpha);

        return of(aRed, aGreen, aBlue, clamp(a / 0.00390625));
    }

    private static int of(float aRed, float aGreen, float aBlue, float alpha) {
        return FastColor.ARGB32.color(clamp(alpha), clamp(aRed), clamp(aGreen), clamp(aBlue));
    }

    private static int clamp(double v) {
        return Math.min((int) v, 255);
    }

    /// </editor-fold>

    public static int rgbaToAbgr(int rgba) {
        var a = FastColor.ARGB32.alpha(rgba);
        var r = FastColor.ARGB32.red(rgba);
        var g = FastColor.ARGB32.green(rgba);
        var b = FastColor.ARGB32.blue(rgba);

        return nativeImageCombine(a, b, g, r);
    }

    public static int nativeImageCombine(int alpha, int blue, int green, int red) {
        return (alpha & 255) << 24 | (blue & 255) << 16 | (green & 255) << 8 | (red & 255);
    }

    public static NativeImage toNaiveImage(ISimpleDrawing drawing) {
        NativeImage image = new NativeImage(drawing.getSize(), drawing.getSize(), false);

        int background;
        if (drawing instanceof IHasPaperColor hasPaperType) {
            background = hasPaperType.getPaperColor();
        } else {
            background = 0;
        }

        int foreground;
        if (drawing instanceof IHasInkType hasInkType) {
            foreground = hasInkType.getInkType().getColor();
        } else {
            foreground = InkType.BLACK.getColor();
        }

        var bgColor = rgbaToAbgr(background);
        image.fillRect(0, 0, drawing.getSize(), drawing.getSize(), bgColor);
        int index = 0;
        byte[] value = drawing.getPixels();
        for (int w = 0; w < drawing.getSize(); w++) {
            for (int h = 0; h < drawing.getSize(); h++) {
                var alpha = 16 * (16 - value[index++]) - 1;

                if (alpha == 255) {
                    continue;
                }

                int mixed = mix(foreground, background, alpha);
                var abgr = rgbaToAbgr(mixed);
                image.setPixelRGBA(w, h, abgr);
            }
        }
        return image;
    }
}
