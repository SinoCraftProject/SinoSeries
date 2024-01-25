package games.moegirl.sinocraft.sinocore.datagen;

import dev.architectury.injectables.annotations.ExpectPlatform;
import games.moegirl.sinocraft.sinocore.datagen.delegate.LanguageProviderDelegateBase;
import net.minecraft.data.PackOutput;

public abstract class AbstractLanguageProvider extends ForgeDataProviderBase<LanguageProviderDelegateBase> {

    public AbstractLanguageProvider(IDataGenContext context, String locale) {
        super(createDelegate(context, locale));
    }

    @ExpectPlatform
    public static LanguageProviderDelegateBase createDelegate(IDataGenContext context, String locale) {
        throw new AssertionError();
    }
}
