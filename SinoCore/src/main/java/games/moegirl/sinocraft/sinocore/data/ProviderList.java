package games.moegirl.sinocraft.sinocore.data;

import net.minecraft.data.CachedOutput;
import net.minecraft.data.DataProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.function.Supplier;

/**
 * 按顺序执行 Provider
 * @author luqin2007
 */
public class ProviderList implements DataProvider {

    private final List<Supplier<DataProvider>> providers = new ArrayList<>();
    private final String name;
    private final Logger logger;

    public ProviderList(String name) {
        this.name = name;
        this.logger = LoggerFactory.getLogger(name);
    }

    public ProviderList then(Supplier<DataProvider> provider) {
        providers.add(provider);
        return this;
    }

    public ProviderList then(DataProvider provider) {
        providers.add(() -> provider);
        return this;
    }

    @Override
    public CompletableFuture<?> run(CachedOutput output) {
        return CompletableFuture.runAsync(() -> {
            for (Supplier<DataProvider> provider : providers) {
                DataProvider p = provider.get();
                try {
                    logger.info("Run {}", p.getName());
                    p.run(output).get();
                } catch (InterruptedException | ExecutionException e) {
                    logger.error("Failed to run provider {}", p.getName(), e);
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    public String getName() {
        return name;
    }
}
