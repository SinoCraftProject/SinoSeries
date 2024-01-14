package games.moegirl.sinocraft.sinobrush.data;

import games.moegirl.sinocraft.sinobrush.SinoBrush;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;

public class SBRItemTags {
    public static final TagKey<Item> UNFOLDED_FAN = TagKey.create(Registries.ITEM, new ResourceLocation(SinoBrush.MODID, "unfolded_fan"));
    public static final TagKey<Item> FOLDED_FAN = TagKey.create(Registries.ITEM, new ResourceLocation(SinoBrush.MODID, "folded_fan"));
    public static final TagKey<Item> FAN = TagKey.create(Registries.ITEM, new ResourceLocation(SinoBrush.MODID, "fan"));
}
