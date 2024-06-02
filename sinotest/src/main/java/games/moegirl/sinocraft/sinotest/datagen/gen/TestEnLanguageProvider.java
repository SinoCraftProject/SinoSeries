package games.moegirl.sinocraft.sinotest.datagen.gen;

import games.moegirl.sinocraft.sinocore.data.gen.AbstractLanguageProvider;
import games.moegirl.sinocraft.sinocore.data.gen.IDataGenContext;
import games.moegirl.sinocraft.sinocore.data.gen.delegate.LanguageProviderDelegateBase;
import games.moegirl.sinocraft.sinotest.datagen.TestLangKeys;
import games.moegirl.sinocraft.sinotest.registry.TestRegistry;

public class TestEnLanguageProvider extends AbstractLanguageProvider {

    public TestEnLanguageProvider(IDataGenContext context) {
        super(context, "en_us");
    }

    @Override
    public void generateData(LanguageProviderDelegateBase delegate) {
        delegate.addTab(TestRegistry.TEST_TAB, "SinoTest: Test Tab");
        delegate.addItem(TestRegistry.TEST_ITEM_MC_TAB, "Test Item: In building tab");
        delegate.addItem(TestRegistry.TEST_ITEM_MOD_TAB, "Test Item: In mod tab");
        delegate.addItem(TestRegistry.TEST_ITEM_MOD_MC_TAB, "Test Item: In mod and building tab");
        delegate.addBlock(TestRegistry.TEST_BLOCK, "Test Block");
        delegate.add(TestLangKeys.TEST_WITH_ITEM, "Your offhand has item with test tag.");
        delegate.add(TestLangKeys.TEST_WITH_BLOCK, "You clicked a block with test tag.");
    }
}
