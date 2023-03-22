package games.moegirl.sinocraft.sinocore.data;

import games.moegirl.sinocraft.sinocore.tree.Tree;
import net.minecraft.data.PackOutput;
import net.minecraftforge.common.data.LanguageProvider;

import java.util.Locale;

public abstract class LanguageProviderBase extends LanguageProvider {
    protected String locale;

    public LanguageProviderBase(PackOutput output, String modid, String locale) {
        super(output, modid, locale);

        this.locale = locale;
    }

    @Override
    protected void addTranslations() {
        addTreeTranslates();

        translate();
    }

    public abstract void translate();

    protected void addTreeTranslates() {
        var treeRegistry = Tree.getRegistry();
        for (var tree : treeRegistry.values()) {
            var translates = tree.makeTranslatesForLocale(locale);
            for (var entry : translates.entrySet()) {
                add(entry.getKey(), entry.getValue());
            }
        }
    }
}
