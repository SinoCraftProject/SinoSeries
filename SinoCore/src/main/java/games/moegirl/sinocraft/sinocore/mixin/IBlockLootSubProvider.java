package games.moegirl.sinocraft.sinocore.mixin;

import net.minecraft.world.level.block.Block;

/**
 * @author luqin2007
 */
public interface IBlockLootSubProvider {

    Iterable<Block> sinocore$getKnownBlocks();
}
