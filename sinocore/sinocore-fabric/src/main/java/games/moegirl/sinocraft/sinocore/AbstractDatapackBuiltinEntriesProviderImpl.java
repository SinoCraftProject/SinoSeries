package games.moegirl.sinocraft.sinocore;

import games.moegirl.sinocraft.sinocore.data.gen.IDataGenContext;
import games.moegirl.sinocraft.sinocore.data.gen.delegate.DatapackProviderDelegateBase;

public abstract class AbstractDatapackBuiltinEntriesProviderImpl {

    public static DatapackProviderDelegateBase createDelegate(IDataGenContext context) {
        throw new IllegalStateException("DataProvider only for forge platform.");
    }
}
