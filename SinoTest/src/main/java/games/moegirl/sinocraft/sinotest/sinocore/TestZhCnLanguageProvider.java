package games.moegirl.sinocraft.sinotest.sinocore;

import games.moegirl.sinocraft.sinocore.data.LanguageProviderBase;
import net.minecraft.data.PackOutput;

public class TestZhCnLanguageProvider extends LanguageProviderBase {
    public TestZhCnLanguageProvider(PackOutput output, String modid, String locale) {
        super(output, modid, locale);
    }

    @Override
    public void translate() {
        add("zh_cn", "简体中文");
    }
}
