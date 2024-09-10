package games.moegirl.sinocraft.sinocore.data.gen;

import net.minecraft.data.DataProvider;
import net.minecraft.resources.ResourceLocation;

public interface ISinoDataProvider extends DataProvider {
    default ResourceLocation loc(String namespace, String path) {
        return ResourceLocation.fromNamespaceAndPath(namespace, path);
    }

    default ResourceLocation mcLoc(String name) {
        return loc("minecraft", name);
    }

    default ResourceLocation modLoc(String name) {
        return loc(getModId(), name);
    }

    String getModId();
}
