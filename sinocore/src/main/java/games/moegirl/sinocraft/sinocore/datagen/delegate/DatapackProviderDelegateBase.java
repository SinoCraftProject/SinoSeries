package games.moegirl.sinocraft.sinocore.datagen.delegate;

import net.minecraft.core.Registry;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.resources.ResourceKey;

import java.util.function.Consumer;

public abstract class DatapackProviderDelegateBase extends ProviderDelegateBase<DatapackProviderDelegateBase> {

    protected DatapackProviderDelegateBase(DataProviderBuilderBase<?, ?> builder) {
        super(builder);
    }

    public abstract <T> void add(ResourceKey<? extends Registry<T>> type, Consumer<BootstapContext<T>> register);
}
