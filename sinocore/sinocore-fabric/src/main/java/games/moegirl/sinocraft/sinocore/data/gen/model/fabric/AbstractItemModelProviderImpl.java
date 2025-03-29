package games.moegirl.sinocraft.sinocore.data.gen.model.fabric;

import games.moegirl.sinocraft.sinocore.data.gen.DataGenContext;
import games.moegirl.sinocraft.sinocore.data.gen.delegate.ItemModelProviderDelegateBase;
import games.moegirl.sinocraft.sinocore.registry.IRegistry;
import net.minecraft.world.item.Item;

public class AbstractItemModelProviderImpl {

    @SafeVarargs
    public static ItemModelProviderDelegateBase<?> createDelegate(DataGenContext context, IRegistry<Item>... registries) {
        throw new IllegalStateException("DataProvider only for forge platform.");
    }
}
