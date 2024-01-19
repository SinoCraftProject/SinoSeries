package games.moegirl.sinocraft.sinocore;

import dev.architectury.injectables.annotations.ExpectPlatform;
import net.minecraft.server.MinecraftServer;

public class SinoCorePlatform {

    @ExpectPlatform
    public static MinecraftServer getServer() {
        throw new AssertionError();
    }
}
