package games.moegirl.sinocraft.sinocore.util.forge;

import games.moegirl.sinocraft.sinocore.util.MenuHelper;
import net.minecraft.server.level.ServerPlayer;

public class MenuHelperImpl {

    public static void openMenu(ServerPlayer player, MenuHelper.ExtendedMenuProvider provider) {
        player.openMenu(provider, provider::saveExtraData);
    }
}
