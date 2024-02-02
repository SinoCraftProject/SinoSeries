package games.moegirl.sinocraft.sinocore.datagen.neoforge;

import games.moegirl.sinocraft.sinocore.datagen.IDataGenContext;
import games.moegirl.sinocraft.sinocore.datagen.delegate.LootTableProviderDelegateBase;
import games.moegirl.sinocraft.sinocore.datagen.loottable.IBlockLootTableSubProvider;
import games.moegirl.sinocraft.sinocore.datagen.loottable.IEntityLootTableSubProvider;

public class AbstractLootTableProviderImpl {

    public static LootTableProviderDelegateBase createDelegate(IDataGenContext context) {
        throw new IllegalStateException("DataProvider only for forge platform.");
    }

    public static IBlockLootTableSubProvider createBlockSubProvider() {
        throw new IllegalStateException("DataProvider only for forge platform.");
    }

    public static IEntityLootTableSubProvider createEntitySubProvider() {
        throw new IllegalStateException("DataProvider only for forge platform.");
    }
}
