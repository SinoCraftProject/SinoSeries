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
        return new FabricRegistryImpl<>(modId, key);
    }

    public static ITabRegistry _createTab(String modId) {
        return new FabricTabRegistryImpl(modId);
    }

    public static IMenuRegister _createMenu(String modId) {
        return new FabricMenuRegister(modId);
    }

    public static IScreenRegister _createScreen(String modId) {
        return new FabricScreenRegister();
    }

    public static ICommandRegistry _createCommand(String modId) {
        return new FabricCommandRegister();
    }

    public static IDataProviderRegister _createDataProvider(String modId) {
        return new IDataProviderRegister() {
            @Override
            public <T extends DataProvider> Supplier<T> put(Function<IDataGenContext, ? extends T> builder, boolean run) {
                return () -> null;
            }
        };
    }
}
