package games.moegirl.sinocraft.sinocore.registry.fabric;

import games.moegirl.sinocraft.sinocore.registry.*;
import games.moegirl.sinocraft.sinocore.data.gen.IDataGenContext;
import net.minecraft.core.Registry;
import net.minecraft.data.DataProvider;
import net.minecraft.resources.ResourceKey;

import java.util.function.Function;
import java.util.function.Supplier;

public class RegistryManagerImpl {

    public static <T> IRegistry<T> _create(String modId, ResourceKey<Registry<T>> key) {
        return new FabricRegistry<>(modId, key);
    }

    public static ITabRegistry _createTab(String modId) {
        return new FabricTabRegistry(modId);
    }

    public static IMenuRegistry _createMenu(String modId) {
        return new FabricMenuRegistry(modId);
    }

    public static IScreenRegistry _createScreen(String modId) {
        return new FabricScreenRegistry();
    }

    public static ICommandRegistry _createCommand(String modId) {
        return new FabricCommandRegister();
    }

    public static IDataProviderRegistry _createDataProvider(String modId) {
        return new IDataProviderRegistry() {
            @Override
            public <T extends DataProvider> Supplier<T> put(Function<IDataGenContext, ? extends T> builder, boolean run) {
                return () -> null;
            }
        };
    }

    public static ICustomStatRegistry _createCustomStat(String modId) {
        return new FabricCustomStatRegistry(modId);
    }
}
