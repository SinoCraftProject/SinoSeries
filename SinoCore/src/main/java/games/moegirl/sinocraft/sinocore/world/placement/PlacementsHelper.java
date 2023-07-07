package games.moegirl.sinocraft.sinocore.world.placement;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;

public class PlacementsHelper {
    public static ResourceKey<PlacedFeature> createKey(ResourceLocation name) {
        return ResourceKey.create(Registries.PLACED_FEATURE, name);
    }
}
