package games.moegirl.sinocraft.sinocore.mixin_inter;

import net.minecraft.world.level.block.Block;

public interface IBlockLoot {

    Iterable<Block> sinocoreGetKnownBlocks();
}
