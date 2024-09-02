package games.moegirl.sinocraft.sinocore.registry.neoforge;

import games.moegirl.sinocraft.sinocore.registry.*;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;

public class RegistryManagerImpl {

    public static <T> IRegistry<T> _create(String modId, ResourceKey<Registry<T>> key) {
        return new NeoForgeRegistryImpl<>(modId, key);
    }

    public static ITabRegistry _createTab(String modId) {
        return new NeoForgeTabRegistryImpl(modId);
    }

    public static IMenuRegistry _createMenu(String modId) {
        return new NeoForgeMenuRegistry(modId);
    }

    public static IScreenRegistry _createScreen(String modId) {
        return new NeoForgeScreenRegistry();
    }

    public static ICommandRegistry _createCommand(String modId) {
        return new NeoForgeCommandRegister();
    }

    public static IDataProviderRegistry _createDataProvider(String modId) {
        return new NeoForgeDataProviderRegistryImpl();
    }

    public static ICustomStatRegister _createCustomStat(String modId) {
        return new NeoForgeCustomStatRegister(modId);
    }
}
