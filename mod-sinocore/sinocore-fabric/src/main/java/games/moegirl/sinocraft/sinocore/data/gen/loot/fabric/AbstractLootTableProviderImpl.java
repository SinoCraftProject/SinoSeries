package games.moegirl.sinocraft.sinocore.data.gen.loot.fabric;

import games.moegirl.sinocraft.sinocore.data.gen.DataGenContext;
import games.moegirl.sinocraft.sinocore.data.gen.delegate.LootTableProviderDelegateBase;
import games.moegirl.sinocraft.sinocore.data.gen.loot.IBlockLootTableSubProvider;
import games.moegirl.sinocraft.sinocore.data.gen.loot.IEntityLootTableSubProvider;
import net.minecraft.core.HolderLookup;

public class AbstractLootTableProviderImpl {

    public static LootTableProviderDelegateBase createDelegate(DataGenContext context) {
        throw new IllegalStateException("DataProvider only for forge platform.");
    }

    public static IBlockLootTableSubProvider createBlockSubProvider(HolderLookup.Provider registries) {
        throw new IllegalStateException("DataProvider only for forge platform.");
    }

    public static IEntityLootTableSubProvider createEntitySubProvider(HolderLookup.Provider registries) {
        throw new IllegalStateException("DataProvider only for forge platform.");
    }
}
