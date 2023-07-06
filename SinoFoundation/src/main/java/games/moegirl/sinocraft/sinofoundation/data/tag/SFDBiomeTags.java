package games.moegirl.sinocraft.sinofoundation.data.tag;

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

    public static TagKey<Biome> biome(String name) {
        return TagKey.create(Registries.BIOME, new ResourceLocation(SinoFoundation.MODID, name));
    }
}
