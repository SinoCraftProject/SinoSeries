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

    ResourceKey<CreativeModeTab> register(String name);

    ResourceKey<CreativeModeTab> register(String name, Supplier<CreativeModeTab> supplier);

    TabItemGenerator tabItems(ResourceKey<CreativeModeTab> tab);
}
