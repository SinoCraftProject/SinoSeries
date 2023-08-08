package games.moegirl.sinocraft.sinofoundation.data.gen.tag;

import games.moegirl.sinocraft.sinofoundation.SinoFoundation;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;

public class SFDBlockTags {
    public static final TagKey<Block> MINECRAFT_WALLS = BlockTags.create(new ResourceLocation("walls"));

    public static final TagKey<Block> FORGE_ORES_NITER = BlockTags.create(new ResourceLocation("forge", "ores/niter"));
    public static final TagKey<Block> FORGE_ORES_SULFUR = BlockTags.create(new ResourceLocation("forge", "ores/sulfur"));
    public static final TagKey<Block> FORGE_ORES_JADE = BlockTags.create(new ResourceLocation("forge", "ores/jade"));

    public static final TagKey<Block> ORES = block("ores");
    public static final TagKey<Block> JADE = block("jade");

    public static final TagKey<Block> JUJUBE_BLOCK = block("jujube");
    public static final TagKey<Block> SPAWN_DRAGONLIVER_MELON = block("spawn_dragonliver_melon");

    public static final TagKey<Block> LANTERN = block("lantern");

    private static TagKey<Block> block(String name) {
        return TagKey.create(Registries.BLOCK, new ResourceLocation(SinoFoundation.MODID, name));
    }
}
