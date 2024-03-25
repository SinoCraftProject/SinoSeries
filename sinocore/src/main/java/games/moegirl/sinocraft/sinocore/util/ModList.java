package games.moegirl.sinocraft.sinocore.util;

import dev.architectury.injectables.annotations.ExpectPlatform;
import net.minecraft.resources.ResourceLocation;
import org.apache.commons.lang3.ArrayUtils;

import java.nio.file.Path;
import java.util.Optional;

public interface ModList {

    @ExpectPlatform
    static Optional<ModContainer> findModById(String modId) {
        throw new AssertionError();
    }

    @ExpectPlatform
    static boolean isModExists(String modId) {
        throw new AssertionError();
    }

    interface ModContainer {

        /**
         * 获取 mod id
         *
         * @return Mod id
         */
        String getId();

        /**
         * 获取 mod 名称
         *
         * @return 返回 mod （显示）名称
         */
        String getName();

        /**
         * 获取版本号
         *
         * @return 一个可读的版本号
         */
        String getVersion();

        /**
         * 从 Mod 包里获取一个文件或目录的路径，不保证一定存在该文件或该目录
         *
         * @param subPaths 子路径
         * @return 返回该文件或目录的路径
         */
        Path findModFile(String... subPaths);

        /**
         * 获取该 Mod 的原始 ModContainer 对象以获取更多信息，具体实现由平台决定
         *
         * @return 返回该 Mod 的 ModContainer 对象
         */
        Object getModContainer();

        /**
         * 从 Mod 包里获取一个 assets 资源，不保证一定存在该文件或该目录
         *
         * @param name 资源名
         * @return 返回该资源的路径
         */
        default Path findResource(ResourceLocation name) {
            String[] paths = name.getPath().split("/");
            paths = ArrayUtils.insert(0, paths, "assets", name.getNamespace());
            return findModFile(paths);
        }

        /**
         * 从 Mod 包里获取一个 assets 资源，不保证一定存在该文件或该目录
         *
         * @param name      资源名
         * @param extension 资源扩展名
         * @return 返回该资源的路径
         */
        default Path findResource(ResourceLocation name, String extension) {
            String[] paths = name.getPath().split("/");
            if (!extension.startsWith(".")) extension = "." + extension;
            paths[paths.length - 1] = paths[paths.length - 1] + extension;
            paths = ArrayUtils.insert(0, paths, "assets", name.getNamespace());
            return findModFile(paths);
        }

        /**
         * 从 Mod 包里获取一个 data 资源，不保证一定存在该文件或该目录
         *
         * @param name 资源名
         * @return 返回该资源的路径
         */
        default Path findData(ResourceLocation name) {
            String[] paths = name.getPath().split("/");
            paths = ArrayUtils.insert(0, paths, "data", name.getNamespace());
            return findModFile(paths);
        }

        default ResourceLocation modLoc(String name) {
            return new ResourceLocation(this.getId(), name);
        }
    }
}
