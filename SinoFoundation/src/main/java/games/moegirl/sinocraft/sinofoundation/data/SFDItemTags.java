package games.moegirl.sinocraft.sinofoundation.data;

import games.moegirl.sinocraft.sinofoundation.SinoFoundation;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;

public class SFDItemTags {
    public static final TagKey<Item> KNIVES = ItemTags.create(new ResourceLocation(SinoFoundation.MODID, "knives"));
}
