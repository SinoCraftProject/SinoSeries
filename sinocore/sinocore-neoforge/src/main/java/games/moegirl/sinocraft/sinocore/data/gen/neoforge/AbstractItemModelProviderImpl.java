package games.moegirl.sinocraft.sinocore.data.gen.neoforge;

import games.moegirl.sinocraft.sinocore.data.gen.IDataGenContext;
import games.moegirl.sinocraft.sinocore.data.gen.delegate.ItemModelProviderDelegateBase;
import games.moegirl.sinocraft.sinocore.data.gen.neoforge.impl.NeoForgeItemModelProviderDelegate;
import games.moegirl.sinocraft.sinocore.registry.IRegistry;
import net.minecraft.world.item.Item;

public class AbstractItemModelProviderImpl {

    @SafeVarargs
    public static ItemModelProviderDelegateBase<?> createDelegate(IDataGenContext context, IRegistry<Item>... registries) {
        if (context instanceof NeoForgeDataGenContextImpl impl) {
            return new NeoForgeItemModelProviderDelegate(impl, registries);
        }
        throw new ClassCastException("Can't cast " + context + " to ForgeDataGenContextImpl at Forge Platform. " +
                "Use SinoCorePlatform#buildDataGeneratorContext to create this context. " +
                "Don't use context implemented yourself, because it contains different information in different platform");
    }
}
