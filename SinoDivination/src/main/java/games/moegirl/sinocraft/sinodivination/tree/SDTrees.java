package games.moegirl.sinocraft.sinodivination.tree;

import games.moegirl.sinocraft.sinocore.tree.Tree;
import games.moegirl.sinocraft.sinocore.tree.TreeBlockType;
import games.moegirl.sinocraft.sinocore.tree.TreeRegistry;
import games.moegirl.sinocraft.sinocore.tree.TreeUtilities;
import games.moegirl.sinocraft.sinodivination.SinoDivination;
import games.moegirl.sinocraft.sinodivination.block.*;
import games.moegirl.sinocraft.sinodivination.blockentity.*;
import games.moegirl.sinocraft.sinodivination.data.SDTags;
import games.moegirl.sinocraft.sinodivination.item.SDItems;
import net.minecraft.data.worldgen.features.TreeFeatures;
import net.minecraft.util.valueproviders.ConstantInt;
import net.minecraft.world.level.levelgen.feature.featuresize.TwoLayersFeatureSize;
import net.minecraft.world.level.levelgen.feature.foliageplacers.BlobFoliagePlacer;
import net.minecraft.world.level.levelgen.feature.trunkplacers.StraightTrunkPlacer;
import net.minecraftforge.eventbus.api.IEventBus;

public class SDTrees {

    public static final Tree COTINUS = Tree.builder(SinoDivination.MODID, "cotinus")
            .translate("zh_cn", "无患")
            .translate(TreeBlockType.CHEST, "zh_cn", "无患木匣")
            .block(TreeBlockType.DOOR, CotinusDoor::new)
            .block(TreeBlockType.TRAPDOOR, CotinusTrapdoor::new)
            .block(TreeBlockType.FENCE_GATE, CotinusFenceGate::new)
            .block(TreeBlockType.CHEST, CotinusChest::new)
            .block(TreeBlockType.TRAPPED_CHEST, CotinusTrappedChest::new)
            .blockEntity(TreeBlockType.DOOR, CotinusDoorEntity::new)
            .blockEntity(TreeBlockType.TRAPDOOR, CotinusEntity::trapdoor)
            .blockEntity(TreeBlockType.FENCE_GATE, CotinusEntity::fenceGate)
            .blockEntity(TreeBlockType.CHEST, CotinusChestEntity::new)
            .blockEntity(TreeBlockType.TRAPPED_CHEST, CotinusTrappedChestEntity::new)
            .blockTags(SDTags.COTINUS_BLOCK)
            .grower(tree -> TreeUtilities.copy(tree, TreeFeatures.OAK,
                    new StraightTrunkPlacer(4, 2, 0),
                    new BlobFoliagePlacer(ConstantInt.of(2), ConstantInt.of(0), 3),
                    new TwoLayersFeatureSize(1, 0, 1)).build())
            .build();

    public static final Tree JUJUBE = Tree.builder(SinoDivination.MODID, "jujube")
            .translate("zh_cn", "枣心")
            .translate(TreeBlockType.CHEST, "zh_cn", "枣木匣")
            .translate(TreeBlockType.SAPLING, "zh_cn", "枣树苗")
            .translate(TreeBlockType.LEAVES, "zh_cn", "枣树叶")
            .block(TreeBlockType.LEAVES, JujubeLeaves::new)
            .block(TreeBlockType.DOOR, JujubeDoor::new)
            .block(TreeBlockType.TRAPDOOR, JujubeTrapdoor::new)
            .block(TreeBlockType.FENCE_GATE, JujubeFenceGate::new)
            .block(TreeBlockType.CHEST, JujubeChest::new)
            .block(TreeBlockType.TRAPPED_CHEST, JujubeTrappedChest::new)
            .blockTags(SDTags.SOPHORA_BLOCK)
            .itemTags()
            .blockProperty(b -> b.destroyTime *= 2)
            .grower(tree -> TreeUtilities.copy(tree, TreeFeatures.OAK,
                    new StraightTrunkPlacer(4, 2, 0),
                    new BlobFoliagePlacer(ConstantInt.of(2), ConstantInt.of(0), 3),
                    new TwoLayersFeatureSize(1, 0, 1)).build())
            .build();

    public static final Tree SOPHORA = Tree.builder(SinoDivination.MODID, "sophora")
            .translate("zh_cn", "槐")
            .translate(TreeBlockType.CHEST, "zh_cn", "槐木匣")
            .block(TreeBlockType.DOOR, SophoraDoor::new)
            .block(TreeBlockType.TRAPDOOR, SophoraTrapdoor::new)
            .block(TreeBlockType.FENCE_GATE, SophoraFenceGate::new)
            .block(TreeBlockType.CHEST, SophoraChest::new)
            .block(TreeBlockType.TRAPPED_CHEST, SophoraTrappedChest::new)
            .blockEntity(TreeBlockType.DOOR, SophoraDoorEntity::new)
            .blockEntity(TreeBlockType.TRAPDOOR, SophoraEntity::trapdoor)
            .blockEntity(TreeBlockType.FENCE_GATE, SophoraEntity::fenceGate)
            .blockEntity(TreeBlockType.CHEST, SophoraChestEntity::new)
            .blockEntity(TreeBlockType.TRAPPED_CHEST, SophoraTrappedChestEntity::new)
            .grower(tree -> TreeUtilities.copy(tree, TreeFeatures.OAK,
                    new StraightTrunkPlacer(4, 2, 0),
                    new BlobFoliagePlacer(ConstantInt.of(2), ConstantInt.of(0), 3),
                    new TwoLayersFeatureSize(1, 0, 1)).build())
            .build();

    public static void register(IEventBus bus) {
        TreeRegistry.register(SinoDivination.MODID, bus, SDBlocks.REGISTRY, SDBlockEntities.REGISTRY, SDItems.REGISTRY);
    }
}
