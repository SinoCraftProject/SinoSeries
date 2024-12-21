package games.moegirl.sinocraft.sinodeco.data.gen.model;

import games.moegirl.sinocraft.sinocore.data.gen.model.AbstractItemModelProvider;
import games.moegirl.sinocraft.sinocore.data.gen.IDataGenContext;
import games.moegirl.sinocraft.sinocore.data.gen.delegate.ItemModelProviderDelegateBase;
import games.moegirl.sinocraft.sinodeco.block.SDBlocks;
import games.moegirl.sinocraft.sinodeco.block.item.SDBlockItems;
import games.moegirl.sinocraft.sinodeco.item.SDItems;

public class ItemModelProvider extends AbstractItemModelProvider {
    public ItemModelProvider(IDataGenContext context) {
        super(context, SDBlockItems.ITEMS, SDItems.ITEMS);
    }

    @Override
    public void generateModels(ItemModelProviderDelegateBase<?> delegate) {
        delegate.blockItem(SDBlocks.MARBLE_BALUSTRADE.get(), "inventory");

        delegate.skipItem(SDBlockItems.MARBLE_BALUSTRADE.get());
    }
}
