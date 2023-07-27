package games.moegirl.sinocraft.sinofeast.data.food.taste;

import games.moegirl.sinocraft.sinofeast.SFConstants;
import games.moegirl.sinocraft.sinofeast.SinoFeast;
import games.moegirl.sinocraft.sinofoundation.data.gen.tag.SFDItemTags;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;

import java.util.*;

public class FoodTastes {
    private static final Random RAND = new Random();

    private static FoodTastes INSTANCE = null;

    private FoodTastes() {
    }

    public static FoodTastes getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new FoodTastes();
        }

        return INSTANCE;
    }

    private final Map<ResourceLocation, FoodTaste> foodTastes = new LinkedHashMap<>();  // qyl27: the order is important.

    private int maxLikeWeight = 0;
    private int maxDislikeWeight = 0;

    public FoodTaste getTaste(ResourceLocation name) {
        return foodTastes.get(name);
    }

    void addTaste(ResourceLocation name, FoodTaste taste) {
        foodTastes.put(name, taste);

        maxLikeWeight += taste.likeWeight();
        maxDislikeWeight += taste.dislikeWeight();
    }

    void initTastes() {
        foodTastes.clear();

        maxLikeWeight = 0;
        maxDislikeWeight = 0;
    }

    public FoodTaste randomLike() {
        var rand = RAND.nextInt(0, maxLikeWeight);
        for (var taste : foodTastes.values()) {
            if (taste.likeWeight() == 0) {
                continue;
            }

            rand -= taste.likeWeight();
            if (rand <= 0) {
                return taste;
            }
        }

        throw new RuntimeException("Random Exception: number " + rand + " is out of bound " + maxLikeWeight);
    }

    public FoodTaste randomDislike() {
        var rand = RAND.nextInt(0, maxDislikeWeight);
        for (var taste : foodTastes.values()) {
            if (taste.dislikeWeight() == 0) {
                continue;
            }

            rand -= taste.dislikeWeight();
            if (rand <= 0) {
                return taste;
            }
        }

        throw new RuntimeException("Random Exception: number " + rand + " is out of bound " + maxDislikeWeight);
    }
}
