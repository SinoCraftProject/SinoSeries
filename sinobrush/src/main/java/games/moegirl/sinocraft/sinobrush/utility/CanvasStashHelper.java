package games.moegirl.sinocraft.sinobrush.utility;

import games.moegirl.sinocraft.sinobrush.drawing.Drawing;
import net.minecraft.client.Minecraft;
import net.minecraft.nbt.NbtIo;

import java.io.File;
import java.io.IOException;

public class CanvasStashHelper {
    private static File STASH_CANVAS;

    public static File getStashCanvas(Minecraft minecraft) {
        if (STASH_CANVAS == null) {
            STASH_CANVAS = new File(minecraft.gameDirectory, "sinoseries/sinobrush/canvas.nbt");
        }

        return STASH_CANVAS;
    }

    public static void stashCanvas(Minecraft minecraft, Drawing drawing) {
        try {
            NbtIo.write(drawing.writeToCompound(), getStashCanvas(minecraft));
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public static Drawing unstashCanvas(Minecraft minecraft) {
        try {
            var tag = NbtIo.read(getStashCanvas(minecraft));
            return Drawing.fromTag(tag);
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
    }
}
