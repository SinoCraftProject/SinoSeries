package games.moegirl.sinocraft.sinocore.datagen.forge.impl;

import net.minecraft.data.PackOutput;
import net.minecraftforge.common.data.LanguageProvider;

public class ForgeLanguageProviderImpl extends LanguageProvider {
    private ForgeLanguageProviderDelegateImpl delegate;

    ForgeLanguageProviderImpl(PackOutput output, String modId, String locale) {
        super(output, modId, locale);
    }

    public void setDelegate(ForgeLanguageProviderDelegateImpl delegate) {
        this.delegate = delegate;
    }

    @Override
    protected void addTranslations() {
//        addTreeTranslates();
        translate();
    }

    public void translate() {
        delegate.generateData();
    }

//    protected void addTreeTranslates() {
//        for (Tree tree : TreeRegistry.getTrees(modid)) {
//            tree.getTranslator().addTranslatesForLocale(locale, tree, this);
//        }
//    }
}
