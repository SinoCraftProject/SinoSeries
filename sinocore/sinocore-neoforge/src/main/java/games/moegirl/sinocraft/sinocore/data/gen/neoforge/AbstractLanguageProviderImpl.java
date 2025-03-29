package games.moegirl.sinocraft.sinocore.data.gen.neoforge;

import games.moegirl.sinocraft.sinocore.data.gen.DataGenContext;
import games.moegirl.sinocraft.sinocore.data.gen.delegate.LanguageProviderDelegateBase;
import games.moegirl.sinocraft.sinocore.data.gen.neoforge.impl.NeoForgeLanguageProviderDelegate;

public class AbstractLanguageProviderImpl {

    public static LanguageProviderDelegateBase createDelegate(DataGenContext context, String locale) {
        return new NeoForgeLanguageProviderDelegate(context, locale);
    }
}
