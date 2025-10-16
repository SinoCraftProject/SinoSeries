package games.moegirl.sinocraft.sinocore.utility.fabric;

import games.moegirl.sinocraft.sinocore.utility.ModList;
import net.fabricmc.loader.api.FabricLoader;
import net.fabricmc.loader.api.ModContainer;

import java.io.File;
import java.io.FileNotFoundException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Optional;

public class ModListImpl {

    public static Optional<ModList.IModContainer> findModById(String modId) {
        return FabricLoader.getInstance().getModContainer(modId).map(FabricModListImpl::new);
    }

    public static boolean isModExists(String modId) {
        return FabricLoader.getInstance().isModLoaded(modId);
    }

    public static class FabricModListImpl implements ModList.IModContainer {

        private final ModContainer container;

        public FabricModListImpl(ModContainer container) {
            this.container = container;
        }

        @Override
        public String getId() {
            return container.getMetadata().getId();
        }

        @Override
        public String getName() {
            return container.getMetadata().getName();
        }

        @Override
        public String getVersion() {
            return container.getMetadata().getVersion().getFriendlyString();
        }

        @Override
        public Path findModFile(String... subPaths) {
            List<Path> paths = container.getRootPaths();
            // 首先，Mod 必须得有个根路径
            if (paths.isEmpty()) throw new RuntimeException("Mod " + getName() + " has no root path");
            String joinedPath = String.join(File.separator, subPaths);
            for (Path path : paths) {
                // 有多个路径的话，找一个存在的
                Path resolve = path.resolve(joinedPath);
                if (Files.exists(resolve) || Files.isDirectory(resolve)) return resolve;
            }
            // 都没
            StringBuilder exceptionMessage = new StringBuilder("Mod ").append(getName()).append(" not found file: ");
            for (Path path : paths) {
                exceptionMessage.append("\n\t").append(path.resolve(joinedPath)).append(", ");
            }
            throw new RuntimeException(new FileNotFoundException(exceptionMessage.toString()));
        }

        @Override
        public Object getModContainer() {
            return container;
        }

        @Override
        public List<Path> getRootFiles() {
            return container.getRootPaths();
        }
    }
}
