package games.moegirl.sinocraft.sinocore.datagen.fabric;

import games.moegirl.sinocraft.sinocore.datagen.IDataGenContext;
import games.moegirl.sinocraft.sinocore.datagen.delegate.AdvancementProviderDelegateBase;

public abstract class AbstractAdvancementProviderImpl {

    public static AdvancementProviderDelegateBase createDelegate(IDataGenContext context) {
        throw new IllegalStateException("DataProvider only for forge platform.");
    }
}
