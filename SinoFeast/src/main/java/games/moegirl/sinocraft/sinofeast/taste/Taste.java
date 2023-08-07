package games.moegirl.sinocraft.sinofeast.taste;

import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;

public record Taste(String key, Boolean isAdvanced, int likeWeight, int dislikeWeight, TagKey<Item> tasteKey,
                    TagKey<Item> tasteKeyPrimary, TagKey<Item> tasteKeySecondary) {

//TODO 将字符串形式的Key转变为Component。从而获得本地化key
}
