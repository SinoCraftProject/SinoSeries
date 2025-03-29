package games.moegirl.sinocraft.sinocore.data.gen.fabric;

import games.moegirl.sinocraft.sinocore.data.gen.DataGenContext;
import games.moegirl.sinocraft.sinocore.data.gen.delegate.DatapackProviderDelegateBase;

public abstract class AbstractDatapackBuiltinEntriesProviderImpl {

    public static DatapackProviderDelegateBase createDelegate(DataGenContext context) {
        throw new IllegalStateException("DataProvider only for forge platform.");
    }
}
