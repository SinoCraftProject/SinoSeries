package games.moegirl.sinocraft.sinocore.data.gen.advancement.neoforge;

import games.moegirl.sinocraft.sinocore.data.gen.DataGenContext;
import games.moegirl.sinocraft.sinocore.data.gen.delegate.AdvancementProviderDelegateBase;
import games.moegirl.sinocraft.sinocore.data.gen.neoforge.NeoForgeDataGenContext;
import games.moegirl.sinocraft.sinocore.data.gen.neoforge.impl.NeoForgeAdvancementProviderDelegate;

public abstract class AbstractAdvancementProviderImpl {

    public static AdvancementProviderDelegateBase createDelegate(DataGenContext context) {
        if (context instanceof NeoForgeDataGenContext impl) {
            return new NeoForgeAdvancementProviderDelegate(impl);
        }
        throw new ClassCastException("Can't cast " + context + " to ForgeDataGenContextImpl at Forge Platform. " +
                "Use SinoCorePlatform#buildDataGeneratorContext to create this context. " +
                "Don't use context implemented yourself, because it contains different information in different platform");
    }
}
