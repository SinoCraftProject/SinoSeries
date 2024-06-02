package games.moegirl.sinocraft.sinocore.data.gen;

import dev.architectury.injectables.annotations.ExpectPlatform;
import games.moegirl.sinocraft.sinocore.data.gen.delegate.LanguageProviderDelegateBase;

public abstract class AbstractLanguageProvider extends ForgeDataProviderBase<LanguageProviderDelegateBase> {

    public AbstractLanguageProvider(IDataGenContext context, String locale) {
        super(createDelegate(context, locale));
    }

    @ExpectPlatform
    public static LanguageProviderDelegateBase createDelegate(IDataGenContext context, String locale) {
        throw new AssertionError();
    }
}
