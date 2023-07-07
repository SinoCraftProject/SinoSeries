package games.moegirl.sinocraft.sinocore.world.features;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;

public class FeaturesHelper {
    public static ResourceKey<ConfiguredFeature<?, ?>> createKey(ResourceLocation name) {
        return ResourceKey.create(Registries.CONFIGURED_FEATURE, name);
    }
}
