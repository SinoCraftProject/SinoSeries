package games.moegirl.sinocraft.sinotest.forge.datagen;

import games.moegirl.sinocraft.sinocore.datagen.AbstractItemModelProvider;
import games.moegirl.sinocraft.sinocore.datagen.IDataGenContext;
import games.moegirl.sinocraft.sinocore.datagen.delegate.ItemModelProviderDelegateBase;
import games.moegirl.sinocraft.sinocore.registry.IRegistry;
import net.minecraft.world.item.Item;

public class TestItemModelProvider extends AbstractItemModelProvider {

    @SafeVarargs
    public TestItemModelProvider(IDataGenContext context, IRegistry<Item>... registries) {
        super(context, registries);
    }

    @Override
    public void generateModels(ItemModelProviderDelegateBase<?> delegate) {
    }
}
