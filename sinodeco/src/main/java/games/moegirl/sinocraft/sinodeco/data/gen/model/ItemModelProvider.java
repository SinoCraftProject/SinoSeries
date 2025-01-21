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
        delegate.blockItem(SDBlocks.PEACH_WOOD_TABLE.get());
        delegate.blockItem(SDBlocks.MARBLE_BALUSTRADE.get(), "inventory");

        delegate.basicItem(SDBlockItems.PEACH_DOOR.get());
        delegate.blockItem(SDBlocks.PEACH_TRAPDOOR.get(), "bottom");
        delegate.basicItem(SDBlockItems.PEACH_SIGN.get());
        delegate.basicItem(SDBlockItems.PEACH_HANGING_SIGN.get());
        delegate.skipItem(SDBlockItems.PEACH_FENCE.get());
        delegate.withExistingParent("peach_fence", modLoc("block/peach_fence_inventory"));
        delegate.skipItem(SDBlockItems.PEACH_BUTTON.get());
        delegate.withExistingParent("peach_button", modLoc("block/peach_button_inventory"));
    }
}
