package games.moegirl.sinocraft.sinobrush.utility;

import games.moegirl.sinocraft.sinobrush.drawing.Drawing;
import net.minecraft.client.Minecraft;
import net.minecraft.nbt.NbtIo;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

public class CanvasStashHelper {
    private static Path STASH_CANVAS;

    public static Path getStashCanvas(Minecraft minecraft) {
        var dir = new File(minecraft.gameDirectory, "sinoseries/sinobrush");
        if (!dir.exists()) {
            dir.mkdirs();
        }

        if (STASH_CANVAS == null) {
            STASH_CANVAS = new File(dir, "canvas.nbt").toPath();
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
            if (tag != null) {
                return Drawing.fromTag(tag);
            }
            return null;
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return null;
    }
}
