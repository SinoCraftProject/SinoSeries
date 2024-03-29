package games.moegirl.sinocraft.sinocore.datagen;

import dev.architectury.injectables.annotations.ExpectPlatform;
import games.moegirl.sinocraft.sinocore.datagen.delegate.LootTableProviderDelegateBase;
import games.moegirl.sinocraft.sinocore.datagen.loottable.IBlockLootTableSubProvider;
import games.moegirl.sinocraft.sinocore.datagen.loottable.IEntityLootTableSubProvider;

public abstract class AbstractLootTableProvider extends ForgeDataProviderBase<LootTableProviderDelegateBase> {

    public AbstractLootTableProvider(IDataGenContext context) {
        super(createDelegate(context));
    }

    @ExpectPlatform
    public static LootTableProviderDelegateBase createDelegate(IDataGenContext context) {
        throw new AssertionError();
    }

    @ExpectPlatform
    public static IBlockLootTableSubProvider createBlockSubProvider() {
        throw new AssertionError();
    }

    @ExpectPlatform
    public static IEntityLootTableSubProvider createEntitySubProvider() {
        throw new AssertionError();
    }
}
