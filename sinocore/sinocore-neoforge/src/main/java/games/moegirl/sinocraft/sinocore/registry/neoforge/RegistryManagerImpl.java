package games.moegirl.sinocraft.sinocore.registry.neoforge;

import games.moegirl.sinocraft.sinocore.registry.*;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;

public class RegistryManagerImpl {

    public static <T> IRegistry<T> _create(String modId, ResourceKey<Registry<T>> key) {
        return new ForgeRegistryImpl<>(modId, key);
    }

    public static ITabRegistry _createTab(String modId) {
        return new ForgeTabRegistryImpl(modId);
    }

    public static IMenuRegistry _createMenu(String modId) {
        return new ForgeMenuRegistry(modId);
    }

    public static IScreenRegistry _createScreen(String modId) {
        return new ForgeScreenRegistry();
    }

    public static ICommandRegistry _createCommand(String modId) {
        return new ForgeCommandRegister();
    }

    public static IDataProviderRegistry _createDataProvider(String modId) {
        return new ForgeDataProviderRegistryImpl();
    }

    public static ICustomStatRegister _createCustomStat(String modId) {
        return new ForgeCustomStatRegister(modId);
    }
}
