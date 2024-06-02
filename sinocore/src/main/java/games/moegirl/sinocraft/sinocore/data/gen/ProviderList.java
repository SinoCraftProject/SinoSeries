package games.moegirl.sinocraft.sinocore.data.gen;

import com.mojang.logging.LogUtils;
import net.minecraft.data.CachedOutput;
import net.minecraft.data.DataProvider;
import org.slf4j.Logger;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

/**
 * 按顺序执行的 Provider
 */
public final class ProviderList implements DataProvider {

    private static final Logger LOGGER = LogUtils.getLogger();

    private final List<DataProvider> providers = new ArrayList<>();

    public ProviderList() {
    }

    public ProviderList(DataProvider first) {
        providers.add(first);
    }

    public ProviderList then(DataProvider provider) {
        providers.add(provider);
        return this;
    }

    @Override
    public CompletableFuture<?> run(CachedOutput output) {
        return CompletableFuture.runAsync(() -> {
            for (DataProvider p : providers) {
                try {
                    LOGGER.info("  Run {}", p.getName());
                    p.run(output).get();
                } catch (InterruptedException | ExecutionException e) {
                    LOGGER.error("  Failed to run provider {}", p.getName(), e);
                }
            }
        });
    }

    @Override
    public String getName() {
        return providers.stream()
                .map(DataProvider::getName)
                .collect(Collectors.joining("->", "Providers[", "]"));
    }
}
