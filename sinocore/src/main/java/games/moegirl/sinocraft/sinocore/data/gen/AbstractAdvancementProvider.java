package games.moegirl.sinocraft.sinocore.data.gen;

import dev.architectury.injectables.annotations.ExpectPlatform;
import games.moegirl.sinocraft.sinocore.data.gen.delegate.AdvancementProviderDelegateBase;

public abstract class AbstractAdvancementProvider extends ForgeDataProviderBase<AdvancementProviderDelegateBase> {

    public AbstractAdvancementProvider(IDataGenContext context) {
        super(createDelegate(context));
    }

    @ExpectPlatform
    public static AdvancementProviderDelegateBase createDelegate(IDataGenContext context) {
        throw new AssertionError();
    }
}
