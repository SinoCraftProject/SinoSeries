package games.moegirl.sinocraft.sinofoundation.data.lang;

import games.moegirl.sinocraft.sinocore.data.abstracted.AbstractLanguageProvider;
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
    }
}
