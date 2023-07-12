package games.moegirl.sinocraft.sinodivination.data.gen;

import games.moegirl.sinocraft.sinocore.data.gen.AbstractAutoItemModelProvider;
import games.moegirl.sinocraft.sinodivination.SDTrees;
import games.moegirl.sinocraft.sinodivination.SinoDivination;
import games.moegirl.sinocraft.sinodivination.item.SDItems;
import net.minecraftforge.data.event.GatherDataEvent;

class SDItemModelProvider extends AbstractAutoItemModelProvider {

    public SDItemModelProvider(GatherDataEvent event) {
        super(event.getGenerator().getPackOutput(), SinoDivination.MODID, event.getExistingFileHelper(), SDItems.REGISTRY.getRegister());
    }

    @Override
    protected void registerItemModels() {
//        basicItem(SDItems.RICE.get());
//        basicItem(SDItems.REHMANNIA.get());
//        basicItem(SDItems.SESAME.get());
//        basicItem(SDItems.DRAGONLIVER_MELON.get());
//        basicItem(SDItems.WORMWOOD_LEAF.get());
//        basicItem(SDItems.GARLIC.get());
        chest(SDItems.COTINUS_CHEST, SDItems.COTINUS_TRAPPED_CHEST, SDTrees.COTINUS);
        chest(SDItems.SOPHORA_CHEST, SDItems.SOPHORA_TRAPPED_CHEST, SDTrees.SOPHORA);
    }
}
