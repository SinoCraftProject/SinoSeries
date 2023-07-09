package games.moegirl.sinocraft.sinocalligraphy.data.gen;

import games.moegirl.sinocraft.sinocalligraphy.SinoCalligraphy;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;

public class SCAItemTags {
    public static final TagKey<Item> PAPERS = ItemTags.create(new ResourceLocation(SinoCalligraphy.MODID, "papers"));
    public static final TagKey<Item> FILLED_PAPERS = ItemTags.create(new ResourceLocation(SinoCalligraphy.MODID, "filled_papers"));
    public static final TagKey<Item> INKS = ItemTags.create(new ResourceLocation(SinoCalligraphy.MODID, "inks"));
    public static final TagKey<Item> FAN = ItemTags.create(new ResourceLocation(SinoCalligraphy.MODID, "fan"));
    public static final TagKey<Item> BRUSHES = ItemTags.create(new ResourceLocation(SinoCalligraphy.MODID, "brushes"));
}
