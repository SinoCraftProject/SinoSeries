package games.moegirl.sinocraft.sinofeast.data.food.taste;

import net.minecraft.resources.ResourceLocation;

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

    private int maxPreferWeight = 0;
    private int maxLikeWeight = 0;
    private int maxDislikeWeight = 0;

    public FoodTaste getTaste(ResourceLocation name) {
        return foodTastes.get(name);
    }

    public Map<ResourceLocation, FoodTaste> getTastes() {
        return foodTastes;
    }

    public void addTaste(ResourceLocation key, FoodTaste taste) {
        foodTastes.put(key, taste);

        if (taste.isAdvanced()) {
            maxPreferWeight += taste.likeWeight();
        }

        maxLikeWeight += taste.likeWeight();
        maxDislikeWeight += taste.dislikeWeight();
    }

    public void initTastes() {
        foodTastes.clear();

        maxPreferWeight = 0;
        maxLikeWeight = 0;
        maxDislikeWeight = 0;
    }

    public FoodTaste randomPrefer(List<FoodTaste> eliminates) {
        int maxWeight = maxPreferWeight;
        for (var taste : eliminates) {
            if (taste.isAdvanced()) {
                maxWeight -= taste.likeWeight();
            }
        }

        var rand = RAND.nextInt(0, maxWeight);
        for (var taste : foodTastes.values()) {
            if (taste.likeWeight() == 0 || !taste.isAdvanced()) {
                continue;
            }

            if (eliminates.contains(taste)) {
                continue;
            }

            rand -= taste.likeWeight();
            if (rand <= 0) {
                return taste;
            }
        }

        throw new RuntimeException("Random Exception: number " + rand + " is out of bound " + maxLikeWeight);
    }

    public FoodTaste randomLike(List<FoodTaste> eliminates) {
        int maxWeight = maxLikeWeight;
        for (var taste : eliminates) {
            if (taste.isAdvanced()) {
                maxWeight -= taste.likeWeight();
            }
        }

        var rand = RAND.nextInt(0, maxWeight);
        for (var taste : foodTastes.values()) {
            if (taste.likeWeight() == 0) {
                continue;
            }

            if (eliminates.contains(taste)) {
                continue;
            }

            rand -= taste.likeWeight();
            if (rand <= 0) {
                return taste;
            }
        }

        throw new RuntimeException("Random Exception: number " + rand + " is out of bound " + maxLikeWeight);
    }

    public FoodTaste randomDislike(List<FoodTaste> eliminates) {
        int maxWeight = maxDislikeWeight;
        for (var taste : eliminates) {
            if (taste.isAdvanced()) {
                maxWeight -= taste.likeWeight();
            }
        }

        var rand = RAND.nextInt(0, maxWeight);
        for (var taste : foodTastes.values()) {
            if (taste.dislikeWeight() == 0) {
                continue;
            }

            if (eliminates.contains(taste)) {
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
