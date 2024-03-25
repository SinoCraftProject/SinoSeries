package games.moegirl.sinocraft.sinocore.gui.widgets;

import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.mojang.serialization.Codec;
import com.mojang.serialization.JsonOps;
import games.moegirl.sinocraft.sinocore.gui.widgets.entry.*;
import games.moegirl.sinocraft.sinocore.util.ModList;
import net.minecraft.resources.ResourceLocation;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

public class WidgetLoader {

    private static final Map<String, Codec<? extends AbstractWidgetEntry>> CODEC_MAP = new HashMap<>();

    public static final Codec<AbstractWidgetEntry> WIDGET_CODEC = Codec.STRING.dispatch(AbstractWidgetEntry::getType, CODEC_MAP::get);

    static {
        CODEC_MAP.put("button", ButtonEntry.CODEC);
        CODEC_MAP.put("editbox", EditBoxEntry.CODEC);
        CODEC_MAP.put("point", PointEntry.CODEC);
        CODEC_MAP.put("progress", ProgressEntry.CODEC);
        CODEC_MAP.put("slot", SlotEntry.CODEC);
        CODEC_MAP.put("slots", SlotsEntry.CODEC);
        CODEC_MAP.put("text", TextEntry.CODEC);
        CODEC_MAP.put("texture", TextureEntry.CODEC);
    }

    private static final Map<ResourceLocation, Widgets> WIDGETS = new HashMap<>();

    public static Widgets loadWidgets(ResourceLocation name) {
        if (WIDGETS.containsKey(name)) return (WIDGETS.get(name));

        ModList.ModContainer container = ModList.findModById(name.getNamespace()).orElseThrow(() -> new RuntimeException("Not found mod " + name.getNamespace()));
        Path path = container.findResource(name, ".json");
        try (BufferedReader reader = Files.newBufferedReader(path)) {
            JsonElement jsonElement = JsonParser.parseReader(reader);
            System.out.println(jsonElement);
            Widgets widgets = Widgets.CODEC.decode(JsonOps.INSTANCE, jsonElement)
                    .getOrThrow(false, err -> {
                        throw new RuntimeException("Failed to load widget " + name + ": " + err);
                    })
                    .getFirst();
            widgets.setTexture(new ResourceLocation(name.getNamespace(), name.getPath() + ".png"));
            WIDGETS.put(name, widgets);
            return widgets;
        } catch (IOException e) {
            throw new RuntimeException("Failed to load widget " + name + " (" + path + ")", e);
        }
    }
}
