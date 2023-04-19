package games.moegirl.sinocraft.sinocore.data;

import games.moegirl.sinocraft.sinocore.tree.TreeRegistry;
import net.minecraft.data.PackOutput;
import net.minecraftforge.common.data.LanguageProvider;

public abstract class LanguageProviderBase extends LanguageProvider {
    protected String modid;
    protected String locale;

    public LanguageProviderBase(PackOutput output, String modid, String locale) {
        super(output, modid, locale);

        this.modid = modid;
        this.locale = locale;
    }

    @Override
    protected void addTranslations() {
        addTreeTranslates();

        translate();
    }

    public abstract void translate();

    protected void addTreeTranslates() {
        var trees = TreeRegistry.getRegistry().get(modid);

        if (trees != null && !trees.isEmpty()) {
            for (var tree : trees) {
                var translates = tree.makeTranslatesForLocale(locale);
                for (var entry : translates.entrySet()) {
                    add(entry.getKey(), entry.getValue());
                }
            }
        }
    }
}
