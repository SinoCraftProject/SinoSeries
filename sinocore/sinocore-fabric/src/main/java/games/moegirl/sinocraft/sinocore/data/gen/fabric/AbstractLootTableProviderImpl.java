package games.moegirl.sinocraft.sinocore.data.gen.fabric;

import games.moegirl.sinocraft.sinocore.data.gen.IDataGenContext;
import games.moegirl.sinocraft.sinocore.data.gen.delegate.LootTableProviderDelegateBase;
import games.moegirl.sinocraft.sinocore.data.gen.loottable.IBlockLootTableSubProvider;
import games.moegirl.sinocraft.sinocore.data.gen.loottable.IEntityLootTableSubProvider;
import net.minecraft.core.HolderLookup;

public class AbstractLootTableProviderImpl {

    public static LootTableProviderDelegateBase createDelegate(IDataGenContext context) {
        throw new IllegalStateException("DataProvider only for forge platform.");
    }

    public static IBlockLootTableSubProvider createBlockSubProvider(HolderLookup.Provider registries) {
        throw new IllegalStateException("DataProvider only for forge platform.");
    }

    public static IEntityLootTableSubProvider createEntitySubProvider(HolderLookup.Provider registries) {
        throw new IllegalStateException("DataProvider only for forge platform.");
    }
}
