package games.moegirl.sinocraft.sinocore.util.neoforge;

import games.moegirl.sinocraft.sinocore.util.MenuHelper;
import net.minecraft.server.level.ServerPlayer;
import net.neoforged.neoforge.network.NetworkHooks;

public class MenuHelperImpl {

    public static void openMenu(ServerPlayer player, MenuHelper.ExtendedMenuProvider provider) {
        NetworkHooks.openScreen(player, provider, provider::saveExtraData);
    }
}
