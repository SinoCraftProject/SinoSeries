package games.moegirl.sinocraft.sinobrush.utility;

import games.moegirl.sinocraft.sinobrush.drawing.MutableDrawing;
import net.minecraft.client.Minecraft;
import net.minecraft.nbt.NbtIo;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;

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

    public static void stashCanvas(Minecraft minecraft, MutableDrawing drawing) {
        try {
            NbtIo.write(drawing.writeToCompound(minecraft.level.registryAccess()), getStashCanvas(minecraft));
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public static MutableDrawing unstashCanvas(Minecraft minecraft) {
        try {
            var tag = NbtIo.read(getStashCanvas(minecraft));
            if (tag != null) {
                var drawing = new MutableDrawing();
                drawing.readFromCompound(tag, minecraft.level.registryAccess());
                return drawing;
            }
            return null;
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return null;
    }
}
