package games.moegirl.sinocraft.sinodeco.data.gen.tag;

import games.moegirl.sinocraft.sinodeco.SinoDeco;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;

public class SDBlockTags {
    public static final TagKey<Block> MARBLE_BUILDING_BLOCKS = TagKey.create(Registries.BLOCK, ResourceLocation.fromNamespaceAndPath(SinoDeco.MODID, "marble_building_blocks"));
}
