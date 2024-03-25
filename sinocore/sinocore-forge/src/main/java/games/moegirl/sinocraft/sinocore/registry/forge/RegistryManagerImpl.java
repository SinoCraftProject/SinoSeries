package games.moegirl.sinocraft.sinocore.registry.forge;

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

    public static IMenuRegister _createMenu(String modId) {
        return new ForgeMenuRegister(modId);
    }

    public static IScreenRegister _createScreen(String modId) {
        return new ForgeScreenRegister();
    }

    public static ICommandRegistry _createCommand(String modId) {
        return new ForgeCommandRegister();
    }

    public static IDataProviderRegister _createDataProvider(String modId) {
        return new ForgeDataProviderRegisterImpl();
    }
}
