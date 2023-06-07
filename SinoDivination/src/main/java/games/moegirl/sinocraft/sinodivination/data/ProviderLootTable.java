package games.moegirl.sinocraft.sinodivination.data;

import games.moegirl.sinocraft.sinocore.data.LootTableProviderBase;
import games.moegirl.sinocraft.sinodivination.SinoDivination;
import games.moegirl.sinocraft.sinodivination.block.SDBlocks;
import net.minecraft.data.DataGenerator;

import java.util.List;

class ProviderLootTable extends LootTableProviderBase {

    public ProviderLootTable(DataGenerator pGenerator) {
        super(pGenerator.getPackOutput(), SinoDivination.MODID);
    }

    @Override
    public void getTables(List<SubProviderEntry> tables) {
        addBlocks(SDBlocks.REGISTRY);
    }
}
