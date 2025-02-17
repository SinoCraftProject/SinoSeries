package games.moegirl.sinocraft.sinocore.registry.fabric;

import games.moegirl.sinocraft.sinocore.registry.*;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;

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
        return new FabricScreenRegistry(modId);
    }

    public static ICommandRegistry _createCommand(String modId) {
        return new FabricCommandRegister(modId);
    }

    public static ICustomStatRegistry _createCustomStat(String modId) {
        return new FabricCustomStatRegistry(modId);
    }
}
