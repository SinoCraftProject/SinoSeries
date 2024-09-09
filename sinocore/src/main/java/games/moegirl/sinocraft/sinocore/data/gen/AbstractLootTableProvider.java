package games.moegirl.sinocraft.sinocore.data.gen;

import dev.architectury.injectables.annotations.ExpectPlatform;
import games.moegirl.sinocraft.sinocore.data.gen.delegate.LootTableProviderDelegateBase;
import games.moegirl.sinocraft.sinocore.data.gen.loottable.IBlockLootTableSubProvider;
import games.moegirl.sinocraft.sinocore.data.gen.loottable.IEntityLootTableSubProvider;
import net.minecraft.core.HolderLookup;

public abstract class AbstractLootTableProvider extends NeoForgeDataProviderBase<LootTableProviderDelegateBase> {

    protected final String modId;

    public AbstractLootTableProvider(IDataGenContext context) {
        super(createDelegate(context));
        modId = context.getModId();
    }

    @Override
    public String getModId() {
        return modId;
    }

    @ExpectPlatform
    public static LootTableProviderDelegateBase createDelegate(IDataGenContext context) {
        throw new AssertionError();
    }

    @ExpectPlatform
    public static IBlockLootTableSubProvider createBlockSubProvider(HolderLookup.Provider registries) {
        throw new AssertionError();
    }

    @ExpectPlatform
    public static IEntityLootTableSubProvider createEntitySubProvider(HolderLookup.Provider registries) {
        throw new AssertionError();
    }
}
