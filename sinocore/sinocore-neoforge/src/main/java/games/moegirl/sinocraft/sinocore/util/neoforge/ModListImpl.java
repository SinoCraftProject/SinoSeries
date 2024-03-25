package games.moegirl.sinocraft.sinocore.util.neoforge;

import games.moegirl.sinocraft.sinocore.util.ModList;
import net.neoforged.fml.ModContainer;

import java.nio.file.Path;
import java.util.Optional;

public class ModListImpl {

    public static Optional<ModList.ModContainer> findModById(String modId) {
        return net.neoforged.fml.ModList.get().getModContainerById(modId).map(NeoForgeModListImpl::new);
    }

    public static boolean isModExists(String modId) {
        return net.neoforged.fml.ModList.get().isLoaded(modId);
    }

    public static class NeoForgeModListImpl implements ModList.ModContainer {

        private final ModContainer container;

        public NeoForgeModListImpl(ModContainer container) {
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
    }
}
