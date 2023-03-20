package games.moegirl.sinocraft.sinocore.block;

import games.moegirl.sinocraft.sinocore.tree.depr.woodwork.IWoodworkBlock;
import games.moegirl.sinocraft.sinocore.tree.depr.woodwork.Woodwork;
import net.minecraft.world.level.block.Block;

/**
 * A class for plank with tree
 */
public class ModPlankBlock extends Block implements IWoodworkBlock {

    private final Woodwork woodwork;

    public ModPlankBlock(Properties properties, Woodwork woodwork) {
        super(properties);
        this.woodwork = woodwork;
    }

    @Override
    public Woodwork getWoodwork() {
        return woodwork;
    }
}
