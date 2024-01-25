package games.moegirl.sinocraft.sinocore.datagen.forge;

import games.moegirl.sinocraft.sinocore.datagen.IDataGenContext;
import games.moegirl.sinocraft.sinocore.datagen.delegate.AdvancementProviderDelegateBase;
import games.moegirl.sinocraft.sinocore.datagen.forge.impl.ForgeAdvancementProviderDelegateImpl;

public abstract class AbstractAdvancementProviderImpl {

    public static AdvancementProviderDelegateBase createDelegate(IDataGenContext context) {
        if (context instanceof ForgeDataGenContextImpl impl) {
            return new ForgeAdvancementProviderDelegateImpl(impl);
        }
        throw new ClassCastException("Can't cast " + context + " to ForgeDataGenContextImpl at Forge Platform. " +
                "Use SinoCorePlatform#buildDataGeneratorContext to create this context. " +
                "Don't use context implemented yourself, because it contains different information in different platform");
    }
}
