package games.moegirl.sinocraft.sinocore.gui.widgets;

import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.mojang.serialization.Codec;
import com.mojang.serialization.JsonOps;
import games.moegirl.sinocraft.sinocore.SinoCorePlatform;
import games.moegirl.sinocraft.sinocore.gui.widgets.entry.*;
import games.moegirl.sinocraft.sinocore.util.ModList;
import net.minecraft.resources.ResourceLocation;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class WidgetLoader {

    private static final Map<String, Codec<? extends AbstractWidgetEntry>> CODEC_MAP = new HashMap<>();

    public static final Codec<AbstractWidgetEntry> WIDGET_CODEC = Codec.STRING.dispatch(AbstractWidgetEntry::getType, CODEC_MAP::get);

    /**
     * 每次调用 loadWidgets 都重新从硬盘加载
     */
    public static boolean FORCE_RELOAD_WIDGETS = SinoCorePlatform.isDevelopmentEnvironment();

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

    /**
     * 从 GUI json 文件加载 Widgets 对象
     *
     * @param name 文件路径
     * @return 从 resources 中加载的 Widgets 对象
     */
    public static Widgets loadWidgets(ResourceLocation name) {
        if (!FORCE_RELOAD_WIDGETS && WIDGETS.containsKey(name)) return (WIDGETS.get(name));
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

    /**
     * 重新加载所有 json 文件
     */
    public static void reloadAllWidgets() {
        List<ResourceLocation> names = new ArrayList<>(WIDGETS.keySet());
        WIDGETS.clear();
        for (ResourceLocation name : names) {
            loadWidgets(name);
        }
    }
}
