package games.moegirl.sinocraft.sinodeco.data.gen.model;

import games.moegirl.sinocraft.sinocore.data.gen.AbstractItemModelProvider;
import games.moegirl.sinocraft.sinocore.data.gen.IDataGenContext;
import games.moegirl.sinocraft.sinocore.data.gen.delegate.ItemModelProviderDelegateBase;
import games.moegirl.sinocraft.sinocore.registry.IRegistry;
import net.minecraft.world.item.Item;

public class SDItemModelProvider extends AbstractItemModelProvider {
    @SafeVarargs
    public SDItemModelProvider(IDataGenContext context, IRegistry<Item>... registries) {
        super(context, registries);
    }

    @Override
    public void generateModels(ItemModelProviderDelegateBase<?> delegate) {

    }
}
