package games.moegirl.sinocraft.sinodivination.tree;

import games.moegirl.sinocraft.sinocore.world.gen.tree.ModTreeGrowerBase;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.valueproviders.ConstantInt;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.levelgen.feature.configurations.TreeConfiguration;
import net.minecraft.world.level.levelgen.feature.featuresize.TwoLayersFeatureSize;
import net.minecraft.world.level.levelgen.feature.foliageplacers.FancyFoliagePlacer;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.minecraft.world.level.levelgen.feature.trunkplacers.FancyTrunkPlacer;
import org.jetbrains.annotations.NotNull;

import java.util.OptionalInt;

public class TreeGrower extends ModTreeGrowerBase {
    public TreeGrower(ResourceLocation growerName) {
        super(growerName);
    }

    @Override
    public @NotNull TreeConfiguration getConfiguration(@NotNull Block trunk, @NotNull Block leaves) {
        return new TreeConfiguration.TreeConfigurationBuilder(
                BlockStateProvider.simple(trunk),
                new FancyTrunkPlacer(3, 11, 0),
                BlockStateProvider.simple(leaves),
                new FancyFoliagePlacer(ConstantInt.of(2), ConstantInt.of(4), 4),
                new TwoLayersFeatureSize(0, 0, 0, OptionalInt.of(4))).ignoreVines().build();
    }
}
