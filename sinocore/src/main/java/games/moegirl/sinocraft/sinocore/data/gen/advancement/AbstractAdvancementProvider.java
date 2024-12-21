package games.moegirl.sinocraft.sinocore.data.gen.advancement;

import dev.architectury.injectables.annotations.ExpectPlatform;
import games.moegirl.sinocraft.sinocore.data.gen.IDataGenContext;
import games.moegirl.sinocraft.sinocore.data.gen.NeoForgeDataProviderBase;
import games.moegirl.sinocraft.sinocore.data.gen.delegate.AdvancementProviderDelegateBase;
import org.jetbrains.annotations.NotNull;

public abstract class AbstractAdvancementProvider extends NeoForgeDataProviderBase<AdvancementProviderDelegateBase> {

    protected final String modId;

    public AbstractAdvancementProvider(IDataGenContext context) {
        super(createDelegate(context));
        this.modId = context.getModId();
    }

    @Override
    public @NotNull String getModId() {
        return modId;
    }

    @ExpectPlatform
    public static AdvancementProviderDelegateBase createDelegate(IDataGenContext context) {
        throw new AssertionError();
    }
}
