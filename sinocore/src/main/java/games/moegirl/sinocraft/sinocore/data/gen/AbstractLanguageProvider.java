package games.moegirl.sinocraft.sinocore.data.gen;

import dev.architectury.injectables.annotations.ExpectPlatform;
import games.moegirl.sinocraft.sinocore.data.gen.delegate.LanguageProviderDelegateBase;
import net.minecraft.resources.ResourceLocation;

public abstract class AbstractLanguageProvider extends ForgeDataProviderBase<LanguageProviderDelegateBase> {

    protected final String modId;

    public AbstractLanguageProvider(IDataGenContext context, String locale) {
        super(createDelegate(context, locale));
        this.modId = context.getModId();
    }

    @Override
    public String getModId() {
        return modId;
    }

    @ExpectPlatform
    public static LanguageProviderDelegateBase createDelegate(IDataGenContext context, String locale) {
        throw new AssertionError();
    }
}
