package games.moegirl.sinocraft.sinocore.registry;

import dev.architectury.injectables.annotations.ExpectPlatform;
import games.moegirl.sinocraft.sinocore.SinoCore;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;

import java.util.HashMap;
import java.util.Map;

public class RegistryManager {

    private static final Map<String, Map<ResourceKey<?>, IRegistry<?>>> DEF_MAP = new HashMap<>();
    private static final Map<String, ITabRegistry> TAB_MAP = new HashMap<>();

    public synchronized static <T> IRegistry<T> create(String modId, ResourceKey<Registry<T>> key) {
        if (Registries.CREATIVE_MODE_TAB.equals(key)) {
            SinoCore.LOGGER.warn("Use obtainTab or createTab to add creative mod tab easier.");
        }

        IRegistry<T> registry = _create(modId, key);
        DEF_MAP.computeIfAbsent(modId, __ -> new HashMap<>()).put(key, registry);
        return registry;
    }

    public synchronized static <T> IRegistry<T> obtain(String modId, ResourceKey<Registry<T>> key) {
        if (Registries.CREATIVE_MODE_TAB.equals(key)) {
            SinoCore.LOGGER.warn("Use obtainTab or createTab to add creative mod tab easier.");
        }

        return (IRegistry<T>) DEF_MAP
                .computeIfAbsent(modId, __ -> new HashMap<>())
                .computeIfAbsent(key, __ -> _create(modId, key));
    }

    public synchronized static ITabRegistry createTab(String modId) {
        ITabRegistry registry = _createTab(modId);
        TAB_MAP.put(modId, registry);
        return registry;
    }

    public synchronized static ITabRegistry obtainTab(String modId) {
        return TAB_MAP.computeIfAbsent(modId, RegistryManager::_createTab);
    }

    @ExpectPlatform
    public static <T> IRegistry<T> _create(String modId, ResourceKey<Registry<T>> key) {
        throw new AssertionError();
    }

    @ExpectPlatform
    public static ITabRegistry _createTab(String modId) {
        throw new AssertionError();
    }
}
