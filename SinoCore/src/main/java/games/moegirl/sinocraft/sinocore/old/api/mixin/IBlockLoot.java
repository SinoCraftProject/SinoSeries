package games.moegirl.sinocraft.sinocore.old.api.mixin;

import net.minecraft.world.level.block.Block;

public interface IBlockLoot {

    Iterable<Block> scGetKnownBlocks();
}
