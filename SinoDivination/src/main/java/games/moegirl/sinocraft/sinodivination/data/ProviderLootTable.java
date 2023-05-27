package games.moegirl.sinocraft.sinodivination.data;

import games.moegirl.sinocraft.sinocore.data.LootTableProviderBase;
import games.moegirl.sinocraft.sinodivination.SinoDivination;
import games.moegirl.sinocraft.sinodivination.block.SDBlocks;
import net.minecraft.data.DataGenerator;

class ProviderLootTable extends LootTableProviderBase {

    public ProviderLootTable(DataGenerator pGenerator) {
        super(pGenerator.getPackOutput(), SinoDivination.MODID, SDBlocks.REGISTRY);
    }

    @Override
    public void addLootTables() {
    }
}
