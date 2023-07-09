package games.moegirl.sinocraft.sinofeast.food.taste;

import net.minecraft.network.chat.Component;

public record FoodTaste(String name, boolean isAdvanced, double likeProbability, double dislikeProbability) {
    public Component getName() {
        return Component.translatable(name());
    }
}
