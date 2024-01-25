package games.moegirl.sinocraft.sinocore.datagen.forge;

import games.moegirl.sinocraft.sinocore.datagen.IDataGenContext;
import games.moegirl.sinocraft.sinocore.datagen.delegate.ItemModelProviderDelegateBase;
import games.moegirl.sinocraft.sinocore.datagen.forge.impl.ForgeAdvancementProviderDelegateImpl;
import games.moegirl.sinocraft.sinocore.datagen.forge.impl.ForgeItemModelProviderDelegateImpl;
import games.moegirl.sinocraft.sinocore.registry.IRegistry;
import net.minecraft.world.item.Item;

public class AbstractItemModelProviderImpl {

    @SafeVarargs
    public static ItemModelProviderDelegateBase<?> createDelegate(IDataGenContext context, IRegistry<Item>... registries) {
        if (context instanceof ForgeDataGenContextImpl impl) {
            return new ForgeItemModelProviderDelegateImpl(impl, registries);
        }
        throw new ClassCastException("Can't cast " + context + " to ForgeDataGenContextImpl at Forge Platform. " +
                "Use SinoCorePlatform#buildDataGeneratorContext to create this context. " +
                "Don't use context implemented yourself, because it contains different information in different platform");
    }
}
