package games.moegirl.sinocraft.sinocore.tree;

import net.minecraft.world.level.block.grower.AbstractTreeGrower;

import javax.annotation.Nonnull;

/**
 * 如何长成一棵树？
 *
 * @author luqin2007
 */
public abstract class TreeSaplingGrower extends AbstractTreeGrower {

    @Nonnull
    protected Tree tree = null;

    void setTree(Tree tree) {
        this.tree = tree;
    }
}
