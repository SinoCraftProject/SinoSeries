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
        delegate.addBlock(SDBlocks.PEACH_WOOD_TABLE.get(), "桃木桌");

        delegate.addBlock(SDBlocks.MARBLE_BLOCK.get(), "大理石块");
        delegate.addBlock(SDBlocks.CHISELED_MARBLE.get(), "雕纹大理石块");
        delegate.addBlock(SDBlocks.MARBLE_PILLAR.get(), "大理石柱");
        delegate.addBlock(SDBlocks.MARBLE_STAIRS.get(), "大理石楼梯");
        delegate.addBlock(SDBlocks.MARBLE_SLAB.get(), "大理石台阶");
        delegate.addBlock(SDBlocks.SMOOTH_MARBLE.get(), "平滑大理石块");
        delegate.addBlock(SDBlocks.SMOOTH_MARBLE_STAIRS.get(), "平滑大理石楼梯");
        delegate.addBlock(SDBlocks.SMOOTH_MARBLE_SLAB.get(), "平滑大理石台阶");
        delegate.addBlock(SDBlocks.MARBLE_BRICKS.get(), "大理石砖");
        delegate.addBlock(SDBlocks.MARBLE_BALUSTRADE.get(), "大理石栏杆");

        delegate.addTab(SDItems.SINO_DECO_TAB, "华夏工缀");

        delegate.addBlock(SDBlocks.PEACH_LEAVES.get(), "桃树树叶");
        delegate.addBlock(SDBlocks.PEACH_SAPLING.get(), "桃树树苗");
        delegate.addBlock(SDBlocks.PEACH_LOG.get(), "桃木原木");
        delegate.addBlock(SDBlocks.PEACH_WOOD.get(), "桃木");
        delegate.addBlock(SDBlocks.STRIPPED_PEACH_LOG.get(), "去皮桃木原木");
        delegate.addBlock(SDBlocks.STRIPPED_PEACH_WOOD.get(), "去皮桃木");
        delegate.addBlock(SDBlocks.PEACH_PLANKS.get(), "桃木木板");
        delegate.addBlock(SDBlocks.PEACH_STAIRS.get(), "桃木楼梯");
        delegate.addBlock(SDBlocks.PEACH_SLAB.get(), "桃木台阶");
        delegate.addBlock(SDBlocks.PEACH_FENCE.get(), "桃木栅栏");
        delegate.addBlock(SDBlocks.PEACH_FENCE_GATE.get(), "桃木栅栏门");
        delegate.addBlock(SDBlocks.PEACH_DOOR.get(), "桃木门");
        delegate.addBlock(SDBlocks.PEACH_TRAPDOOR.get(), "桃木活板门");
        delegate.addBlock(SDBlocks.PEACH_PRESSURE_PLATE.get(), "桃木压力板");
        delegate.addBlock(SDBlocks.PEACH_BUTTON.get(), "桃木按钮");
        delegate.addBlock(SDBlocks.PEACH_SIGN.get(), "桃木告示牌");
        delegate.addBlock(SDBlocks.PEACH_HANGING_SIGN.get(), "悬挂式桃木告示牌");
        delegate.addBlock(SDBlocks.PEACH_CHEST.get(), "桃木匣");
        delegate.addItem(SDItems.PEACH_BOAT.get(), "桃木船");
        delegate.addItem(SDItems.PEACH_CHEST_BOAT.get(), "桃木运输船");
    }
}
