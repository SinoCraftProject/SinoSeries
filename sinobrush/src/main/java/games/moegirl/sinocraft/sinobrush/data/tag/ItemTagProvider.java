package games.moegirl.sinocraft.sinobrush.data.tag;

import games.moegirl.sinocraft.sinobrush.item.SBRItems;
import games.moegirl.sinocraft.sinocore.datagen.AbstractItemTagsProvider;
import games.moegirl.sinocraft.sinocore.datagen.IDataGenContext;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.tags.TagsProvider;
import net.minecraft.world.level.block.Block;

public class ItemTagProvider extends AbstractItemTagsProvider {
    public ItemTagProvider(IDataGenContext context, TagsProvider<Block> blockTagsProvider) {
        super(context, blockTagsProvider);
    }

    @Override
    protected void addTags(HolderLookup.Provider provider) {
        tag(SBRItemTags.FAN).add(SBRItems.FAN.get(), SBRItems.FOLDED_FAN.get());
        tag(SBRItemTags.XUAN_PAPER).add(SBRItems.XUAN_PAPER.get(), SBRItems.FILLED_XUAN_PAPER.get());

    }
}
