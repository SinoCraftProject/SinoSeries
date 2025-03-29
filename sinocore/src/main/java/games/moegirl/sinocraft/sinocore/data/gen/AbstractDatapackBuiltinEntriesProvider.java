package games.moegirl.sinocraft.sinocore.data.gen;

import dev.architectury.injectables.annotations.ExpectPlatform;
import games.moegirl.sinocraft.sinocore.data.gen.delegate.DatapackProviderDelegateBase;

public abstract class AbstractDatapackBuiltinEntriesProvider extends NeoForgeDataProviderBase<DatapackProviderDelegateBase> {

    protected final String modId;

    public AbstractDatapackBuiltinEntriesProvider(DataGenContext context) {
        super(createDelegate(context));
        this.modId = context.getModId();
    }

    @Override
    public String getModId() {
        return modId;
    }

    @ExpectPlatform
    public static DatapackProviderDelegateBase createDelegate(DataGenContext context) {
        throw new AssertionError();
    }
}
