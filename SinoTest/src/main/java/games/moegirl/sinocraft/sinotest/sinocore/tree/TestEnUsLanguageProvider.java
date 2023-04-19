package games.moegirl.sinocraft.sinotest.sinocore.tree;

import games.moegirl.sinocraft.sinocore.data.abstracted.AbstractLanguageProvider;
import net.minecraft.data.PackOutput;

public class TestEnUsLanguageProvider extends AbstractLanguageProvider {
    public TestEnUsLanguageProvider(PackOutput output, String modid, String locale) {
        super(output, modid, locale);
    }

    @Override
    public void translate() {
        add("en_us", "English");
    }
}
