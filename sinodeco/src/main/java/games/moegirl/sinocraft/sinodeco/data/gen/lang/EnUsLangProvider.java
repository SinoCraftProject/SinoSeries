package games.moegirl.sinocraft.sinodeco.data.gen.lang;

import games.moegirl.sinocraft.sinocore.data.gen.AbstractLanguageProvider;
import games.moegirl.sinocraft.sinocore.data.gen.IDataGenContext;
import games.moegirl.sinocraft.sinocore.data.gen.delegate.LanguageProviderDelegateBase;
import games.moegirl.sinocraft.sinodeco.block.SDBlocks;
import games.moegirl.sinocraft.sinodeco.item.SDItems;

public class EnUsLangProvider extends AbstractLanguageProvider {
    public EnUsLangProvider(IDataGenContext context) {
        super(context, "en_us");
    }

    @Override
    public void generateData(LanguageProviderDelegateBase delegate) {
        delegate.addBlock(SDBlocks.WOODEN_TABLE.get(), "Wooden Table");

        delegate.addTab(SDItems.SINO_DECO_TAB, "SinoDeco");
    }
}
