package games.moegirl.sinocraft.sinofoundation.data.lang;

import games.moegirl.sinocraft.sinocore.data.abstracted.AbstractLanguageProvider;
import games.moegirl.sinocraft.sinofoundation.item.SFDItems;
import games.moegirl.sinocraft.sinofoundation.item.SinoSeriesTabs;
import net.minecraft.data.PackOutput;

public class SFDLanguageProviderZHTW extends AbstractLanguageProvider {
    public SFDLanguageProviderZHTW(PackOutput output, String modid, String locale) {
        super(output, modid, locale);
    }

    @Override
    public void translate() {
        addTab(SinoSeriesTabs.BUILDING_BLOCKS, "華夏系列 | 建築方塊");
//        addTab(SinoSeriesTabs.FUNCTIONAL_BLOCKS, "華夏系列 | 功能性方塊");
        addTab(SinoSeriesTabs.AGRICULTURE, "華夏系列 | 農業");
        addTab(SinoSeriesTabs.TOOLS, "華夏系列 | 工具與實用物品");
        addTab(SinoSeriesTabs.WEAPONS, "華夏系列 | 戰鬥用品");
        addTab(SinoSeriesTabs.MATERIALS, "華夏系列 | 原材料");
        addTab(SinoSeriesTabs.MISC, "華夏系列 | 雜項");

        add(SFDItems.IRON_KNIFE.get(), "鐵質小刀");
        add(SFDItems.GOLD_KNIFE.get(), "金質小刀");
        add(SFDItems.DIAMOND_KNIFE.get(), "鑽石小刀");
    }
}
