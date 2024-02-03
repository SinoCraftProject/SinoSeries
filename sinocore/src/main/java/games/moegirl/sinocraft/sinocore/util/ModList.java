package games.moegirl.sinocraft.sinocore.util;

import dev.architectury.injectables.annotations.ExpectPlatform;
import org.apache.commons.lang3.tuple.Pair;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Stream;

public class ModList {

    public static final Logger LOGGER = LogManager.getLogger();

    @ExpectPlatform
    public static Stream<IModContainer> getAllMods() {
        throw new AssertionError();
    }

    @ExpectPlatform
    public static Optional<IModContainer> findMod(String modId) {
        throw new AssertionError();
    }

    /**
     * Mod 信息
     */
    public interface IModContainer {

        String getId();

        String getName();

        String getVersion();

        String getDescription();

        Object getPlatformContainer();

        /**
         * 获取 Forge Mod 的主类，Fabric 返回 empty
         */
        Optional<Object> getForgeMainObject();

        /**
         * 遍历 Mod 所有文件
         */
        default Stream<Path> walkFiles() {
            return getRootFiles().stream().flatMap(p -> Functions.getStreamOrEmpty(() -> Files.walk(p), LOGGER));
        }

        /**
         * 遍历 Mod 所有文件，第一个值表示该文件所在的根目录
         */
        default Stream<Pair<Path, Path>> walkRootAndFiles() {
            return getRootFiles().stream().flatMap(p -> Functions.getStreamOrEmpty(() -> Files.walk(p).map(c -> Pair.of(p, c)), LOGGER));
        }

        /**
         * 遍历 Mod 所有类
         */
        default Stream<Class<?>> walkClasses() {
            return walkRootAndFiles()
                    .filter(pr -> pr.getValue().getFileName().toString().endsWith(".class"))
                    .filter(pr -> !Objects.equals("package-info.class", pr.getValue().getFileName().toString()))
                    .map(pr -> parseClassName(pr.getKey(), pr.getValue()))
                    .map(s -> Functions.getOrEmpty(() -> Class.forName(s), ModList.LOGGER))
                    .filter(Optional::isPresent)
                    .map(Optional::get);
        }

        /**
         * 获取 Mod 根目录或 jar 包文件
         */
        List<Path> getRootFiles();

        private String parseClassName(Path root, Path file) {
            String className = file.toString();
            className = className.substring(0, className.length() - 6);

            // forge: str(root) = '/'
            // fabric: str(root) = 完整路径
            if (className.startsWith(root.toString())) {
                className = className.substring(root.toString().length());
            }
            // fabric: 分隔符为 File.separator
            // forge: 分隔符为 '/'
            className = className.replace(File.separator, ".");
            className = className.replace("/", ".");
            while (className.startsWith(".")) {
                className = className.substring(1);
            }
            return className;
        }
    }
}
