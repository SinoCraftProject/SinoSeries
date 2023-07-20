package games.moegirl.sinocraft.sinofeast.data.food.taste;

import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;

public record FoodTaste(ResourceLocation key, String name, boolean isAdvanced, int likeWeight, int dislikeWeight) {
    public Component getName() {
        return Component.translatable(name());
    }
}
