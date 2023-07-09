package games.moegirl.sinocraft.sinofoundation.data.gen.lang;

import games.moegirl.sinocraft.sinocore.data.gen.lang.AbstractLanguageProvider;
import games.moegirl.sinocraft.sinofoundation.SFDConstants;
import games.moegirl.sinocraft.sinofoundation.block.SFDBlocks;
import games.moegirl.sinocraft.sinofoundation.item.SFDItems;
import games.moegirl.sinocraft.sinofoundation.item.SinoSeriesTabs;
import net.minecraft.data.PackOutput;

public class SFDLanguageProviderZHL extends AbstractLanguageProvider {
    public SFDLanguageProviderZHL(PackOutput output, String modid, String locale) {
        super(output, modid, locale);
    }

    @Override
    public void translate() {
        addTab(SinoSeriesTabs.BUILDING_BLOCKS, "夏藝 | 築方");
        addTab(SinoSeriesTabs.FUNCTIONAL_BLOCKS, "夏藝 | 用方");
        addTab(SinoSeriesTabs.AGRICULTURE, "夏藝 | 農事");
        addTab(SinoSeriesTabs.TOOLS, "夏藝 | 用物");
        addTab(SinoSeriesTabs.WEAPONS, "夏藝 | 兵者");
        addTab(SinoSeriesTabs.MATERIALS, "夏藝 | 原材");
        addTab(SinoSeriesTabs.MISC, "夏藝 | 雜項");

        // Fixme: qyl27: LZH translation.
        add(SFDItems.IRON_KNIFE.get(), "铁厨刀");
        add(SFDItems.GOLD_KNIFE.get(), "金厨刀");
        add(SFDItems.DIAMOND_KNIFE.get(), "钻石厨刀");
        add(SFDItems.ASHES.get(), "燼");
        add(SFDItems.TREE_BARK.get(), "树皮");

        add(SFDBlocks.STOVE.get(), "灶");

        add(SFDConstants.TRANSLATE_STOVE_FULL_OF_ASHES, "灶台被灰烬填满了。空手右键取出灰烬，空手 Shift + 右键清除灰烬。");
        add(SFDConstants.TRANSLATE_STOVE_NOT_A_FUEL, "你发现手中的物品不是燃料。");
        add(SFDConstants.TRANSLATE_STOVE_EMPTY_NOW, "灶台里面现在是空的。");
    }
}
