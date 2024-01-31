package games.moegirl.sinocraft.sinocore.datagen;

import dev.architectury.injectables.annotations.ExpectPlatform;
import games.moegirl.sinocraft.sinocore.datagen.delegate.DatapackProviderDelegateBase;

public abstract class AbstractDatapackBuiltinEntriesProvider extends ForgeDataProviderBase<DatapackProviderDelegateBase> {

    public AbstractDatapackBuiltinEntriesProvider(IDataGenContext context) {
        super(createDelegate(context));
    }

    @ExpectPlatform
    public static DatapackProviderDelegateBase createDelegate(IDataGenContext context) {
        throw new AssertionError();
    }
}
