package games.moegirl.sinocraft.sinocore.gui.menu;

import dev.architectury.injectables.annotations.ExpectPlatform;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.MenuProvider;

public class MenuHelper {
    public static void openMenu(ServerPlayer player, MenuProvider provider) {
        player.openMenu(provider);
    }

    public static void openMenu(ServerPlayer player, ISimpleMenuProvider provider) {
        player.openMenu(provider);
    }

    @ExpectPlatform
    public static void openMenuWithData(ServerPlayer player, IExtraDataMenuProvider provider) {
        throw new AssertionError();
    }
}
