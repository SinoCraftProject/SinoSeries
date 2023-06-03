package games.moegirl.sinocraft.sinocore.world.gen.tree;

import games.moegirl.sinocraft.sinocore.tree.TreeBlockType;
import games.moegirl.sinocraft.sinocore.world.gen.ModConfiguredFeatures;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.grower.AbstractTreeGrower;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.configurations.TreeConfiguration;
import org.jetbrains.annotations.Nullable;

/**
 * DefaultTreeGrower helps saplings grow to trees.
 */
public class DefaultTreeGrower extends ModTreeGrowerBase {
    protected ResourceKey<ConfiguredFeature<?, ?>> resourceKey;

    public DefaultTreeGrower(ResourceLocation growerName) {
        super(growerName);
        this.resourceKey = ModConfiguredFeatures.registerKey(growerName);
    }

    @Override
    public TreeConfiguration getConfiguration(Block trunk, Block leaves) {
        return ModConfiguredFeatures.defaultTree(trunk, leaves);
    }
}
