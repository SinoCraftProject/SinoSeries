package games.moegirl.sinocraft.sinocore.mixins.mixin;

import games.moegirl.sinocraft.sinocore.mixins.interfaces.IBlockLootSubProvider;
import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.world.level.block.Block;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

/**
 * @author luqin2007
 */
@Mixin(BlockLootSubProvider.class)
public abstract class BlockLootSubProviderMixin implements IBlockLootSubProvider {

    @Shadow(remap = false) protected abstract Iterable<Block> getKnownBlocks();

    @Override
    public Iterable<Block> sino$getKnownBlocks() {
        return getKnownBlocks();
    }
}
