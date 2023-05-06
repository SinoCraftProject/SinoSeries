package games.moegirl.sinocraft.sinodivination.old.tree;

import games.moegirl.sinocraft.sinocore.tree.Tree;
import games.moegirl.sinocraft.sinocore.tree.TreeBlockType;
import games.moegirl.sinocraft.sinocore.tree.TreeRegistry;
import games.moegirl.sinocraft.sinocore.tree.TreeUtilities;
import games.moegirl.sinocraft.sinodivination.SinoDivination;
import games.moegirl.sinocraft.sinodivination.old.block.*;
import games.moegirl.sinocraft.sinodivination.old.data.SDTags;
import net.minecraft.data.worldgen.features.TreeFeatures;
import net.minecraft.util.valueproviders.ConstantInt;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.levelgen.feature.featuresize.TwoLayersFeatureSize;
import net.minecraft.world.level.levelgen.feature.foliageplacers.BlobFoliagePlacer;
import net.minecraft.world.level.levelgen.feature.trunkplacers.StraightTrunkPlacer;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;

public class SDTrees {

    public static final Tree COTINUS = Tree.builder(SinoDivination.MODID, "cotinus")
            .block(TreeBlockType.DOOR, CotinusDoor::new)
            .block(TreeBlockType.TRAPDOOR, CotinusTrapdoor::new)
            .block(TreeBlockType.FENCE_GATE, CotinusFenceGate::new)
            .blockTags(SDTags.COTINUS_BLOCK)
            .grower(tree -> TreeUtilities.copy(tree, TreeFeatures.OAK,
                    new StraightTrunkPlacer(4, 2, 0),
                    new BlobFoliagePlacer(ConstantInt.of(2), ConstantInt.of(0), 3),
                    new TwoLayersFeatureSize(1, 0, 1)).build())
            .build();

    public static final Tree JUJUBE = Tree.builder(SinoDivination.MODID, "jujube")
            .block(TreeBlockType.LEAVES, JujubeLeaves::new)
            .block(TreeBlockType.DOOR, JujubeDoor::new)
            .block(TreeBlockType.TRAPDOOR, JujubeTrapdoor::new)
            .block(TreeBlockType.FENCE_GATE, JujubeFenceGate::new)
            .blockTags(SDTags.SOPHORA_BLOCK)
            .itemTags()
            .blockProperty(b -> b.destroyTime *= 2)
            .grower(tree -> TreeUtilities.copy(tree, TreeFeatures.OAK,
                    new StraightTrunkPlacer(4, 2, 0),
                    new BlobFoliagePlacer(ConstantInt.of(2), ConstantInt.of(0), 3),
                    new TwoLayersFeatureSize(1, 0, 1)).build())
            .build();

    public static final Tree SOPHORA = Tree.builder(SinoDivination.MODID, "sophora")
            .block(TreeBlockType.DOOR, SophoraDoor::new)
            .block(TreeBlockType.TRAPDOOR, SophoraTrapdoor::new)
            .block(TreeBlockType.FENCE_GATE, SophoraFenceGate::new)
            .grower(tree -> TreeUtilities.copy(tree, TreeFeatures.OAK,
                    new StraightTrunkPlacer(4, 2, 0),
                    new BlobFoliagePlacer(ConstantInt.of(2), ConstantInt.of(0), 3),
                    new TwoLayersFeatureSize(1, 0, 1)).build())
            .build();

    public static void register(IEventBus bus, DeferredRegister<Block> blockRegister, DeferredRegister<Item> itemRegister) {
        TreeRegistry.register(SinoDivination.MODID, bus, blockRegister, itemRegister);
    }
}
