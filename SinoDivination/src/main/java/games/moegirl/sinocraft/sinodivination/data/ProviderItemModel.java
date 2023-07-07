package games.moegirl.sinocraft.sinodivination.data;

import games.moegirl.sinocraft.sinocore.data.model.BaseAutoItemModelProvider;
import games.moegirl.sinocraft.sinodivination.SinoDivination;
import games.moegirl.sinocraft.sinodivination.item.SDItems;
import net.minecraftforge.data.event.GatherDataEvent;

class ProviderItemModel extends BaseAutoItemModelProvider {

    public ProviderItemModel(GatherDataEvent event) {
        super(event.getGenerator().getPackOutput(), SinoDivination.MODID, event.getExistingFileHelper(), SDItems.REGISTRY);
    }

    @Override
    protected void registerItemModels() {
//        basicItem(SDItems.RICE.get());
//        basicItem(SDItems.REHMANNIA.get());
//        basicItem(SDItems.SESAME.get());
//        basicItem(SDItems.DRAGONLIVER_MELON.get());
//        basicItem(SDItems.WORMWOOD_LEAF.get());
//        basicItem(SDItems.GARLIC.get());
    }
}
