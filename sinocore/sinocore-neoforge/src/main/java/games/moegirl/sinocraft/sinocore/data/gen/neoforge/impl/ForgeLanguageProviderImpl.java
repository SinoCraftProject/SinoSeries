package games.moegirl.sinocraft.sinocore.data.gen.neoforge.impl;

import games.moegirl.sinocraft.sinocore.data.gen.IDataGenContext;
import net.minecraftforge.common.data.LanguageProvider;

public class ForgeLanguageProviderImpl extends LanguageProvider {
    private ForgeLanguageProviderDelegate delegate;

    ForgeLanguageProviderImpl(IDataGenContext context, String locale) {
        super(context.getOutput(), context.getModId(), locale);
    }

    public void setDelegate(ForgeLanguageProviderDelegate delegate) {
        this.delegate = delegate;
    }

    @Override
    protected void addTranslations() {
        delegate.generateData();
    }
}
