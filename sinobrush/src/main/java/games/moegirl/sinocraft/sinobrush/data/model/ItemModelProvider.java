package games.moegirl.sinocraft.sinobrush.data.model;

import games.moegirl.sinocraft.sinobrush.SinoBrush;
import games.moegirl.sinocraft.sinobrush.item.SBRItems;
import games.moegirl.sinocraft.sinocore.datagen.AbstractItemModelProvider;
import games.moegirl.sinocraft.sinocore.datagen.IDataGenContext;
import games.moegirl.sinocraft.sinocore.datagen.delegate.ItemModelProviderDelegateBase;
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
        // Todo: qyl27: modLoc and mcLoc.
        delegate.basicItem(SBRItems.BRUSH.get())
                .texture("layer0", new ResourceLocation(SinoBrush.MODID, "item/brush"))
                .override().predicate(new ResourceLocation("damaged"), 1).model(delegate
                        .getBuilder("item/brush_inked")
                        .texture("layer0", new ResourceLocation(SinoBrush.MODID, "item/brush_inked")));

        delegate.basicItem(SBRItems.INK_BOTTLE.get())
                .texture("layer0", new ResourceLocation(SinoBrush.MODID, "item/ink_bottle_body"))
                .texture("layer1", new ResourceLocation(SinoBrush.MODID, "item/ink_bottle_cap"));

        delegate.basicItem(SBRItems.FILLED_XUAN_PAPER.get())
                .texture("layer0", new ResourceLocation(SinoBrush.MODID, "item/xuan_paper"));

        delegate.skipItem(SBRItems.FAN.get(), SBRItems.FOLDED_FAN.get());
    }
}
