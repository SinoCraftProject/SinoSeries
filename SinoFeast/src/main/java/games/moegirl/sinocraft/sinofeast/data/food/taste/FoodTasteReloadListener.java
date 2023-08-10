package games.moegirl.sinocraft.sinofeast.data.food.taste;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.mojang.serialization.JsonOps;
import games.moegirl.sinocraft.sinofeast.SinoFeast;
import games.moegirl.sinocraft.sinofeast.networking.packet.S2CSyncFoodTastePacket;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.server.packs.resources.SimpleJsonResourceReloadListener;
import net.minecraft.util.profiling.ProfilerFiller;
import net.minecraftforge.event.AddReloadListenerEvent;
import net.minecraftforge.event.OnDatapackSyncEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.Map;

@Mod.EventBusSubscriber(modid = SinoFeast.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class FoodTasteReloadListener extends SimpleJsonResourceReloadListener {
    private static final Gson GSON = new Gson();

    public FoodTasteReloadListener() {
        super(GSON, "sinoseries/food_tastes");
    }

    @Override
    protected void apply(Map<ResourceLocation, JsonElement> map, ResourceManager resourceManager, ProfilerFiller profiler) {
        FoodTastes.getInstance().initTastes();

        for (var entry : map.entrySet()) {

            FoodTaste first = FoodTasteCodec.TASTE_CODEC.parse(JsonOps.INSTANCE, entry.getValue()).get().orThrow();

            FoodTastes.getInstance().addTaste(first.key(),first);
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

    @SubscribeEvent
    public static void onDataPackSync(OnDatapackSyncEvent event) {
        if (event.getPlayer() == null) {
            SinoFeast.getInstance().getNetworking().sendToAll(new S2CSyncFoodTastePacket(FoodTastes.getInstance().getTastes().values()));
        } else {
            SinoFeast.getInstance().getNetworking().sendTo(new S2CSyncFoodTastePacket(FoodTastes.getInstance().getTastes().values()), event.getPlayer());
        }
    }
}
