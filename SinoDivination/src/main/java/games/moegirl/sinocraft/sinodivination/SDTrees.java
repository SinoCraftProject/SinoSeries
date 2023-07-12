package games.moegirl.sinocraft.sinodivination;

import games.moegirl.sinocraft.sinocore.handler.BlockStrippingHandler;
import games.moegirl.sinocraft.sinocore.tab.TabsRegistry;
import games.moegirl.sinocraft.sinocore.tree.Tree;
import games.moegirl.sinocraft.sinocore.tree.TreeBlockType;
import games.moegirl.sinocraft.sinocore.tree.TreeRegistry;
import games.moegirl.sinocraft.sinodivination.block.*;
import games.moegirl.sinocraft.sinodivination.blockentity.CotinusDoorEntity;
import games.moegirl.sinocraft.sinodivination.blockentity.CotinusEntity;
import games.moegirl.sinocraft.sinodivination.blockentity.SophoraDoorEntity;
import games.moegirl.sinocraft.sinodivination.blockentity.SophoraEntity;
import games.moegirl.sinocraft.sinofoundation.SinoFoundation;
import games.moegirl.sinocraft.sinofoundation.data.SFDBlockTags;
import games.moegirl.sinocraft.sinofoundation.item.SFDItems;
import games.moegirl.sinocraft.sinofoundation.item.SinoSeriesTabs;
import net.minecraft.util.valueproviders.ConstantInt;
import net.minecraft.world.level.levelgen.feature.configurations.TreeConfiguration;
import net.minecraft.world.level.levelgen.feature.featuresize.TwoLayersFeatureSize;
import net.minecraft.world.level.levelgen.feature.foliageplacers.FancyFoliagePlacer;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.minecraft.world.level.levelgen.feature.trunkplacers.FancyTrunkPlacer;
import net.minecraftforge.eventbus.api.IEventBus;

import java.util.OptionalInt;

/**
 * @author luqin2007
 */
public class SDTrees {

    public static final Tree COTINUS = Tree.builder(SinoFoundation.MODID, "cotinus")
            .translate("zh_cn", "无患")
            .block(TreeBlockType.DOOR, CotinusDoor::new)
            .block(TreeBlockType.TRAPDOOR, CotinusTrapdoor::new)
            .block(TreeBlockType.FENCE_GATE, CotinusFenceGate::new)
            .blockEntity(TreeBlockType.DOOR, CotinusDoorEntity::new)
            .blockEntity(TreeBlockType.TRAPDOOR, CotinusEntity::trapdoor)
            .blockEntity(TreeBlockType.FENCE_GATE, CotinusEntity::fenceGate)
            .blockTags(SFDBlockTags.COTINUS_BLOCK)
            .grower(t -> new TreeConfiguration.TreeConfigurationBuilder(
                    BlockStateProvider.simple(t.getBlock(TreeBlockType.LOG)),
                    new FancyTrunkPlacer(3, 11, 0),
                    BlockStateProvider.simple(t.getBlock(TreeBlockType.LEAVES)),
                    new FancyFoliagePlacer(ConstantInt.of(2), ConstantInt.of(4), 4),
                    new TwoLayersFeatureSize(0, 0, 0, OptionalInt.of(4))).ignoreVines().build())
            .tab(TabsRegistry.items(SinoSeriesTabs.BUILDING_BLOCKS))
            .tab(TreeBlockType.SAPLING, TabsRegistry.items(SinoSeriesTabs.AGRICULTURE))
            .build();

    public static final Tree SOPHORA = Tree.builder(SinoFoundation.MODID, "sophora")
            .translate("zh_cn", "槐")
            .block(TreeBlockType.DOOR, SophoraDoor::new)
            .block(TreeBlockType.TRAPDOOR, SophoraTrapdoor::new)
            .block(TreeBlockType.FENCE_GATE, SophoraFenceGate::new)
            .blockEntity(TreeBlockType.DOOR, SophoraDoorEntity::new)
            .blockEntity(TreeBlockType.TRAPDOOR, SophoraEntity::trapdoor)
            .blockEntity(TreeBlockType.FENCE_GATE, SophoraEntity::fenceGate)
            .blockTags(SFDBlockTags.JUJUBE_BLOCK)
            .grower(t -> new TreeConfiguration.TreeConfigurationBuilder(
                    BlockStateProvider.simple(t.getBlock(TreeBlockType.LOG)),
                    new FancyTrunkPlacer(3, 11, 0),
                    BlockStateProvider.simple(t.getBlock(TreeBlockType.LEAVES)),
                    new FancyFoliagePlacer(ConstantInt.of(2), ConstantInt.of(4), 4),
                    new TwoLayersFeatureSize(0, 0, 0, OptionalInt.of(4))).ignoreVines().build())
            .tab(TabsRegistry.items(SinoSeriesTabs.BUILDING_BLOCKS))
            .tab(TreeBlockType.SAPLING, TabsRegistry.items(SinoSeriesTabs.AGRICULTURE))
            .build();

    static {
        for (var entry : BlockStrippingHandler.getDeferredBlockStrippingMap().entrySet()) {
            BlockStrippingHandler.registerStripping(entry.getKey(), entry.getValue().getLeft(), entry.getValue().getMiddle(), SFDItems.TREE_BARK);
        }
    }

    public static void register(IEventBus bus) {
        TreeRegistry.register(SinoDivination.MODID, bus);
        TreeRegistry.getRegistry().get(SinoDivination.MODID)
                .forEach(tree -> BlockStrippingHandler.registerStripping(tree, SFDItems.TREE_BARK));
    }
}
