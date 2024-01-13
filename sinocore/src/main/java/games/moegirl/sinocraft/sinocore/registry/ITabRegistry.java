package games.moegirl.sinocraft.sinocore.registry;

import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.CreativeModeTab;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Supplier;

public interface ITabRegistry {

    Map<ResourceKey<CreativeModeTab>, TabItemGenerator> VANILLA_GENERATORS = new ConcurrentHashMap<>();
    Map<ResourceKey<CreativeModeTab>, TabItemGenerator> GENERATORS = new ConcurrentHashMap<>();

    void register();

    IRef<CreativeModeTab, CreativeModeTab> registerForRef(String name);

    default ResourceKey<CreativeModeTab> register(String name) {
        return registerForRef(name).getKey();
    }

    <T extends CreativeModeTab> IRef<CreativeModeTab, T> registerForRef(String name, Supplier<? extends T> supplier);

    default <T extends CreativeModeTab> ResourceKey<CreativeModeTab> register(String name, Supplier<? extends T> supplier) {
        return registerForRef(name, supplier).getKey();
    }

    TabItemGenerator tabItems(ResourceKey<CreativeModeTab> tab);
}
