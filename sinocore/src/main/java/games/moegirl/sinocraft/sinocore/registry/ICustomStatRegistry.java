package games.moegirl.sinocraft.sinocore.registry;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.stats.Stat;
import net.minecraft.stats.StatFormatter;

import java.util.Optional;

public interface ICustomStatRegistry {
    String modId();

    void register();

    ResourceLocation register(String name, StatFormatter statFormatter);

    ResourceLocation register(String name);

    Optional<Stat<?>> get(ResourceLocation key);
}
