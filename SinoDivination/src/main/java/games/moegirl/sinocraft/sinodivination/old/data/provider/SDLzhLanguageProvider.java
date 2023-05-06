package games.moegirl.sinocraft.sinodivination.old.data.provider;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import games.moegirl.sinocraft.sinodivination.SinoDivination;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.PackType;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.common.data.LanguageProvider;
import net.minecraftforge.data.event.GatherDataEvent;
import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

public class SDLzhLanguageProvider extends LanguageProvider {

    private final ExistingFileHelper helper;

    public SDLzhLanguageProvider(GatherDataEvent event) {
        super(event.getGenerator().getPackOutput(), SinoDivination.MODID, "lzh");
        this.helper = event.getExistingFileHelper();
    }

    @Override
    protected void addTranslations() {
        ResourceLocation location = new ResourceLocation(SinoDivination.MODID, "lang_map/map.json");
        try(InputStream is = helper.getResource(location, PackType.CLIENT_RESOURCES).open()) {
            JsonObject obj = JsonParser.parseString(IOUtils.toString(is, StandardCharsets.UTF_8)).getAsJsonObject();
            for (String s : obj.keySet()) {
                add(s, obj.get(s).getAsString());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
