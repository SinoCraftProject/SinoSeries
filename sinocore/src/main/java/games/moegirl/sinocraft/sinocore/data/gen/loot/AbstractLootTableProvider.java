package games.moegirl.sinocraft.sinocore.data.gen.loot;

import dev.architectury.injectables.annotations.ExpectPlatform;
import games.moegirl.sinocraft.sinocore.data.gen.DataGenContext;
import games.moegirl.sinocraft.sinocore.data.gen.NeoForgeDataProviderBase;
import games.moegirl.sinocraft.sinocore.data.gen.delegate.LootTableProviderDelegateBase;
import net.minecraft.core.HolderLookup;
import org.jetbrains.annotations.NotNull;

public abstract class AbstractLootTableProvider extends NeoForgeDataProviderBase<LootTableProviderDelegateBase> {

    protected final String modId;

    public AbstractLootTableProvider(DataGenContext context) {
        super(createDelegate(context));
        modId = context.getModId();
    }

    @Override
    public @NotNull String getModId() {
        return modId;
    }

    @ExpectPlatform
    public static LootTableProviderDelegateBase createDelegate(DataGenContext context) {
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
