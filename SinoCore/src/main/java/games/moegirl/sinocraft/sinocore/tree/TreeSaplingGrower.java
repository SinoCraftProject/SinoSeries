package games.moegirl.sinocraft.sinocore.tree;

import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceKey;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.grower.AbstractTreeGrower;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraftforge.common.util.Lazy;

import javax.annotation.Nullable;
import java.util.Objects;
import java.util.function.Function;

public class TreeSaplingGrower extends AbstractTreeGrower {

    @Nullable
    private Tree tree = null;
    private final Lazy<Holder<? extends ConfiguredFeature<?, ?>>> feature;

    public TreeSaplingGrower(Function<Tree, Holder<? extends ConfiguredFeature<?, ?>>> factory) {
        this.feature = Lazy.of(() -> factory.apply(Objects.requireNonNull(this.tree)));
    }

    // todo fix tree grower
    @Override
    @Nullable
    protected ResourceKey<ConfiguredFeature<?, ?>> getConfiguredFeature(RandomSource random, boolean hasFlowers) {
        // return feature.get();
        return null;
    }

    void setTree(Tree tree) {
        this.tree = tree;
    }
}
