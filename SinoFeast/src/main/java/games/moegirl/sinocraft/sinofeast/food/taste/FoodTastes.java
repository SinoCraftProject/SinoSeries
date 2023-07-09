package games.moegirl.sinocraft.sinofeast.food.taste;

import net.minecraft.resources.ResourceLocation;

import java.util.HashMap;
import java.util.Map;

public class FoodTastes {
    private static final Map<ResourceLocation, FoodTaste> FOOD_TASTES = new HashMap<>();

    public static FoodTaste getTaste(ResourceLocation name) {
        return FOOD_TASTES.get(name);
    }

    static void addTaste(ResourceLocation name, FoodTaste taste) {
        FOOD_TASTES.put(name, taste);
    }

    static void clearTastes() {
        FOOD_TASTES.clear();
    }
}
