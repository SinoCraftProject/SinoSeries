package games.moegirl.sinocraft.sinocore.registry.forge;

import games.moegirl.sinocraft.sinocore.registry.IMenuRegister;
import games.moegirl.sinocraft.sinocore.registry.IDataProviderRegister;
import games.moegirl.sinocraft.sinocore.registry.IRegistry;
import games.moegirl.sinocraft.sinocore.registry.IScreenRegister;
import games.moegirl.sinocraft.sinocore.registry.ITabRegistry;
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

    public static IDataProviderRegister _createDataProvider(String modId) {
        return new ForgeDataProviderRegisterImpl();
    }
}
