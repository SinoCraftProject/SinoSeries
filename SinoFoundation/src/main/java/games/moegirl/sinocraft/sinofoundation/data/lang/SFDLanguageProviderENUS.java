package games.moegirl.sinocraft.sinofoundation.data.lang;

import games.moegirl.sinocraft.sinocore.data.abstracted.AbstractLanguageProvider;
import games.moegirl.sinocraft.sinofoundation.item.SinoSeriesTabs;
import net.minecraft.data.PackOutput;

public class SFDLanguageProviderENUS extends AbstractLanguageProvider {
    public SFDLanguageProviderENUS(PackOutput output, String modid, String locale) {
        super(output, modid, locale);
    }

    @Override
    public void translate() {
        addTab(SinoSeriesTabs.BUILDING_BLOCKS, "SinoSeries | Building Blocks");
        addTab(SinoSeriesTabs.FUNCTIONAL_BLOCKS, "SinoSeries | Functional Blocks");
        addTab(SinoSeriesTabs.AGRICULTURE, "SinoSeries | Agriculture");
        addTab(SinoSeriesTabs.TOOLS, "SinoSeries | Tools & Utilities");
        addTab(SinoSeriesTabs.WEAPONS, "SinoSeries | Combat");
        addTab(SinoSeriesTabs.MATERIALS, "SinoSeries | Ingredients");
        addTab(SinoSeriesTabs.MISC, "SinoSeries | Misc");
    }
}
