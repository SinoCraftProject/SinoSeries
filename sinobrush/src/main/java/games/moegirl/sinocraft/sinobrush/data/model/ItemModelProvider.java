package games.moegirl.sinocraft.sinobrush.data.model;

import games.moegirl.sinocraft.sinobrush.SinoBrush;
import games.moegirl.sinocraft.sinobrush.data.tag.SBRItemTags;
import games.moegirl.sinocraft.sinobrush.item.SBRItems;
import games.moegirl.sinocraft.sinocore.data.gen.AbstractItemModelProvider;
import games.moegirl.sinocraft.sinocore.data.gen.IDataGenContext;
import games.moegirl.sinocraft.sinocore.data.gen.delegate.ItemModelProviderDelegateBase;
import games.moegirl.sinocraft.sinocore.registry.IRegistry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;

public class ItemModelProvider extends AbstractItemModelProvider {
    @SafeVarargs
    public ItemModelProvider(IDataGenContext context, IRegistry<Item>... registries) {
        super(context, registries);
    }

    @Override
    public void generateModels(ItemModelProviderDelegateBase<?> delegate) {
        delegate.basicItem(SBRItems.BRUSH.get())
                .texture("layer0", modLoc("item/brush"))
                .override()
                .predicate(mcLoc("damaged"), 1)
                .model(delegate.getBuilder("item/inked_brush")
                        .texture("layer0", modLoc("item/inked_brush")));

        delegate.basicItem(SBRItems.INK_BOTTLE.get())
                .texture("layer0", modLoc("item/ink_bottle_mask"))
                .texture("layer1", modLoc("item/ink_bottle"));

        delegate.skipItem(SBRItems.FAN.get(), SBRItems.FOLDED_FAN.get(),
                SBRItems.INK_BOTTLE.get(), SBRItems.FILLED_XUAN_PAPER.get());
    }
}
