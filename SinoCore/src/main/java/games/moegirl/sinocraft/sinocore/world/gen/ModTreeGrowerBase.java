package games.moegirl.sinocraft.sinocore.world.gen;

import games.moegirl.sinocraft.sinocore.tree.Tree;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.grower.AbstractTreeGrower;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.configurations.TreeConfiguration;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * ModTreeGrowerBase is the base class of all Mod Tree Grower.
 */
public abstract class ModTreeGrowerBase extends AbstractTreeGrower {
    protected ResourceLocation growerName;
    protected ResourceKey<ConfiguredFeature<?, ?>> resourceKey;

    public ModTreeGrowerBase(ResourceLocation growerName) {
        this.growerName = growerName;
        this.resourceKey = ResourceKey.create(Registries.CONFIGURED_FEATURE, growerName);
    }

    public ResourceKey<ConfiguredFeature<?, ?>> getResourceKey() {
        return resourceKey;
    }

    @Nullable
    @Override
    protected ResourceKey<ConfiguredFeature<?, ?>> getConfiguredFeature(@NotNull RandomSource random, boolean hasFlowers) {
        return resourceKey;
    }

    public abstract TreeConfiguration getConfiguration(Tree tree);
}
