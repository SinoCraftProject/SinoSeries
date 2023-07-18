package games.moegirl.sinocraft.sinofoundation.data.gen.tag;

import games.moegirl.sinocraft.sinofoundation.SinoFoundation;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.biome.Biome;

/**
 * @author luqin2007
 */
public class SFDBiomeTags {

    public static final TagKey<Biome> SPAWN_RICE = biome("spawn_rice");
    public static final TagKey<Biome> SPAWN_REHMANNIA = biome("spawn_rehmannia");
    public static final TagKey<Biome> SPAWN_DRAGONLIVER_MELON = biome("spawn_dragonliver_melon");

    public static final TagKey<Biome> BLACK_JADE_GENERATION = biome("black_jade_generation");
    public static final TagKey<Biome> GREEN_JADE_GENERATION = biome("green_jade_generation");
    public static final TagKey<Biome> RED_JADE_GENERATION = biome("red_jade_generation");
    public static final TagKey<Biome> WHITE_JADE_GENERATION = biome("white_jade_generation");
    public static final TagKey<Biome> YELLOW_JADE_GENERATION = biome("yellow_jade_generation");

    public static TagKey<Biome> biome(String name) {
        return TagKey.create(Registries.BIOME, new ResourceLocation(SinoFoundation.MODID, name));
    }
}
