package games.moegirl.sinocraft.sinofoundation.block;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;

public class BambooPlaqueBlock extends Block {
    public BambooPlaqueBlock() {
        super(Properties.copy(Blocks.BAMBOO_PLANKS)
                .noOcclusion()
                .strength(1));
    }

    // Todo: block entity
}
