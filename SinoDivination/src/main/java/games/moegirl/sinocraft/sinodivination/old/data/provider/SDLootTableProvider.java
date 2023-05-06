package games.moegirl.sinocraft.sinodivination.old.data.provider;

import games.moegirl.sinocraft.sinocore.data.LootTableProviderBase;
import games.moegirl.sinocraft.sinodivination.SinoDivination;
import games.moegirl.sinocraft.sinodivination.old.block.SDBlocks;
import net.minecraft.data.DataGenerator;

public class SDLootTableProvider extends LootTableProviderBase {

    public SDLootTableProvider(DataGenerator pGenerator) {
        super(pGenerator.getPackOutput(), SinoDivination.MODID, SDBlocks.REGISTRY);
    }

    @Override
    public void addLootTables() {
    }
}
