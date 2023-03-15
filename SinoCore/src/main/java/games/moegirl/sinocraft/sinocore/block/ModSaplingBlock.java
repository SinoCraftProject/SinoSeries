package games.moegirl.sinocraft.sinocore.block;

import games.moegirl.sinocraft.sinocore.tree.ITreeBlock;
import games.moegirl.sinocraft.sinocore.tree.Tree;
import net.minecraft.world.level.block.SaplingBlock;
import net.minecraft.world.level.block.grower.AbstractTreeGrower;
import net.minecraft.world.level.material.Material;

/**
 * 树苗
 */
public class ModSaplingBlock extends SaplingBlock implements ITreeBlock {

    private final Tree tree;

    public ModSaplingBlock(AbstractTreeGrower pTreeGrower, Properties pProperties, Tree tree) {
        super(pTreeGrower, pProperties);
        this.tree = tree;
    }

    public ModSaplingBlock(Tree tree) {
        this(tree.properties().grower(), Properties.of(Material.PLANT)
                .noCollission()
                .randomTicks()
                .strength(tree.properties().strengthModifier().apply(0), 0)
                .sound(tree.properties().saplingSound()), tree);
    }

    @Override
    public Tree getTree() {
        return tree;
    }
}
