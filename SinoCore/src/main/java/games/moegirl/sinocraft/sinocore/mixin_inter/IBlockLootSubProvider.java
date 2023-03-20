package games.moegirl.sinocraft.sinocore.mixin_inter;

import net.minecraft.world.level.block.Block;

public interface IBlockLootSubProvider {

    Iterable<Block> sinocoreGetKnownBlocks();
}
