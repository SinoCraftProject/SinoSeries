package games.moegirl.sinocraft.sinocore;

import dev.architectury.injectables.annotations.ExpectPlatform;
import net.minecraft.server.MinecraftServer;

import java.nio.file.Path;

public class SinoCorePlatform {

    @ExpectPlatform
    public static MinecraftServer getServer() {
        throw new AssertionError();
    }

    @ExpectPlatform
    public static boolean isDevelopmentEnvironment() {
        throw new AssertionError();
    }

    /**
     * 获取配置文件目录
     */
    @ExpectPlatform
    public static Path getConfigFolder() {
        throw new AssertionError();
    }
}
