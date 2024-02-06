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
        var inkedBrush = delegate.getBuilder("item/brush_inked")
//                .parent()
                .texture("layer0", new ResourceLocation(SinoBrush.MODID, "item/brush_inked"));
        delegate.basicItem(SBRItems.BRUSH.get())
                .texture("layer0", new ResourceLocation(SinoBrush.MODID, "item/brush"))
                .override().predicate(new ResourceLocation("damaged"), 1).model(inkedBrush);

        delegate.getBuilder("item/ink_bottle")
//                .parent()
                .texture("layer0", new ResourceLocation(SinoBrush.MODID, "item/ink_bottle_body"))
                .texture("layer1", new ResourceLocation(SinoBrush.MODID, "item/ink_bottle_cap"));

        delegate.skipItem(SBRItems.FAN.get(), SBRItems.FOLDED_FAN.get());
    }
}
