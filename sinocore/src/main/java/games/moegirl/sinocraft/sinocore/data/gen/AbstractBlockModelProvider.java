package games.moegirl.sinocraft.sinocore.data.gen;

import dev.architectury.injectables.annotations.ExpectPlatform;
import games.moegirl.sinocraft.sinocore.data.gen.delegate.BlockModelProviderDelegateBase;
import games.moegirl.sinocraft.sinocore.data.gen.delegate.ProviderDelegateBase;

public abstract class AbstractBlockModelProvider extends ForgeDataProviderBase {
    protected final String modId;

    public AbstractBlockModelProvider(IDataGenContext context) {
        super(createDelegate(context));
        this.modId = context.getModId();
    }

    protected abstract void generateModels(BlockModelProviderDelegateBase delegate);

    @Override
    public void generateData(ProviderDelegateBase delegate) {
        generateModels((BlockModelProviderDelegateBase) delegate);
    }

    @Override
    public String getModId() {
        return modId;
    }

    @ExpectPlatform
    public static BlockModelProviderDelegateBase createDelegate(IDataGenContext context) {
        throw new AssertionError();
    }
}
