package games.moegirl.sinocraft.sinocore.util.forge;

import games.moegirl.sinocraft.sinocore.util.ModList;
import net.minecraftforge.fml.ModContainer;

import java.nio.file.Path;
import java.util.Optional;

public class ModListImpl {

    public static Optional<ModList.ModContainer> findModById(String modId) {
        return net.minecraftforge.fml.ModList.get().getModContainerById(modId).map(ForgeModListImpl::new);
    }

    public static boolean isModExists(String modId) {
        return net.minecraftforge.fml.ModList.get().isLoaded(modId);
    }

    public static class ForgeModListImpl implements ModList.ModContainer {

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
    }
}
