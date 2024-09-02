package games.moegirl.sinocraft.sinocore.data.gen.neoforge;

import games.moegirl.sinocraft.sinocore.data.gen.IDataGenContext;
import games.moegirl.sinocraft.sinocore.data.gen.delegate.AdvancementProviderDelegateBase;
import games.moegirl.sinocraft.sinocore.data.gen.neoforge.impl.NeoForgeAdvancementProviderDelegate;

public abstract class AbstractAdvancementProviderImpl {

    public static AdvancementProviderDelegateBase createDelegate(IDataGenContext context) {
        if (context instanceof NeoForgeDataGenContextImpl impl) {
            return new NeoForgeAdvancementProviderDelegate(impl);
        }
        throw new ClassCastException("Can't cast " + context + " to ForgeDataGenContextImpl at Forge Platform. " +
                "Use SinoCorePlatform#buildDataGeneratorContext to create this context. " +
                "Don't use context implemented yourself, because it contains different information in different platform");
    }
}
