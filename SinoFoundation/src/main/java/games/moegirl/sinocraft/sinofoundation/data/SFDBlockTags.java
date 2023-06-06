package games.moegirl.sinocraft.sinofoundation.data;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;

public class SFDBlockTags {
    public static final TagKey<Block> MINECRAFT_WALLS = BlockTags.create(new ResourceLocation("walls"));

    public static final TagKey<Block> FORGE_ORES_NITER = BlockTags.create(new ResourceLocation("forge", "ores/niter"));
    public static final TagKey<Block> FORGE_ORES_SULFUR = BlockTags.create(new ResourceLocation("forge", "ores/sulfur"));
    public static final TagKey<Block> FORGE_ORES_JADE = BlockTags.create(new ResourceLocation("forge", "ores/jade"));
}
