package games.moegirl.sinocraft.sinocore;

import dev.architectury.injectables.annotations.ExpectPlatform;
import games.moegirl.sinocraft.sinocore.data.gen.IDataGenContext;
import net.minecraft.core.HolderLookup;
import net.minecraft.server.MinecraftServer;

import java.nio.file.Path;
import java.util.concurrent.CompletableFuture;

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
     * 创建用于创建 DataProvider 对象的上下文，不同平台使用不同类型的上下文对象。其中第一个参数类型如下：
     * <ul>
     *     <li>Forge: GatherDataEvent</li>
     *     <li>Fabric: FabricDataOutput，可以从 FabricDataGenerator.Pack 中获取</li>
     * </ul>
     *
     * @param platformContext  用于获取目录与 Mod 信息的上下文相关对象
     * @param registriesFuture 用于获取注册表信息的上下文对象
     */
    @ExpectPlatform
    public static IDataGenContext buildDataGeneratorContext(Object platformContext, CompletableFuture<HolderLookup.Provider> registriesFuture) {
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
