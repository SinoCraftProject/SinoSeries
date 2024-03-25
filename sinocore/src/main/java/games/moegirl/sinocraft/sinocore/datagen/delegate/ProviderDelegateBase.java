package games.moegirl.sinocraft.sinocore.datagen.delegate;

import games.moegirl.sinocraft.sinocore.datagen.ForgeDataProviderBase;
import net.minecraft.data.DataProvider;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Objects;

/**
 * 持有 Forge 的 DataProvider 实现，并负责收集数据
 */
public abstract class ProviderDelegateBase<T extends ProviderDelegateBase<T>> {

    private ForgeDataProviderBase<T> provider;
    private DataProvider forgeProvider;
    private DataProviderBuilderBase<?, ?> forgeProviderBuilder;
    private final Logger logger;

    protected ProviderDelegateBase(DataProvider provider) {
        this.forgeProvider = provider;
        this.logger = LogManager.getLogger(provider.getName());
    }

    protected ProviderDelegateBase(DataProviderBuilderBase<?, ?> builder) {
        this.forgeProviderBuilder = builder.bind(this);
        this.logger = LogManager.getLogger(builder.getDataProviderName());
    }

    public void setDelegateProvider(ForgeDataProviderBase<T> provider) {
        this.provider = provider;
    }

    public <V extends DataProvider> V getForgeProvider() {
        if (forgeProvider == null) {
            Objects.requireNonNull(forgeProviderBuilder);
            forgeProvider = forgeProviderBuilder.build();
        }
        return (V) Objects.requireNonNull(forgeProvider);
    }

    public void generateData() {
        provider.generateData((T) this);
    }

    public synchronized Logger getLogger() {
        return logger;
    }

    public String getName() {
        return forgeProvider == null ? forgeProviderBuilder.getDataProviderName() : forgeProvider.getName();
    }
}
