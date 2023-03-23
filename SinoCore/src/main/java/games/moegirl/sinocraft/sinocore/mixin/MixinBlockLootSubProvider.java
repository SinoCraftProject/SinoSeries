package games.moegirl.sinocraft.sinocore.mixin;

import games.moegirl.sinocraft.sinocore.mixin_inter.IBlockLootSubProvider;
import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.world.level.block.Block;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

/**
 * @author luqin2007
 */
@Mixin(BlockLootSubProvider.class)
public abstract class MixinBlockLootSubProvider implements IBlockLootSubProvider {

    @Shadow(remap = false) protected abstract Iterable<Block> getKnownBlocks();

    /**
     * 获取 BlockLootSubProvider 已添加的所有方块，等效于 getKnownBlocks 方法，用于将其中的内容从另一个 LootTableProvider 中移除，
     * 以防重复注册
     */
    @Override
    public Iterable<Block> sinocoreGetKnownBlocks() {
        return getKnownBlocks();
    }
}
