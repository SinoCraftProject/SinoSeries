package games.moegirl.sinocraft.sinocore.data;

import games.moegirl.sinocraft.sinocore.tree.Tree;
import net.minecraft.data.PackOutput;
import net.minecraftforge.common.data.LanguageProvider;

import java.util.Map;

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
        var trees = Tree.getRegistry()
                .entrySet()
                .stream()
                .filter(e -> e.getKey().getNamespace().equals(modid))
                .map(Map.Entry::getValue)
                .toList();

        for (var tree : trees) {
            var translates = tree.makeTranslatesForLocale(locale);
            for (var entry : translates.entrySet()) {
                add(entry.getKey(), entry.getValue());
            }
        }
    }
}
