package games.moegirl.sinocraft.sinocore.fabric;

import games.moegirl.sinocraft.sinocore.data.gen.DataGenContext;
import net.minecraft.core.HolderLookup;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.server.MinecraftServer;

import java.util.concurrent.CompletableFuture;

public class SinoCorePlatformImpl {

    static MinecraftServer SERVER;

    public static MinecraftServer getServer() {
        return SERVER;
    }

    public static boolean isDevelopmentEnvironment() {
        return FabricLoader.getInstance().isDevelopmentEnvironment();
    }

    public static DataGenContext buildDataGeneratorContext(Object object, CompletableFuture<HolderLookup.Provider> registriesFuture) {
        throw new IllegalStateException("DataProvider only for forge platform.");
    }
}
