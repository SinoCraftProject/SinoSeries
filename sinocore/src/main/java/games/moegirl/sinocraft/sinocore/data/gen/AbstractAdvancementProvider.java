package games.moegirl.sinocraft.sinocore.data.gen;

import dev.architectury.injectables.annotations.ExpectPlatform;
import games.moegirl.sinocraft.sinocore.data.gen.delegate.AdvancementProviderDelegateBase;
import net.minecraft.resources.ResourceLocation;

public abstract class AbstractAdvancementProvider extends ForgeDataProviderBase<AdvancementProviderDelegateBase> {

    protected final String modId;

    public AbstractAdvancementProvider(IDataGenContext context) {
        super(createDelegate(context));
        this.modId = context.getModId();
    }

    @Override
    public String getModId() {
        return modId;
    }

    @ExpectPlatform
    public static AdvancementProviderDelegateBase createDelegate(IDataGenContext context) {
        throw new AssertionError();
    }
}
