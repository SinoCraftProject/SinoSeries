package games.moegirl.sinocraft.sinocore.registry;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.stats.Stat;
import net.minecraft.stats.StatFormatter;

import java.util.Optional;

public interface ICustomStatRegistry extends IRegistryBase<ResourceLocation> {
    ResourceLocation register(String name, StatFormatter statFormatter);

    default ResourceLocation register(String name) {
        return register(name, StatFormatter.DEFAULT);
    }
}
