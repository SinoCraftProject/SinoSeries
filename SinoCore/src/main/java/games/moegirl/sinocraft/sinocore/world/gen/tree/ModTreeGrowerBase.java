package games.moegirl.sinocraft.sinocore.world.gen.tree;

import games.moegirl.sinocraft.sinocore.world.gen.ModConfiguredFeatures;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.grower.AbstractTreeGrower;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import org.jetbrains.annotations.Nullable;

/**
 * DefaultTreeGrower helps saplings grow to trees.
 */
public class ModTreeGrowerBase extends AbstractTreeGrower {

    protected ResourceLocation growerName;
    protected ResourceKey<ConfiguredFeature<?, ?>> resourceKey;

    public ModTreeGrowerBase(ResourceLocation growerName) {
        this.growerName = growerName;
        this.resourceKey = ModConfiguredFeatures.registerKey(growerName);
    }

    public ResourceKey<ConfiguredFeature<?, ?>> getResourceKey() {
        return resourceKey;
    }

    @Nullable
    @Override
    public ResourceKey<ConfiguredFeature<?, ?>> getConfiguredFeature(RandomSource random, boolean hasFlowers) {
        return resourceKey;
    }
}
