package games.moegirl.sinocraft.sinocore.data.gen;

import dev.architectury.injectables.annotations.ExpectPlatform;
import games.moegirl.sinocraft.sinocore.data.gen.delegate.ItemModelProviderDelegateBase;
import games.moegirl.sinocraft.sinocore.data.gen.delegate.ProviderDelegateBase;
import games.moegirl.sinocraft.sinocore.registry.IRegistry;
import net.minecraft.world.item.Item;

public abstract class AbstractItemModelProvider extends ForgeDataProviderBase {

    @SafeVarargs
    public AbstractItemModelProvider(IDataGenContext context, IRegistry<Item>... registries) {
        super(createDelegate(context, registries));
    }

    public abstract void generateModels(ItemModelProviderDelegateBase<?> delegate);

    @Override
    public final void generateData(ProviderDelegateBase delegate) {
        generateModels((ItemModelProviderDelegateBase<?>) delegate);
    }

    @SafeVarargs
    @ExpectPlatform
    public static ItemModelProviderDelegateBase<?> createDelegate(IDataGenContext context, IRegistry<Item>... registries) {
        throw new AssertionError();
    }
}
