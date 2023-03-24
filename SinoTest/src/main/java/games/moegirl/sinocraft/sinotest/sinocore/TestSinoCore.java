package games.moegirl.sinocraft.sinotest.sinocore;

import games.moegirl.sinocraft.sinocore.tree.Tree;
import games.moegirl.sinocraft.sinocore.utility.NetworkHolder;
import games.moegirl.sinocraft.sinocore.woodwork.Woodwork;
import net.minecraft.util.valueproviders.ConstantInt;
import net.minecraft.world.level.levelgen.feature.configurations.TreeConfiguration;
import net.minecraft.world.level.levelgen.feature.featuresize.TwoLayersFeatureSize;
import net.minecraft.world.level.levelgen.feature.foliageplacers.BlobFoliagePlacer;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.minecraft.world.level.levelgen.feature.trunkplacers.StraightTrunkPlacer;
import net.minecraftforge.eventbus.api.IEventBus;

import static games.moegirl.sinocraft.sinotest.SinoTest.*;

public class TestSinoCore {

    public static final NetworkHolder NETWORK = NetworkHolder.simple(CHANNEL, NET_ID);

    public static Tree tree = Tree.builder(MODID, "test")
            .zh_cn("测试树")
            .grower(t -> new TreeConfiguration.TreeConfigurationBuilder(
                    BlockStateProvider.simple(t.log()),
                    new StraightTrunkPlacer(4, 1, 4),
                    BlockStateProvider.simple(t.leaves()),
                    new BlobFoliagePlacer(ConstantInt.of(1), ConstantInt.of(0), 3),
                    new TwoLayersFeatureSize(1, 0, 1)
            ).build())
            .build(BLOCKS, ITEMS);

    public static Woodwork woodwork = Woodwork.builder(MODID, "test")
            .zh_cn("测试")
            .build(tree.log, ITEMS, BLOCKS, BLOCK_ENTITIES);

    public void register(IEventBus bus) {
        Tree.register(MODID, bus);
        Woodwork.register(MODID, bus, NETWORK);
    }
}
