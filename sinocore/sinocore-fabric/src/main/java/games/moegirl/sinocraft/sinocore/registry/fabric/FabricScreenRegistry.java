package games.moegirl.sinocraft.sinocore.registry.fabric;

import com.mojang.datafixers.util.Pair;
import games.moegirl.sinocraft.sinocore.registry.IRegRef;
import games.moegirl.sinocraft.sinocore.registry.IScreenRegistry;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("unchecked")
public class FabricScreenRegistry implements IScreenRegistry {

    private final List<Pair<IRegRef<MenuType<?>>, IScreenFactory<?>>> screens = new ArrayList<>();

    private final String modId;

    public FabricScreenRegistry(String modId) {
        this.modId = modId;
    }

    @Override
    public String modId() {
        return modId;
    }

    @Override
    public void register() {
        for (Pair<IRegRef<MenuType<?>>, IScreenFactory<?>> screen : screens) {
            MenuScreens.register(screen.getFirst().get(), new ScreenFactoryWrapper(screen.getSecond()));
        }
    }

    @Override
    public <T extends AbstractContainerMenu> void register(IRegRef<MenuType<?>> menuType, IScreenFactory<T> screenFactory) {
        screens.add(Pair.of(menuType, screenFactory));
    }

    @SuppressWarnings("rawtypes")
    static class ScreenFactoryWrapper implements MenuScreens.ScreenConstructor {

        private final IScreenFactory factory;

        public ScreenFactoryWrapper(IScreenFactory<?> factory) {
            this.factory = factory;
        }

        @Override
        public @NotNull Screen create(AbstractContainerMenu menu, Inventory inventory, Component title) {
            return factory.create(menu, inventory, title);
        }

        @Override
        public void fromPacket(Component title, MenuType type, Minecraft mc, int windowId) {
            factory.fromPacket(title, type, mc, windowId);
        }
    }
}
