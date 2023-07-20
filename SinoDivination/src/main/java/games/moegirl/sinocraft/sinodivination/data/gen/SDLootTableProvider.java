package games.moegirl.sinocraft.sinodivination.data.gen;

import games.moegirl.sinocraft.sinocore.data.gen.AbstructLootTableProvider;
import games.moegirl.sinocraft.sinodivination.SinoDivination;
import games.moegirl.sinocraft.sinodivination.block.SDBlocks;
import net.minecraft.data.DataGenerator;

import java.util.List;

class SDLootTableProvider extends AbstructLootTableProvider {

    public SDLootTableProvider(DataGenerator pGenerator) {
        super(pGenerator.getPackOutput(), SinoDivination.MODID);
    }

    @Override
    public void getTables(List<SubProviderEntry> tables) {
        addBlocks(SDBlocks.REGISTRY);
    }
}
