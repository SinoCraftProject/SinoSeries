package games.moegirl.sinocraft.sinocore.old.api.tree;

import net.minecraft.core.Holder;
import net.minecraft.world.level.block.grower.AbstractTreeGrower;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraftforge.common.util.Lazy;

import javax.annotation.Nullable;
import java.util.Objects;
import java.util.Random;
import java.util.function.Function;

public class TreeSaplingGrower extends AbstractTreeGrower {

    @Nullable
    private Tree tree = null;
    private final Lazy<Holder<? extends ConfiguredFeature<?, ?>>> feature;

    public TreeSaplingGrower(Function<Tree, Holder<? extends ConfiguredFeature<?, ?>>> factory) {
        this.feature = Lazy.of(() -> factory.apply(Objects.requireNonNull(this.tree)));
    }

    @Override
    protected Holder<? extends ConfiguredFeature<?, ?>> getConfiguredFeature(Random pRandom, boolean pLargeHive) {
        return feature.get();
    }

    void setTree(Tree tree) {
        this.tree = tree;
    }
}
