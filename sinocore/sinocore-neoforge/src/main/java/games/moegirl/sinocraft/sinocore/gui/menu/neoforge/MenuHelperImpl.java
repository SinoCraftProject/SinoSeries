package games.moegirl.sinocraft.sinocore.gui.menu.neoforge;

import games.moegirl.sinocraft.sinocore.gui.menu.IExtraDataMenuProvider;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkHooks;

public class MenuHelperImpl {
    public static void openMenuWithData(ServerPlayer player, IExtraDataMenuProvider provider) {
        NetworkHooks.openScreen(player, provider, provider::writeExtraData);
    }
}
