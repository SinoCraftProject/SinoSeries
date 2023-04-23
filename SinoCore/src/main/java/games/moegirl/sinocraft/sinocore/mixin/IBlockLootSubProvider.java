package games.moegirl.sinocraft.sinocore.mixin;

import net.minecraft.world.level.block.Block;

/**
 * TODO: 找不到 net.minecraft.data.loot.BlockLootSubProvider#getKnownBlocks() 的 at
 *
 * @author luqin2007
 */
public interface IBlockLootSubProvider {

    Iterable<Block> sinocore$getKnownBlocks();
}
