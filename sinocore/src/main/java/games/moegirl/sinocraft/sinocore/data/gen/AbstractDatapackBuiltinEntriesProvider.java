package games.moegirl.sinocraft.sinocore.data.gen;

import dev.architectury.injectables.annotations.ExpectPlatform;
import games.moegirl.sinocraft.sinocore.data.gen.delegate.DatapackProviderDelegateBase;
import net.minecraft.resources.ResourceLocation;

public abstract class AbstractDatapackBuiltinEntriesProvider extends ForgeDataProviderBase<DatapackProviderDelegateBase> {

    protected final String modId;

    public AbstractDatapackBuiltinEntriesProvider(IDataGenContext context) {
        super(createDelegate(context));
        this.modId = context.getModId();
    }

    @Override
    public String getModId() {
        return modId;
    }

    @ExpectPlatform
    public static DatapackProviderDelegateBase createDelegate(IDataGenContext context) {
        throw new AssertionError();
    }
}
