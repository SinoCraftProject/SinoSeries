package games.moegirl.sinocraft.sinotest.sinocore.tree;

import games.moegirl.sinocraft.sinocore.data.abstracted.AbstractLanguageProvider;
import net.minecraft.data.PackOutput;

public class TestZhTwLanguageProvider extends AbstractLanguageProvider {
    public TestZhTwLanguageProvider(PackOutput output, String modid, String locale) {
        super(output, modid, locale);
    }

    @Override
    public void translate() {
        add("zh_tw", "正體中文");
    }
}
