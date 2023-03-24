package games.moegirl.sinocraft.sinotest.sinocore;

import games.moegirl.sinocraft.sinocore.data.LanguageProviderBase;
import net.minecraft.data.PackOutput;

public class TestZhTwLanguageProvider extends LanguageProviderBase {
    public TestZhTwLanguageProvider(PackOutput output, String modid, String locale) {
        super(output, modid, locale);
    }

    @Override
    public void translate() {
        add("zh_tw", "正體中文");
    }
}
