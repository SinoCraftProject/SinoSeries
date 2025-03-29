package games.moegirl.sinocraft.sinotest.datagen.gen;

import games.moegirl.sinocraft.sinocore.data.gen.model.AbstractItemModelProvider;
import games.moegirl.sinocraft.sinocore.data.gen.DataGenContext;
import games.moegirl.sinocraft.sinocore.data.gen.delegate.ItemModelProviderDelegateBase;
import games.moegirl.sinocraft.sinotest.registry.TestRegistry;

public class TestItemModelProvider extends AbstractItemModelProvider {

    public TestItemModelProvider(DataGenContext context) {
        super(context, TestRegistry.ITEMS);
    }

    @Override
    public void generateModels(ItemModelProviderDelegateBase<?> delegate) {
    }
}
