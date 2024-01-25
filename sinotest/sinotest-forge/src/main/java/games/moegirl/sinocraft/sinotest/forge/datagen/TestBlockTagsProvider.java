package games.moegirl.sinocraft.sinotest.forge.datagen;

import games.moegirl.sinocraft.sinocore.datagen.AbstractBlockTagsProvider;
import games.moegirl.sinocraft.sinocore.datagen.IDataGenContext;
import games.moegirl.sinocraft.sinotest.data.TestTags;
import games.moegirl.sinocraft.sinotest.registry.TestRegistry;
import net.minecraft.core.HolderLookup;

public class TestBlockTagsProvider extends AbstractBlockTagsProvider {

    public TestBlockTagsProvider(IDataGenContext context) {
        super(context);
    }

    @Override
    protected void addTags(HolderLookup.Provider provider) {
        tag(TestTags.TEST_BLOCK_TAG).add(TestRegistry.TEST_BLOCK.getKey());
    }
}
