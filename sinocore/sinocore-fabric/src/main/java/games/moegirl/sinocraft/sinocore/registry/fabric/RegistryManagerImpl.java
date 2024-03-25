package games.moegirl.sinocraft.sinocore.registry.fabric;

import games.moegirl.sinocraft.sinocore.registry.*;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;

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
}
