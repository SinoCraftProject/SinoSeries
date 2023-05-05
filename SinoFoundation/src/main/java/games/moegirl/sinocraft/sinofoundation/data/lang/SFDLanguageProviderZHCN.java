package games.moegirl.sinocraft.sinofoundation.data.lang;

import games.moegirl.sinocraft.sinocore.data.abstracted.AbstractLanguageProvider;
import games.moegirl.sinocraft.sinofoundation.item.SinoSeriesTabs;
import net.minecraft.data.PackOutput;

public class SFDLanguageProviderZHCN extends AbstractLanguageProvider {
    public SFDLanguageProviderZHCN(PackOutput output, String modid, String locale) {
        super(output, modid, locale);
    }

    @Override
    public void translate() {
        addTab(SinoSeriesTabs.BUILDING_BLOCKS, "华夏系列 | 建筑方块");
//        addTab(SinoSeriesTabs.FUNCTIONAL_BLOCKS, "华夏系列 | 功能性方块");
        addTab(SinoSeriesTabs.AGRICULTURE, "华夏系列 | 农业");
        addTab(SinoSeriesTabs.TOOLS, "华夏系列 | 工具与实用物品");
        addTab(SinoSeriesTabs.WEAPONS, "华夏系列 | 战斗用品");
        addTab(SinoSeriesTabs.MATERIALS, "华夏系列 | 原材料");
        addTab(SinoSeriesTabs.MISC, "华夏系列 | 杂项");
    }
}
