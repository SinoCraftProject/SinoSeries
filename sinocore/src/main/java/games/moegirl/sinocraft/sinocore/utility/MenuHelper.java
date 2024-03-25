package games.moegirl.sinocraft.sinocore.utility;

import dev.architectury.injectables.annotations.ExpectPlatform;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.MenuProvider;

public class MenuHelper {

    @ExpectPlatform
    public static void openMenu(ServerPlayer player, ExtendedMenuProvider provider) {
        throw new AssertionError();
    }

    public interface ExtendedMenuProvider extends MenuProvider {
        default void saveExtraData(FriendlyByteBuf buf) {
        }

        @Override
        default Component getDisplayName() {
            return Component.empty();
        }
    }
}
