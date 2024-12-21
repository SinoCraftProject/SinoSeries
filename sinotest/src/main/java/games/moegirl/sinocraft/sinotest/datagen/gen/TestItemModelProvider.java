package games.moegirl.sinocraft.sinotest.datagen.gen;

import games.moegirl.sinocraft.sinocore.data.gen.model.AbstractItemModelProvider;
import games.moegirl.sinocraft.sinocore.data.gen.IDataGenContext;
import games.moegirl.sinocraft.sinocore.data.gen.delegate.ItemModelProviderDelegateBase;
import games.moegirl.sinocraft.sinotest.registry.TestRegistry;

public class TestItemModelProvider extends AbstractItemModelProvider {

    public TestItemModelProvider(IDataGenContext context) {
        super(context, TestRegistry.ITEMS);
    }

    @Override
    public void generateModels(ItemModelProviderDelegateBase<?> delegate) {
    }
}
