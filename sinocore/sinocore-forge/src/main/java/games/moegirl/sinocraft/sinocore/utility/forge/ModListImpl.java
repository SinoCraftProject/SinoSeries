package games.moegirl.sinocraft.sinocore.utility.forge;

import net.minecraftforge.fml.ModContainer;
import net.minecraftforge.fml.ModList;

import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.nio.file.spi.FileSystemProvider;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

public class ModListImpl {

    private static final FileSystemProvider UFSP = FileSystemProvider.installedProviders().stream()
            .filter(f -> "union".equalsIgnoreCase(f.getScheme())).findFirst().orElseThrow();

    private static final List<games.moegirl.sinocraft.sinocore.utility.ModList.IModContainer> containers = new ArrayList<>();

    public static Stream<games.moegirl.sinocraft.sinocore.utility.ModList.IModContainer> getAllMods() {
        if (containers.isEmpty()) {
            ModList.get().forEachModContainer((name, mc) -> containers.add(new ForgeModContainerWrapper(mc)));
        }
        return containers.stream();
    }

    public static Optional<games.moegirl.sinocraft.sinocore.utility.ModList.IModContainer> findMod(String modId) {
        return ModList.get().getModContainerById(modId).map(ForgeModContainerWrapper::new);
    }

    record ForgeModContainerWrapper(ModContainer container)
            implements games.moegirl.sinocraft.sinocore.utility.ModList.IModContainer {

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
            return container.getModInfo().getVersion().getQualifier();
        }

        @Override
        public String getDescription() {
            return container.getModInfo().getDescription();
        }

        @Override
        public Object getPlatformContainer() {
            return container;
        }

        @Override
        public Optional<Object> getForgeMainObject() {
            return Optional.ofNullable(container.getMod());
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
