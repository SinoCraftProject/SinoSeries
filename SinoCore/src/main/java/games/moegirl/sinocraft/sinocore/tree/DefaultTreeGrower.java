package games.moegirl.sinocraft.sinocore.tree;

import net.minecraft.data.worldgen.features.FeatureUtils;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.grower.AbstractTreeGrower;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import org.jetbrains.annotations.Nullable;

/**
 * DefaultTreeGrower helps saplings grow to trees.
 */
public class DefaultTreeGrower extends AbstractTreeGrower {

    protected TreeType tree;
    protected ResourceLocation growerName;

    public DefaultTreeGrower(TreeType tree) {
        this(tree, tree.name);
    }

    public DefaultTreeGrower(TreeType tree, ResourceLocation growerName) {
        this.tree = tree;
        this.growerName = growerName;
    }

    @Nullable
    @Override
    protected ResourceKey<ConfiguredFeature<?, ?>> getConfiguredFeature(RandomSource random, boolean hasFlowers) {
        return FeatureUtils.createKey(growerName.toString());
    }
}
