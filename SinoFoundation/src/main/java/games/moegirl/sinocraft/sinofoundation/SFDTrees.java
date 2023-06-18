package games.moegirl.sinocraft.sinofoundation;

import games.moegirl.sinocraft.sinocore.event.BlockStrippingEvent;
import games.moegirl.sinocraft.sinocore.tab.TabsRegistry;
import games.moegirl.sinocraft.sinocore.tree.Tree;
import games.moegirl.sinocraft.sinocore.tree.TreeBlockType;
import games.moegirl.sinocraft.sinocore.tree.TreeRegistry;
import games.moegirl.sinocraft.sinofoundation.block.entity.CotinusDoorEntity;
import games.moegirl.sinocraft.sinofoundation.block.entity.CotinusEntity;
import games.moegirl.sinocraft.sinofoundation.block.entity.SophoraDoorEntity;
import games.moegirl.sinocraft.sinofoundation.block.entity.SophoraEntity;
import games.moegirl.sinocraft.sinofoundation.block.tree.*;
import games.moegirl.sinocraft.sinofoundation.data.SFDBlockTags;
import games.moegirl.sinocraft.sinofoundation.item.SFDItems;
import games.moegirl.sinocraft.sinofoundation.item.SinoSeriesTabs;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.valueproviders.ConstantInt;
import net.minecraft.world.level.levelgen.feature.configurations.TreeConfiguration;
import net.minecraft.world.level.levelgen.feature.featuresize.TwoLayersFeatureSize;
import net.minecraft.world.level.levelgen.feature.foliageplacers.BlobFoliagePlacer;
import net.minecraft.world.level.levelgen.feature.foliageplacers.FancyFoliagePlacer;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.minecraft.world.level.levelgen.feature.trunkplacers.FancyTrunkPlacer;
import net.minecraft.world.level.levelgen.feature.trunkplacers.StraightTrunkPlacer;
import net.minecraftforge.eventbus.api.IEventBus;

import java.util.OptionalInt;

public class SFDTrees {

    public static final Tree MULBERRY = Tree.builder(new ResourceLocation(SinoFoundation.MODID, "mulberry"))
            .translate("zh_cn", "桑树")
            .translate("zh_tw", "桑樹")
            .translate("zh_hk", "桑樹")
            .translate("lzh", "桑")
            .translate("en_us", "Mulberry")
            .grower(t -> new TreeConfiguration.TreeConfigurationBuilder(
                    BlockStateProvider.simple(t.getBlock(TreeBlockType.LOG)),
                    new StraightTrunkPlacer(4, 2, 0),
                    BlockStateProvider.simple(t.getBlock(TreeBlockType.LEAVES)),
                    new BlobFoliagePlacer(ConstantInt.of(2), ConstantInt.of(0), 3),
                    new TwoLayersFeatureSize(1, 0, 1)).build())
            .tab(TabsRegistry.items(SinoSeriesTabs.BUILDING_BLOCKS))
            .tab(TreeBlockType.SAPLING, TabsRegistry.items(SinoSeriesTabs.AGRICULTURE))
            .build();

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
            .build();

    public static final Tree JUJUBE = Tree.builder(SinoFoundation.MODID, "jujube")
            .translate("zh_cn", "枣心")
            .translate(TreeBlockType.SAPLING, "zh_cn", "枣树苗")
            .translate(TreeBlockType.LEAVES, "zh_cn", "枣树叶")
            .block(TreeBlockType.LEAVES, JujubeLeaves::new)
            .block(TreeBlockType.DOOR, JujubeDoor::new)
            .block(TreeBlockType.TRAPDOOR, JujubeTrapdoor::new)
            .block(TreeBlockType.FENCE_GATE, JujubeFenceGate::new)
            .blockTags(SFDBlockTags.SOPHORA_BLOCK)
            .blockProperty(b -> b.destroyTime *= 2)
            .grower(t -> new TreeConfiguration.TreeConfigurationBuilder(
                    BlockStateProvider.simple(t.getBlock(TreeBlockType.LOG)),
                    new FancyTrunkPlacer(3, 11, 0),
                    BlockStateProvider.simple(t.getBlock(TreeBlockType.LEAVES)),
                    new FancyFoliagePlacer(ConstantInt.of(2), ConstantInt.of(4), 4),
                    new TwoLayersFeatureSize(0, 0, 0, OptionalInt.of(4))).ignoreVines().build())
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
            .build();

    static {
        for (var entry : BlockStrippingEvent.getDeferredBlockStrippingMap().entrySet()) {
            BlockStrippingEvent.registerStripping(entry.getKey(), entry.getValue().getLeft(), entry.getValue().getMiddle(), SFDItems.TREE_BARK);
        }
    }

    public static void register(IEventBus bus) {
        TreeRegistry.register(SinoFoundation.MODID, bus);
        TreeRegistry.getRegistry().get(SinoFoundation.MODID)
                .forEach(tree -> BlockStrippingEvent.registerStripping(tree, SFDItems.TREE_BARK));
    }
}
