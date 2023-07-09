package games.moegirl.sinocraft.sinofeast.food.info;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import games.moegirl.sinocraft.sinofeast.SinoFeast;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.server.packs.resources.SimpleJsonResourceReloadListener;
import net.minecraft.util.profiling.ProfilerFiller;
import net.minecraftforge.event.AddReloadListenerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.Map;

@Mod.EventBusSubscriber(modid = SinoFeast.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class FoodInfoReloadListener extends SimpleJsonResourceReloadListener {
    private static final Gson GSON = new Gson();

    public FoodInfoReloadListener() {
        super(GSON, "sinoseries/sinofeast/food_info");
    }

    @Override
    protected void apply(Map<ResourceLocation, JsonElement> map, ResourceManager resourceManager, ProfilerFiller profiler) {
        FoodInfoRegistry.clearInfos();
        for (var entry : map.entrySet()) {
            var name = entry.getKey();
            var item = new ResourceLocation(name.getNamespace(), name.getPath());


        }
    }

    @Override
    public String getName() {
        return "SinoFeast: Food Reload Listener";
    }

    @SubscribeEvent
    public static void onAddReloadListener(AddReloadListenerEvent event) {
        event.addListener(new FoodInfoReloadListener());
    }
}
