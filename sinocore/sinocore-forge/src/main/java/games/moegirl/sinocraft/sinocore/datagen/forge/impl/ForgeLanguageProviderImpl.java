package games.moegirl.sinocraft.sinocore.datagen.forge.impl;

import games.moegirl.sinocraft.sinocore.datagen.IDataGenContext;
import net.minecraftforge.common.data.LanguageProvider;

public class ForgeLanguageProviderImpl extends LanguageProvider {
    private ForgeLanguageProviderDelegateImpl delegate;

    ForgeLanguageProviderImpl(IDataGenContext context, String locale) {
        super(context.getOutput(), context.getModId(), locale);
    }

    public void setDelegate(ForgeLanguageProviderDelegateImpl delegate) {
        this.delegate = delegate;
    }

    @Override
    protected void addTranslations() {
        delegate.generateData();
    }
}
