package games.moegirl.sinocraft.sinocore.datagen;

import games.moegirl.sinocraft.sinocore.utility.Functions;
import games.moegirl.sinocraft.sinocore.utility.ModList;
import net.minecraft.data.CachedOutput;
import net.minecraft.data.DataProvider;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

/**
 * 直接使用 Forge 的 DataProvider
 * <p></p>
 * 要求 Forge 部分需要存在实现 IForgeProviders 的类并存在无参构造
 */
public class ForgeProvider implements DataProvider {

    private final List<DataProvider> providers;
    private final String name;

    public ForgeProvider(IDataGenContext context) {
        this.providers = new ArrayList<>();
        initDataProviders(context);
        this.name = "Forge Provider[" + context.getModId() + "]: " + providers.stream()
                .map(DataProvider::getName)
                .collect(Collectors.joining(", ", "[", "]"));
    }

    private void initDataProviders(IDataGenContext context) {
        try {
            ModList.findMod(context.getModId()).stream()
                    .flatMap(ModList.IModContainer::walkClasses)
                    .filter(IForgeProviders.class::isAssignableFrom)
                    .filter(c -> !c.isInterface() && !Modifier.isAbstract(c.getModifiers()))
                    .map(c -> (IForgeProviders) Functions.constructor(c).get())
                    .filter(p -> context.getModId().equals(p.getModId()))
                    .flatMap(p -> p.allProviders(context).stream())
                    .forEach(providers::add);
        } catch (Exception e) {
            Logger logger = LogManager.getLogger(context.getModId());
            logger.error("Can't get files from mod {}", context.getModId());
            logger.error(e);
        }
    }

    @Override
    public CompletableFuture<?> run(CachedOutput output) {
        return CompletableFuture.allOf(providers.stream()
                .map(p -> p.run(output))
                .toArray(CompletableFuture<?>[]::new));
    }

    @Override
    public String getName() {
        return name;
    }

    public interface IForgeProviders {

        String getModId();

        List<DataProvider> allProviders(IDataGenContext context);
    }
}
