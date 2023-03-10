package games.moegirl.sinocraft.sinocore.old.api.world;

import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.BiomeSpecialEffects;
import org.apache.commons.lang3.ArrayUtils;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public interface PlacedFilter {
    PlacedFilter ALL = (biomeName, category, climate, effects) -> true;

    static PlacedFilter ofBiome(ResourceLocation biome) {
        return (biomeName, category, climate, effects) -> biome.equals(biomeName);
    }

    static PlacedFilter ofBiome(Biome biome) {
        return ofBiome(biome.delegate.name());
    }

    static PlacedFilter ofBiome(ResourceKey<Biome> biome) {
        return ofBiome(biome.getRegistryName());
    }

    static PlacedFilter ofBiomes(ResourceLocation... biomes) {
        return ofBiomes(List.of(biomes));
    }

    static PlacedFilter ofBiomes(Biome... biomes) {
        return ofBiomes(Arrays.stream(biomes).map(biome -> biome.delegate.name()).collect(Collectors.toList()));
    }

    static PlacedFilter ofBiomes(ResourceKey<?>... biomes) {
        return ofBiomes(Arrays.stream(biomes).map(ResourceKey::getRegistryName).collect(Collectors.toList()));
    }

    static PlacedFilter ofBiomes(Collection<ResourceLocation> biomes) {
        return (biomeName, category, climate, effects) -> biomes.contains(biomeName);
    }

    static PlacedFilter ofCategory(Biome.BiomeCategory category) {
        return (biomeName, category2, climate, effects) -> category == category2;
    }

    static PlacedFilter ofCategories(Biome.BiomeCategory... categories) {
        return (biomeName, category, climate, effects) -> ArrayUtils.contains(categories, category);
    }

    static PlacedFilter ofCategories(Collection<Biome.BiomeCategory> categories) {
        return (biomeName, category, climate, effects) -> categories.contains(category);
    }

    static PlacedFilter ofClimate(Predicate<Biome.ClimateSettings> settings) {
        return (biomeName, category, climate, effects) -> settings.test(climate);
    }

    static PlacedFilter ofEffects(Predicate<BiomeSpecialEffects> effects) {
        return (biomeName, category, climate, effects2) -> effects.test(effects2);
    }

    static PlacedFilter and(PlacedFilter a, PlacedFilter b) {
        return (biomeName, category, climate, effects) -> a.test(biomeName, category, climate, effects) && b.test(biomeName, category, climate, effects);
    }

    static PlacedFilter or(PlacedFilter a, PlacedFilter b) {
        return (biomeName, category, climate, effects) -> a.test(biomeName, category, climate, effects) || b.test(biomeName, category, climate, effects);
    }

    boolean test(ResourceLocation biomeName, Biome.BiomeCategory category, Biome.ClimateSettings climate, BiomeSpecialEffects effects);

    default PlacedFilter and(PlacedFilter other) {
        return and(this, other);
    }

    default PlacedFilter or(PlacedFilter other) {
        return or(this, other);
    }
}
