package games.moegirl.sinocraft.sinotest.sinocore;

import games.moegirl.sinocraft.sinocore.data.LanguageProviderBase;
import net.minecraft.data.PackOutput;

public class TestEnUsLanguageProvider extends LanguageProviderBase {
    public TestEnUsLanguageProvider(PackOutput output, String modid, String locale) {
        super(output, modid, locale);
    }

    @Override
    public void translate() {
        add("en_us", "English");
    }
}
