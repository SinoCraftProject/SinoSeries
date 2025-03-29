package games.moegirl.sinocraft.sinocore.data.gen.model;

import dev.architectury.injectables.annotations.ExpectPlatform;
import games.moegirl.sinocraft.sinocore.data.gen.DataGenContext;
import games.moegirl.sinocraft.sinocore.data.gen.NeoForgeDataProviderBase;
import games.moegirl.sinocraft.sinocore.data.gen.delegate.ItemModelProviderDelegateBase;
import games.moegirl.sinocraft.sinocore.data.gen.delegate.ProviderDelegateBase;
import games.moegirl.sinocraft.sinocore.registry.IRegistry;
import net.minecraft.world.item.Item;

public abstract class AbstractItemModelProvider extends NeoForgeDataProviderBase {

    protected final String modId;

    @SafeVarargs
    public AbstractItemModelProvider(DataGenContext context, IRegistry<Item>... registries) {
        super(createDelegate(context, registries));
        this.modId = context.getModId();
    }

    public abstract void generateModels(ItemModelProviderDelegateBase<?> delegate);

    @Override
    public final void generateData(ProviderDelegateBase delegate) {
        generateModels((ItemModelProviderDelegateBase<?>) delegate);
    }

    @Override
    public String getModId() {
        return modId;
    }

    @SafeVarargs
    @ExpectPlatform
    public static ItemModelProviderDelegateBase<?> createDelegate(DataGenContext context, IRegistry<Item>... registries) {
        throw new AssertionError();
    }
}
