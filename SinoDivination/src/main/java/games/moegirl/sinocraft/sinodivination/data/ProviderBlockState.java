package games.moegirl.sinocraft.sinodivination.data;

import games.moegirl.sinocraft.sinocore.data.model.BaseAutoBlockStateProvider;
import games.moegirl.sinocraft.sinodivination.block.SDBlocks;
import net.minecraftforge.data.event.GatherDataEvent;

import static games.moegirl.sinocraft.sinodivination.SinoDivination.MODID;

class ProviderBlockState extends BaseAutoBlockStateProvider {

    public ProviderBlockState(GatherDataEvent event) {
        super(event.getGenerator().getPackOutput(), MODID, event.getExistingFileHelper(), SDBlocks.REGISTRY);
    }

    @Override
    protected void registerBlockStatesAndModels() {
        skipBlock(SDBlocks.KETTLE_POT.get());
    }
}
