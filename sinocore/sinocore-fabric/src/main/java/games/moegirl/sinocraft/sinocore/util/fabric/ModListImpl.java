package games.moegirl.sinocraft.sinocore.util.fabric;

import games.moegirl.sinocraft.sinocore.util.ModList;
import net.fabricmc.loader.api.FabricLoader;
import net.fabricmc.loader.api.ModContainer;

import java.nio.file.Path;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

public class ModListImpl {

    public static Stream<ModList.IModContainer> getAllMods() {
        return FabricLoader.getInstance().getAllMods().stream().map(FabricModContainerWrapper::new);
    }

    public static Optional<ModList.IModContainer> findMod(String modId) {
        return FabricLoader.getInstance().getModContainer(modId).map(FabricModContainerWrapper::new);
    }

    record FabricModContainerWrapper(ModContainer container) implements ModList.IModContainer {

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
        public String getDescription() {
            return container.getMetadata().getDescription();
        }

        @Override
        public Object getPlatformContainer() {
            return container;
        }

        @Override
        public Optional<Object> getForgeMainObject() {
            return Optional.empty();
        }

        @Override
        public List<Path> getRootFiles() {
            return container.getRootPaths();
        }
    }
}
