package games.moegirl.sinocraft.sinotest.datagen.gen;

import games.moegirl.sinocraft.sinocore.data.gen.AbstractItemTagsProvider;
import games.moegirl.sinocraft.sinocore.data.gen.IDataGenContext;
import games.moegirl.sinocraft.sinotest.datagen.TestTags;
import games.moegirl.sinocraft.sinotest.registry.TestRegistry;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.tags.TagsProvider;
import net.minecraft.world.level.block.Block;

public class TestItemTagsProvider extends AbstractItemTagsProvider {

    public TestItemTagsProvider(IDataGenContext context, TagsProvider<Block> blockTagsProvider) {
        super(context, blockTagsProvider);
    }

    @Override
    protected void addTags(HolderLookup.Provider provider) {
        tag(TestTags.TEST_ITEM_TAG).add(TestRegistry.TEST_ITEM_MOD_TAB.getKey());
    }
}
