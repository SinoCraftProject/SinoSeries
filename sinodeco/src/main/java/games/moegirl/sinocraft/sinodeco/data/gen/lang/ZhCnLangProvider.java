package games.moegirl.sinocraft.sinodeco.data.gen.lang;

import games.moegirl.sinocraft.sinocore.data.gen.AbstractLanguageProvider;
import games.moegirl.sinocraft.sinocore.data.gen.IDataGenContext;
import games.moegirl.sinocraft.sinocore.data.gen.delegate.LanguageProviderDelegateBase;
import games.moegirl.sinocraft.sinodeco.block.SDBlocks;
import games.moegirl.sinocraft.sinodeco.item.SDItems;

public class ZhCnLangProvider extends AbstractLanguageProvider {
    public ZhCnLangProvider(IDataGenContext context) {
        super(context, "zh_cn");
    }

    @Override
    public void generateData(LanguageProviderDelegateBase delegate) {
        delegate.addBlock(SDBlocks.WOODEN_TABLE.get(), "木桌");

        delegate.addTab(SDItems.SINO_DECO_TAB, "华夏工缀");
    }
}
