package games.moegirl.sinocraft.sinocalligraphy.drawing;

import com.google.gson.JsonParseException;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import games.moegirl.sinocraft.sinocalligraphy.drawing.simple.SimpleDrawing;
import net.minecraft.nbt.TagParser;

public class DrawingParser {
    public static String toJson(SimpleDrawing drawing) {
        var tag = drawing.serializeNBT();
        return tag.getAsString();
    }

    public static SimpleDrawing fromJson(String json) {
        try {
            var tag = TagParser.parseTag(json);
            var drawing = new SimpleDrawing();
            drawing.deserializeNBT(tag);
            return drawing;
        } catch (CommandSyntaxException | JsonParseException ex) {
            throw new RuntimeException(ex);
        }
    }
}
