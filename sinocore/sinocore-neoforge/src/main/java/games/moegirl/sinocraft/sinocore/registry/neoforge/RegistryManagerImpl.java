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
    public static IMenuRegister _createMenu(String modId) {
        return new NeoForgeMenuRegister(modId);
    }

    public static IScreenRegister _createScreen(String modId) {
        return new NeoForgeScreenRegister();
    }

    public static ICommandRegistry _createCommand(String modId) {
        return new NeoForgeCommandRegister();
    }
}
