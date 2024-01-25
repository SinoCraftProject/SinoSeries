package games.moegirl.sinocraft.sinotest.data;

import games.moegirl.sinocraft.sinotest.SinoTest;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;

public class TestKeys {

    public static ResourceKey<ConfiguredFeature<?, ?>> TEST_FEATURE = key(Registries.CONFIGURED_FEATURE, "test_feature");

    public static <T> ResourceKey<T> key(ResourceKey<Registry<T>> registry, String name) {
        return ResourceKey.create(registry, new ResourceLocation(SinoTest.MODID, name));
    }
}
