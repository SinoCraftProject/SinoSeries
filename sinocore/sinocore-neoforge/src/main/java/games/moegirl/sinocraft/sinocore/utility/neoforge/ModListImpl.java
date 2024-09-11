package games.moegirl.sinocraft.sinocore.utility.neoforge;

import games.moegirl.sinocraft.sinocore.utility.ModList;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.javafmlmod.FMLModContainer;
import net.neoforged.fml.loading.FMLLoader;

import java.io.FileInputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.nio.file.spi.FileSystemProvider;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.zip.ZipInputStream;

public class ModListImpl {

    private static final FileSystemProvider UFSP = FileSystemProvider.installedProviders().stream()
            .filter(f -> "union".equalsIgnoreCase(f.getScheme())).findFirst().orElseThrow();

    public static Optional<ModList.IModContainer> findModById(String modId) {
        return net.neoforged.fml.ModList.get().getModContainerById(modId).map(ForgeModListImpl::new);
    }

    public static boolean isModExists(String modId) {
        return net.neoforged.fml.ModList.get().isLoaded(modId);
    }

    public static class ForgeModListImpl implements ModList.IModContainer {

        private final ModContainer container;

        public ForgeModListImpl(ModContainer container) {
            this.container = container;
        }

        @Override
        public String getId() {
            return container.getModId();
        }

        @Override
        public String getName() {
            return container.getModInfo().getDisplayName();
        }

        @Override
        public String getVersion() {
            return container.getModInfo().getVersion().toString();
        }

        @Override
        public Path findModFile(String... subPaths) {
            return container.getModInfo().getOwningFile().getFile().findResource(subPaths);
        }

        @Override
        public Object getModContainer() {
            return container;
        }

        @Override
        public List<Path> getRootFiles() {
            var layer = FMLLoader.getGameLayer().findModule(container.getModInfo().getOwningFile().moduleName()).orElseThrow();
            return container.getModInfo().getOwningFile().getFile().getScanResult().getClasses()
                    .stream()
                    .map(d -> Class.forName(layer, d.clazz().getClassName()))
                    .map(this::getPathByClass)
                    .toList();
        }

        private Path getPathByClass(Class<?> mainClass) {
            try {
                var uri = mainClass.getProtectionDomain().getCodeSource().getLocation().toURI();
                FileSystem fs;
                if ("file".equalsIgnoreCase(uri.getScheme())) {
                    fs = FileSystems.getFileSystem(uri);
                } else {
                    fs = UFSP.getFileSystem(uri);
                }
                return fs.getPath("/");
            } catch (URISyntaxException ex) {
                throw new RuntimeException(ex);
            }
        }

        private Path getResourcePathOnly(ModContainer container) {
            return container.getModInfo().getOwningFile().getFile().getFilePath();
        }
    }
}
