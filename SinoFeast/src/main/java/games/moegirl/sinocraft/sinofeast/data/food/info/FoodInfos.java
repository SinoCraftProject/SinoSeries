package games.moegirl.sinocraft.sinofeast.data.food.info;

import net.minecraft.resources.ResourceLocation;
import java.util.HashMap;
import java.util.Map;

public class FoodInfos {
    private static final Map<ResourceLocation, FoodInfo> FOOD_INFO_REGISTRY = new HashMap<>();

    public static FoodInfo getInfo(ResourceLocation name) {
        return FOOD_INFO_REGISTRY.get(name);
    }

    static void addInfo(ResourceLocation name, FoodInfo info) {
        FOOD_INFO_REGISTRY.put(name, info);
    }

    static void clearInfos() {
        FOOD_INFO_REGISTRY.clear();
    }
}
