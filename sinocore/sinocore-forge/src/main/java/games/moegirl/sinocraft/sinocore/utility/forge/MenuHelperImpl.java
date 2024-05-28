package games.moegirl.sinocraft.sinocore.utility.forge;

import games.moegirl.sinocraft.sinocore.utility.MenuHelper;
import net.minecraft.server.level.ServerPlayer;

public class MenuHelperImpl {

    public static void openMenu(ServerPlayer player, MenuHelper.ExtendedMenuProvider provider) {
        player.openMenu(provider);
    }
}
