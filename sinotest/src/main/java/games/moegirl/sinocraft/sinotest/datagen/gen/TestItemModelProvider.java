package games.moegirl.sinocraft.sinotest.datagen.gen;

import games.moegirl.sinocraft.sinocore.datagen.AbstractItemModelProvider;
import games.moegirl.sinocraft.sinocore.datagen.IDataGenContext;
import games.moegirl.sinocraft.sinocore.datagen.delegate.ItemModelProviderDelegateBase;
import games.moegirl.sinocraft.sinotest.registry.TestRegistry;

public class TestItemModelProvider extends AbstractItemModelProvider {

    public TestItemModelProvider(IDataGenContext context) {
        super(context, TestRegistry.ITEMS);
    }

    @Override
    public void generateModels(ItemModelProviderDelegateBase<?> delegate) {
    }
}
