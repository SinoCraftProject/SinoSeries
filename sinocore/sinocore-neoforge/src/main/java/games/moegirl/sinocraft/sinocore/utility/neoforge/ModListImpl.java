package games.moegirl.sinocraft.sinocore.utility.neoforge;

import net.neoforged.fml.ModContainer;
import net.neoforged.fml.ModList;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

public class ModListImpl {

    private static final List<games.moegirl.sinocraft.sinocore.utility.ModList.IModContainer> containers = new ArrayList<>();

    public static Stream<games.moegirl.sinocraft.sinocore.utility.ModList.IModContainer> getAllMods() {
        if (containers.isEmpty()) {
            ModList.get().forEachModContainer((name, mc) -> containers.add(new NeoForgeModContainerWrapper(mc)));
        }
        return containers.stream();
    }

    public static Optional<games.moegirl.sinocraft.sinocore.utility.ModList.IModContainer> findMod(String modId) {
        return ModList.get().getModContainerById(modId).map(NeoForgeModContainerWrapper::new);
    }

    record NeoForgeModContainerWrapper(ModContainer container)
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
            return List.of(container.getModInfo().getOwningFile().getFile().getFilePath());
        }
    }
}
