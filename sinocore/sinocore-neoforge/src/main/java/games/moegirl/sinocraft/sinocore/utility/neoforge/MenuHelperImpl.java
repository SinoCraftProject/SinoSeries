package games.moegirl.sinocraft.sinocore.utility.neoforge;

import games.moegirl.sinocraft.sinocore.utility.MenuHelper;
import net.minecraft.server.level.ServerPlayer;
import net.neoforged.neoforge.network.NetworkHooks;

public class MenuHelperImpl {

    public static void openMenu(ServerPlayer player, MenuHelper.ExtendedMenuProvider provider) {
        NetworkHooks.openScreen(player, provider, provider::saveExtraData);
    }
}
