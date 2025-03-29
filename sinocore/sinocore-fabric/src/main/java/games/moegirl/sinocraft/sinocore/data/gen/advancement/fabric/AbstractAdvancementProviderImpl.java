package games.moegirl.sinocraft.sinocore.data.gen.advancement.fabric;

import games.moegirl.sinocraft.sinocore.data.gen.DataGenContext;
import games.moegirl.sinocraft.sinocore.data.gen.delegate.AdvancementProviderDelegateBase;

public abstract class AbstractAdvancementProviderImpl {

    public static AdvancementProviderDelegateBase createDelegate(DataGenContext context) {
        throw new IllegalStateException("DataProvider only for forge platform.");
    }
}
