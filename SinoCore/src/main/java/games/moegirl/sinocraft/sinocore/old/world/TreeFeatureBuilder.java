package games.moegirl.sinocraft.sinocore.old.world;

import net.minecraft.util.valueproviders.ConstantInt;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.TreeConfiguration;
import net.minecraft.world.level.levelgen.feature.featuresize.FeatureSize;
import net.minecraft.world.level.levelgen.feature.featuresize.TwoLayersFeatureSize;
import net.minecraft.world.level.levelgen.feature.foliageplacers.BlobFoliagePlacer;
import net.minecraft.world.level.levelgen.feature.foliageplacers.FoliagePlacer;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.minecraft.world.level.levelgen.feature.treedecorators.TreeDecorator;
import net.minecraft.world.level.levelgen.feature.trunkplacers.StraightTrunkPlacer;
import net.minecraft.world.level.levelgen.feature.trunkplacers.TrunkPlacer;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * A build to generate a tree, default is same to oak
 */
public class TreeFeatureBuilder extends BaseFeatureBuilder<TreeConfiguration, TreeFeatureBuilder> {

    private BlockStateProvider trunk = BlockStateProvider.simple(Blocks.OAK_WOOD);
    private TrunkPlacer trunkPlacer = new StraightTrunkPlacer(4, 2, 0);
    private BlockStateProvider foliage = BlockStateProvider.simple(Blocks.OAK_LEAVES);
    private FoliagePlacer foliagePlacer = new BlobFoliagePlacer(ConstantInt.of(2), ConstantInt.of(0), 3);
    private BlockStateProvider dirt = null;
    private FeatureSize size = new TwoLayersFeatureSize(1, 0, 1);
    private boolean hasVine = false;
    private boolean forceDirt = false;

    private final List<TreeDecorator> decorators = new ArrayList<>();

    public TreeFeatureBuilder() {
        super(Feature.TREE);
    }

    @Override
    public TreeFeatureBuilder fromConfiguration(TreeConfiguration parent) {
        trunk = parent.trunkProvider;
        trunkPlacer = parent.trunkPlacer;
        foliage = parent.foliageProvider;
        foliagePlacer = parent.foliagePlacer;
        dirt = parent.dirtProvider;
        size = parent.minimumSize;
        hasVine = !parent.ignoreVines;
        forceDirt = parent.forceDirt;
        decorators.addAll(parent.decorators);
        return this;
    }

    @Override
    protected TreeConfiguration buildConfiguration() {
        TreeConfiguration.TreeConfigurationBuilder builder =
                new TreeConfiguration.TreeConfigurationBuilder(trunk, trunkPlacer, foliage, foliagePlacer, size);
        if (!hasVine) {
            builder.ignoreVines();
        }
        if (forceDirt) {
            builder.forceDirt();
        }
        if (!decorators.isEmpty()) {
            builder.decorators(List.copyOf(decorators));
        }
        if (dirt != null) {
            builder.dirt(dirt);
        }
        return builder.build();
    }

    /**
     * Set trunk block(s)
     *
     * @param trunk trunk blocks
     * @return this builder
     */
    public TreeFeatureBuilder trunk(BlockStateProvider trunk) {
        this.trunk = trunk;
        return this;
    }

    /**
     * Set how to place trunk blocks
     *
     * @param placer placer to trunk
     * @return this builder
     */
    public TreeFeatureBuilder trunkPlacer(TrunkPlacer placer) {
        this.trunkPlacer = placer;
        return this;
    }

    /**
     * Set foliage block(s)
     *
     * @param foliage foliage blocks
     * @return this builder
     */
    public TreeFeatureBuilder foliage(BlockStateProvider foliage) {
        this.foliage = foliage;
        return this;
    }

    /**
     * Set how to place foliage blocks
     *
     * @param placer placer to foliage
     * @return this builder
     */
    public TreeFeatureBuilder foliagePlacer(FoliagePlacer placer) {
        this.foliagePlacer = placer;
        return this;
    }

    /**
     * Set dirt
     * @param dirt dirt
     * @return this builder
     */
    public TreeFeatureBuilder dirt(BlockStateProvider dirt) {
        this.dirt = dirt;
        return this;
    }

    /**
     * Minimum tree size
     * @param size size
     * @return this builder
     */
    public TreeFeatureBuilder minimumSize(FeatureSize size) {
        this.size = size;
        return this;
    }

    /**
     * Mark this tree can grow vines
     * @return this builder
     */
    public TreeFeatureBuilder hasVines() {
        this.hasVine = true;
        return this;
    }

    /**
     * Mark this tree forceDirt
     * @return this builder
     */
    public TreeFeatureBuilder forceDirt() {
        this.forceDirt = true;
        return this;
    }

    /**
     * Add tree decorator
     * @param decorator decorator
     * @return this builder
     */
    public TreeFeatureBuilder addDecorator(TreeDecorator decorator) {
        decorators.add(decorator);
        return this;
    }

    /**
     * Add tree decorators
     * @param decorators decorators
     * @return this builder
     */
    public TreeFeatureBuilder addDecorators(TreeDecorator... decorators) {
        Collections.addAll(this.decorators, decorators);
        return this;
    }

    /**
     * Add tree decorators
     * @param decorators decorators
     * @return this builder
     */
    public TreeFeatureBuilder addDecorators(Collection<? extends TreeDecorator> decorators) {
        this.decorators.addAll(decorators);
        return this;
    }
}
