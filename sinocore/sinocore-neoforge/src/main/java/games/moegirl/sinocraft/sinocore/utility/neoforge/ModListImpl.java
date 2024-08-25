package games.moegirl.sinocraft.sinocore.utility.neoforge;

import games.moegirl.sinocraft.sinocore.utility.ModList;
import net.minecraftforge.fml.ModContainer;

import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.nio.file.spi.FileSystemProvider;
import java.util.List;
import java.util.Optional;

public class ModListImpl {

    private static final FileSystemProvider UFSP = FileSystemProvider.installedProviders().stream()
            .filter(f -> "union".equalsIgnoreCase(f.getScheme())).findFirst().orElseThrow();

    public static Optional<ModList.IModContainer> findModById(String modId) {
        return net.minecraftforge.fml.ModList.get().getModContainerById(modId).map(ForgeModListImpl::new);
    }

    public static boolean isModExists(String modId) {
        return net.minecraftforge.fml.ModList.get().isLoaded(modId);
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
            try {
                Object modObject = container.getMod();
                if (modObject == null) {
                    return List.of(getResourcePathOnly(container));
                } else {
                    return List.of(getPathByClass(modObject.getClass()));
                }
            } catch (URISyntaxException e) {
                return List.of(getResourcePathOnly(container));
            }
        }

        private Path getPathByClass(Class<?> mainClass) throws URISyntaxException {
            URI uri = mainClass.getProtectionDomain().getCodeSource().getLocation().toURI();
            FileSystem fs;
            if ("file".equalsIgnoreCase(uri.getScheme())) {
                fs = FileSystems.getFileSystem(uri);
            } else {
                fs = UFSP.getFileSystem(uri);
            }
            return fs.getPath("/");
        }

        private Path getResourcePathOnly(ModContainer container) {
            return container.getModInfo().getOwningFile().getFile().getFilePath();
        }
    }
}
