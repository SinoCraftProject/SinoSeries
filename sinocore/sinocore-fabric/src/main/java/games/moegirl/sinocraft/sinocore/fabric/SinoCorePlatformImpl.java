package games.moegirl.sinocraft.sinocore.fabric;

import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.server.MinecraftServer;

public class SinoCorePlatformImpl {

    static MinecraftServer SERVER;

    public static MinecraftServer getServer() {
        return SERVER;
    }

    public static boolean isDevelopmentEnvironment() {
        return FabricLoader.getInstance().isDevelopmentEnvironment();
    }
}
