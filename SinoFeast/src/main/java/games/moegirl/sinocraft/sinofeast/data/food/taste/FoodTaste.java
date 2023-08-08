package games.moegirl.sinocraft.sinofeast.data.food.taste;

import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;

public record FoodTaste(ResourceLocation key, boolean isAdvanced, int likeWeight, int dislikeWeight,
                        TagKey<Item> tasteKey, TagKey<Item> tasteKeyPrimary, TagKey<Item> tasteKeySecondary) {
    public Component getName() {
        return Component.translatable("sinofeast.taste."+key.getPath());
    }
}
