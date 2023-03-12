package games.moegirl.sinocraft.sinocore.old.block;

import games.moegirl.sinocraft.sinocore.tree.Tree;
import net.minecraft.world.level.block.SaplingBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.grower.AbstractTreeGrower;
import net.minecraft.world.level.material.Material;

/**
 * A class for sapling with tree
 */
public class BlockTreeSapling extends SaplingBlock implements ITreeBlock {

    private final Tree tree;

    public BlockTreeSapling(AbstractTreeGrower pTreeGrower, Properties pProperties, Tree tree) {
        super(pTreeGrower, pProperties);
        this.tree = tree;
    }

    public BlockTreeSapling(Tree tree) {
        this(tree.properties().grower(), Properties.of(Material.PLANT)
                .noCollission()
                .randomTicks()
                .strength(tree.properties().strengthModifier().apply(0), 0)
                .sound(SoundType.GRASS), tree);
    }

    @Override
    public Tree getTree() {
        return tree;
    }
}
