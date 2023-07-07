package games.moegirl.sinocraft.sinocore.mixins.interfaces;

import net.minecraft.world.level.block.Block;

/**
 * @author luqin2007
 */
public interface IBlockLootSubProvider {

    Iterable<Block> sino$getKnownBlocks();
}
