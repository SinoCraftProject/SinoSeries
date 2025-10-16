package games.moegirl.sinocraft.sinocore.registry;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.inventory.MenuAccess;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;

/**
 * Screen 注册表 <br/>
 *
 * <b>注意：{@link net.minecraft.client.gui.screens.Screen} 只能在客户端访问</b>
 */
public interface IScreenRegistry extends IRegistrable<Screen> {
    <T extends AbstractContainerMenu> void register(IRegRef<MenuType<?>> menuType, IScreenFactory<T> screenFactory);

    interface IScreenFactory<T extends AbstractContainerMenu> {

        default void fromPacket(Component title, MenuType<T> type, Minecraft mc, int windowId) {
            assert mc.player != null;
            T menu = type.create(windowId, mc.player.getInventory());
            Screen screen = this.create(menu, mc.player.getInventory(), title);
            mc.player.containerMenu = ((MenuAccess<?>) screen).getMenu();
            mc.setScreen(screen);
        }

        Screen create(T menu, Inventory inventory, Component title);
    }
}
