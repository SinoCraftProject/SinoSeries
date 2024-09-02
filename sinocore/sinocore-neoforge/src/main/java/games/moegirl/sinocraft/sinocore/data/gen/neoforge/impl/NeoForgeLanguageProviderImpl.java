package games.moegirl.sinocraft.sinocore.data.gen.neoforge.impl;

import games.moegirl.sinocraft.sinocore.data.gen.IDataGenContext;
import net.neoforged.neoforge.common.data.LanguageProvider;

public class NeoForgeLanguageProviderImpl extends LanguageProvider {
    private NeoForgeLanguageProviderDelegate delegate;

    NeoForgeLanguageProviderImpl(IDataGenContext context, String locale) {
        super(context.getOutput(), context.getModId(), locale);
    }

    public void setDelegate(NeoForgeLanguageProviderDelegate delegate) {
        this.delegate = delegate;
    }

    @Override
    protected void addTranslations() {
        delegate.generateData();
    }
}
