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
        delegate.addBlock(SDBlocks.PEACH_WOOD_TABLE.get(), "Peach Wood Table");

        delegate.addBlock(SDBlocks.MARBLE_BLOCK.get(), "Marble Block");
        delegate.addBlock(SDBlocks.CHISELED_MARBLE.get(), "Chiseled Marble Block");
        delegate.addBlock(SDBlocks.MARBLE_PILLAR.get(), "Marble Pillar");
        delegate.addBlock(SDBlocks.MARBLE_STAIRS.get(), "Marble Stairs");
        delegate.addBlock(SDBlocks.MARBLE_SLAB.get(), "Marble Slab");
        delegate.addBlock(SDBlocks.SMOOTH_MARBLE.get(), "Smooth Marble Block");
        delegate.addBlock(SDBlocks.SMOOTH_MARBLE_STAIRS.get(), "Smooth Marble Stairs");
        delegate.addBlock(SDBlocks.SMOOTH_MARBLE_SLAB.get(), "Smooth Marble Slab");
        delegate.addBlock(SDBlocks.MARBLE_BRICKS.get(), "Marble Bricks");
        delegate.addBlock(SDBlocks.MARBLE_BALUSTRADE.get(), "Marble Balustrade");

        delegate.addTab(SDItems.SINO_DECO_TAB, "SinoDeco");
    }
}
