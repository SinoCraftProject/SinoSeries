package games.moegirl.sinocraft.sinocore.registry.neoforge;

import games.moegirl.sinocraft.sinocore.registry.*;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;

public class RegistryManagerImpl {

    public static <T> IRegistry<T> _create(String modId, ResourceKey<Registry<T>> key) {
        return new NeoForgeRegistry<>(modId, key);
    }

    public static ITabRegistry _createTab(String modId) {
        return new NeoForgeTabRegistry(modId);
    }

    public static IMenuRegistry _createMenu(String modId) {
        return new NeoForgeMenuRegistry(modId);
    }

    public static IScreenRegistry _createScreen(String modId) {
        return new NeoForgeScreenRegistry(modId);
    }

    public static ICommandRegistry _createCommand(String modId) {
        return new NeoForgeCommandRegister(modId);
    }

    public static ICustomStatRegistry _createCustomStat(String modId) {
        return new NeoForgeCustomStatRegistry(modId);
    }
}
