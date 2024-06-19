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

        delegate.addBlock(SDBlocks.MARBLE_BLOCK.get(), "大理石块");
        delegate.addBlock(SDBlocks.CHISELED_MARBLE.get(), "雕纹大理石块");
        delegate.addBlock(SDBlocks.MARBLE_PILLAR.get(), "大理石柱");
        delegate.addBlock(SDBlocks.MARBLE_STAIRS.get(), "大理石楼梯");
        delegate.addBlock(SDBlocks.MARBLE_SLAB.get(), "大理石台阶");
        delegate.addBlock(SDBlocks.SMOOTH_MARBLE.get(), "平滑大理石块");
        delegate.addBlock(SDBlocks.SMOOTH_MARBLE_STAIRS.get(), "平滑大理石楼梯");
        delegate.addBlock(SDBlocks.SMOOTH_MARBLE_SLAB.get(), "平滑大理石台阶");
        delegate.addBlock(SDBlocks.MARBLE_BRICKS.get(), "大理石砖");
        delegate.addBlock(SDBlocks.MARBLE_WALL.get(), "大理石墙");

        delegate.addTab(SDItems.SINO_DECO_TAB, "华夏工缀");
    }
}
