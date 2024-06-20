package games.moegirl.sinocraft.sinodeco.data.gen.tag;

import games.moegirl.sinocraft.sinocore.data.gen.AbstractBlockTagsProvider;
import games.moegirl.sinocraft.sinocore.data.gen.IDataGenContext;
import games.moegirl.sinocraft.sinodeco.block.SDBlocks;
import net.minecraft.core.HolderLookup;
import net.minecraft.tags.BlockTags;

public class BlockTagsProvider extends AbstractBlockTagsProvider {
    public BlockTagsProvider(IDataGenContext context) {
        super(context);
    }

    @Override
    protected void addTags(HolderLookup.Provider provider) {
        tag(BlockTags.WALLS).add(SDBlocks.MARBLE_BALUSTRADE.get());
        tag(SDBlockTags.MARBLE_BUILDING_BLOCKS).add(SDBlocks.MARBLE_BLOCK.get(), SDBlocks.CHISELED_MARBLE.get(),
                SDBlocks.MARBLE_PILLAR.get(), SDBlocks.MARBLE_STAIRS.get(), SDBlocks.MARBLE_SLAB.get(),
                SDBlocks.SMOOTH_MARBLE.get(), SDBlocks.SMOOTH_MARBLE_STAIRS.get(), SDBlocks.SMOOTH_MARBLE_SLAB.get(),
                SDBlocks.MARBLE_BRICKS.get(), SDBlocks.MARBLE_BALUSTRADE.get());
    }
}
