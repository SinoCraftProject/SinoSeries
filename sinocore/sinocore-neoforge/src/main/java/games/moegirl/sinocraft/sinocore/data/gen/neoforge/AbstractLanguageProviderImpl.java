package games.moegirl.sinocraft.sinocore.data.gen.neoforge;

import games.moegirl.sinocraft.sinocore.data.gen.IDataGenContext;
import games.moegirl.sinocraft.sinocore.data.gen.delegate.LanguageProviderDelegateBase;
import games.moegirl.sinocraft.sinocore.data.gen.neoforge.impl.ForgeLanguageProviderDelegate;

public class AbstractLanguageProviderImpl {

    public static LanguageProviderDelegateBase createDelegate(IDataGenContext context, String locale) {
        return new ForgeLanguageProviderDelegate(context, locale);
    }
}
