package games.moegirl.sinocraft.sinocore.datagen.forge;

import games.moegirl.sinocraft.sinocore.datagen.IDataGenContext;
import games.moegirl.sinocraft.sinocore.datagen.delegate.LanguageProviderDelegateBase;
import games.moegirl.sinocraft.sinocore.datagen.forge.impl.ForgeLanguageProviderDelegateImpl;

public class AbstractLanguageProviderImpl {

    public static LanguageProviderDelegateBase createDelegate(IDataGenContext context, String locale) {
        return new ForgeLanguageProviderDelegateImpl(context.getOutput(), context.getModId(), locale);
    }
}
