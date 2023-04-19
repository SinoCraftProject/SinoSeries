package games.moegirl.sinocraft.sinocore.data.abstracted;

import games.moegirl.sinocraft.sinocore.tree.TreeRegistry;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.common.data.LanguageProvider;

public abstract class AbstractLanguageProvider extends LanguageProvider {
    protected String modid;
    protected String locale;

    public AbstractLanguageProvider(PackOutput output, String modid, String locale) {
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

    protected void addTab(ResourceLocation name, String translate) {
        add("tab." + modid + "." + name.getPath(), translate);
    }
}
