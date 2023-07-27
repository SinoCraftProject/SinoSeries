package games.moegirl.sinocraft.sinofeast.data.food.taste;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import games.moegirl.sinocraft.sinofeast.SinoFeast;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.server.packs.resources.SimpleJsonResourceReloadListener;
import net.minecraft.tags.ItemTags;
import net.minecraft.util.profiling.ProfilerFiller;
import net.minecraftforge.event.AddReloadListenerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.Map;

@Mod.EventBusSubscriber(modid = SinoFeast.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class FoodTasteReloadListener extends SimpleJsonResourceReloadListener {
    private static final Gson GSON = new Gson();

    public FoodTasteReloadListener() {
        super(GSON, "sinoseries/sinofeast/food_tastes");
    }

    @Override
    protected void apply(Map<ResourceLocation, JsonElement> map, ResourceManager resourceManager, ProfilerFiller profiler) {
        FoodTastes.getInstance().initTastes();
        for (var entry : map.entrySet()) {
            var key = entry.getKey();
            var json = entry.getValue().getAsJsonObject();
            var name = json.get("name").getAsString();
            var isAdvanced = json.get("is_advanced").getAsBoolean();
            var likeWeight = json.get("like_weight").getAsInt();
            var dislikeWeight = json.get("dislike_weight").getAsInt();
            var tasteKey = json.get("taste_key").getAsString();
            var tasteKeyPrimary = json.get("taste_key_primary").getAsString();
            var tasteKeySecondary = json.get("taste_key_secondary").getAsString();
            FoodTastes.getInstance().addTaste(key, new FoodTaste(key, name, isAdvanced, likeWeight, dislikeWeight,
                    ItemTags.create(new ResourceLocation(tasteKey)),
                    ItemTags.create(new ResourceLocation(tasteKeyPrimary)),
                    ItemTags.create(new ResourceLocation(tasteKeySecondary))));
        }
    }

    @Override
    public String getName() {
        return "SinoFeast: Food Tastes Reload Listener";
    }

    @SubscribeEvent
    public static void onAddReloadListener(AddReloadListenerEvent event) {
        event.addListener(new FoodTasteReloadListener());
    }
}
