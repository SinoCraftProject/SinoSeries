package games.moegirl.sinocraft.sinofoundation.data.lang;

import games.moegirl.sinocraft.sinocore.data.abstracted.AbstractLanguageProvider;
import games.moegirl.sinocraft.sinofoundation.SFDConstants;
import games.moegirl.sinocraft.sinofoundation.block.SFDBlocks;
import games.moegirl.sinocraft.sinofoundation.item.SFDItems;
import games.moegirl.sinocraft.sinofoundation.item.SinoSeriesTabs;
import net.minecraft.data.PackOutput;

public class SFDLanguageProviderZHCN extends AbstractLanguageProvider {
    public SFDLanguageProviderZHCN(PackOutput output, String modid, String locale) {
        super(output, modid, locale);
    }

    @Override
    public void translate() {
        addTab(SinoSeriesTabs.BUILDING_BLOCKS, "华夏系列 | 建筑方块");
        addTab(SinoSeriesTabs.FUNCTIONAL_BLOCKS, "华夏系列 | 功能性方块");
        addTab(SinoSeriesTabs.AGRICULTURE, "华夏系列 | 农业");
        addTab(SinoSeriesTabs.TOOLS, "华夏系列 | 工具与实用物品");
        addTab(SinoSeriesTabs.WEAPONS, "华夏系列 | 战斗用品");
        addTab(SinoSeriesTabs.MATERIALS, "华夏系列 | 原材料");
        addTab(SinoSeriesTabs.MISC, "华夏系列 | 杂项");

        add(SFDItems.IRON_KNIFE.get(), "铁质小刀");
        add(SFDItems.GOLD_KNIFE.get(), "金质小刀");
        add(SFDItems.DIAMOND_KNIFE.get(), "钻石小刀");
        add(SFDItems.ASHES.get(), "炉灰");
        add(SFDItems.TREE_BARK.get(), "树皮");

        add(SFDBlocks.STOVE.get(), "灶台");

        add(SFDConstants.TRANSLATE_STOVE_FULL_OF_ASHES, "灶台被灰烬填满了。空手右键取出灰烬，空手 Shift + 右键清除全部灰烬。");
        add(SFDConstants.TRANSLATE_STOVE_NOT_A_FUEL, "你发现手中的物品不是燃料。");
        add(SFDConstants.TRANSLATE_STOVE_EMPTY_NOW, "灶台里面现在是空的。");
    }
}
