package games.moegirl.sinocraft.sinodeco.data.gen.tag;

import games.moegirl.sinocraft.sinocore.data.gen.AbstractItemTagsProvider;
import games.moegirl.sinocraft.sinocore.data.gen.IDataGenContext;
import games.moegirl.sinocraft.sinodeco.block.item.SDBlockItems;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.tags.TagsProvider;
import net.minecraft.world.level.block.Block;

public class ItemTagsProvider extends AbstractItemTagsProvider {
    public ItemTagsProvider(IDataGenContext context, TagsProvider<Block> blockTagsProvider) {
        super(context, blockTagsProvider);
    }

    @Override
    protected void addTags(HolderLookup.Provider provider) {
        tag(SDItemTags.MARBLE_BLOCKS).add(SDBlockItems.MARBLE_BLOCK.get(),
                SDBlockItems.CHISELED_MARBLE.get(), SDBlockItems.MARBLE_PILLAR.get());
    }
}
