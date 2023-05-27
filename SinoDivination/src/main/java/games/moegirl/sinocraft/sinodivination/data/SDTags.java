package games.moegirl.sinocraft.sinodivination.data;

import games.moegirl.sinocraft.sinodivination.SinoDivination;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.Block;

public class SDTags {

    // Items ===========================================================================================================

    public static final TagKey<Item> JADE_SACRIFICIAL_UTENSIL = item("jade_sacrificial_utensil");
    public static final TagKey<Item> COPPER_SACRIFICIAL_UTENSIL = item("copper_sacrificial_utensil");
    public static final TagKey<Item> SACRIFICIAL_UTENSIL = item("sacrificial_utensil");
    public static final TagKey<Item> SACRIFICIAL_UTENSIL_MATERIAL = item("sacrificial_utensil_material");

    public static final TagKey<Item> FORGE_DUSTS_SULFUR = ItemTags.create(new ResourceLocation("forge", "dusts/sulfur"));
    public static final TagKey<Item> FORGE_DUSTS_NITER = ItemTags.create(new ResourceLocation("forge", "dusts/niter"));

    // Blocks ==========================================================================================================

    public static final TagKey<Block> COTINUS_BLOCK = block("cotinus");
    public static final TagKey<Block> SOPHORA_BLOCK = block("sophora");

    public static final TagKey<Block> FIRE_SOURCE = block("fire_source");
    public static final TagKey<Block> HEAT_SOURCE = block("heat");

    public static final TagKey<Block> SPAWN_DRAGONLIVER_MELON_BLOCK = block("dragonliver_melon_spawner");

    public static final TagKey<Block> FORGE_ORES_NITER = BlockTags.create(new ResourceLocation("forge", "ores/niter"));
    public static final TagKey<Block> FORGE_ORES_SULFUR = BlockTags.create(new ResourceLocation("forge", "ores/sulfur"));
    public static final TagKey<Block> FORGE_ORES_JADE = BlockTags.create(new ResourceLocation("forge", "ores/jade"));

    // Biomes ==========================================================================================================

    public static final TagKey<Biome> SPAWN_RICE = biome("spawn_rice");
    public static final TagKey<Biome> SPAWN_REHMANNIA = biome("spawn_rehmannia");
    public static final TagKey<Biome> SPAWN_DRAGONLIVER_MELON = biome("spawn_dragonliver_melon");

    // =================================================================================================================

    public static TagKey<Block> block(String name) {
        return TagKey.create(Registries.BLOCK, new ResourceLocation(SinoDivination.MODID, name));
    }

    public static TagKey<Item> item(String name) {
        return TagKey.create(Registries.ITEM, new ResourceLocation(SinoDivination.MODID, name));
    }

    public static TagKey<Biome> biome(String name) {
        return TagKey.create(Registries.BIOME, new ResourceLocation(SinoDivination.MODID, name));
    }
}
