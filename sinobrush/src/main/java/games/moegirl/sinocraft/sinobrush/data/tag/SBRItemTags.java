package games.moegirl.sinocraft.sinobrush.data.tag;

import games.moegirl.sinocraft.sinobrush.SinoBrush;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;

public class SBRItemTags {
    public static final TagKey<Item> FAN = TagKey.create(Registries.ITEM, new ResourceLocation(SinoBrush.MODID, "fan"));
    public static final TagKey<Item> XUAN_PAPER = TagKey.create(Registries.ITEM, new ResourceLocation(SinoBrush.MODID, "xuan_paper"));

}
