package games.moegirl.sinocraft.sinocore.registry.fabric;

import games.moegirl.sinocraft.sinocore.registry.IDataProviderRegister;
import games.moegirl.sinocraft.sinocore.registry.IRegistry;
import games.moegirl.sinocraft.sinocore.registry.ITabRegistry;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;

public class RegistryManagerImpl {

    public static <T> IRegistry<T> _create(String modId, ResourceKey<Registry<T>> key) {
        return new FabricRegistryImpl<>(modId, key);
    }

    public static ITabRegistry _createTab(String modId) {
        return new FabricTabRegistryImpl(modId);
    }

    public static IDataProviderRegister _createDataProvider(String modId) {
        throw new IllegalStateException("Data Provider only for forge platform.");
    }
}
