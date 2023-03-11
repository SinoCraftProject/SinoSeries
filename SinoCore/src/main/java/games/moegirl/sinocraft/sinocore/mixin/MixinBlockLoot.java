package games.moegirl.sinocraft.sinocore.mixin;

import games.moegirl.sinocraft.sinocore.old.IBlockLoot;
import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.world.level.block.Block;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(BlockLootSubProvider.class)
public abstract class MixinBlockLoot implements IBlockLoot {

    @Shadow(remap = false) protected abstract Iterable<Block> getKnownBlocks();

    @Override
    public Iterable<Block> sinocoreGetKnownBlocks() {
        return getKnownBlocks();
    }
}
