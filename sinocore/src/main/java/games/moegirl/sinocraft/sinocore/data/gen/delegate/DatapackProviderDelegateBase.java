package games.moegirl.sinocraft.sinocore.data.gen.delegate;

import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;

import java.util.function.Consumer;

public abstract class DatapackProviderDelegateBase extends ProviderDelegateBase<DatapackProviderDelegateBase> {

    protected DatapackProviderDelegateBase(DataProviderBuilderBase<?, ?> builder) {
        super(builder);
    }

    // Fixme: qyl27: migrate to 1.21
//    public abstract <T> void add(ResourceKey<? extends Registry<T>> type, Consumer<BootstapContext<T>> register);
}
