package games.moegirl.sinocraft.sinocore.forge;

import net.minecraft.server.MinecraftServer;
import net.minecraftforge.server.ServerLifecycleHooks;

public class SinoCorePlatformImpl {

    public static MinecraftServer getServer() {
        return ServerLifecycleHooks.getCurrentServer();
    }
}
