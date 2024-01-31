package games.moegirl.sinocraft.sinotest.datagen.gen;

import games.moegirl.sinocraft.sinocore.datagen.AbstractLanguageProvider;
import games.moegirl.sinocraft.sinocore.datagen.IDataGenContext;
import games.moegirl.sinocraft.sinocore.datagen.delegate.LanguageProviderDelegateBase;
import games.moegirl.sinocraft.sinotest.datagen.TestLangKeys;
import games.moegirl.sinocraft.sinotest.registry.TestRegistry;

public class TestZhLanguageProvider extends AbstractLanguageProvider {

    public TestZhLanguageProvider(IDataGenContext context) {
        super(context, "zh_cn");
    }

    @Override
    public void generateData(LanguageProviderDelegateBase delegate) {
        delegate.addTab(TestRegistry.TEST_TAB, "SinoTest: 测试标签");
        delegate.addItem(TestRegistry.TEST_ITEM_MC_TAB, "测试物品：位于建筑物品标签");
        delegate.addItem(TestRegistry.TEST_ITEM_MOD_TAB, "测试物品：位于 Mod 标签");
        delegate.addItem(TestRegistry.TEST_ITEM_MOD_MC_TAB, "测试物品：位于两个标签");
        delegate.addBlock(TestRegistry.TEST_BLOCK, "测试方块");
        delegate.add(TestLangKeys.TEST_WITH_ITEM, "你的副手物品携带了测试标签。");
        delegate.add(TestLangKeys.TEST_WITH_BLOCK, "你点击的方块带有测试标签。");
    }
}
