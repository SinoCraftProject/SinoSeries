package games.moegirl.sinocraft.sinodeco.data.gen.tag;

import games.moegirl.sinocraft.sinodeco.SinoDeco;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;

public class SDItemTags {
    public static final TagKey<Item> MARBLE_BLOCKS = TagKey.create(Registries.ITEM, ResourceLocation.fromNamespaceAndPath(SinoDeco.MODID, "marble_blocks"));

    public static final TagKey<Item> PEACH_LOGS = TagKey.create(Registries.ITEM, ResourceLocation.fromNamespaceAndPath(SinoDeco.MODID, "peach_logs"));
}
