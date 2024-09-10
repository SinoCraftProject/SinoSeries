package games.moegirl.sinocraft.sinocore.gui.menu.fabric;

import games.moegirl.sinocraft.sinocore.gui.menu.IExtraDataMenuProvider;
import io.netty.buffer.ByteBufUtil;
import io.netty.buffer.Unpooled;
import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerFactory;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class MenuHelperImpl {
    public static void openMenuWithData(ServerPlayer player, IExtraDataMenuProvider provider) {
        player.openMenu(new ExtendedScreenHandlerFactory<byte[]>() {
            @Override
            public byte[] getScreenOpeningData(ServerPlayer player) {
                var buf = new RegistryFriendlyByteBuf(Unpooled.buffer(), player.registryAccess());
                provider.writeExtraData(buf);
                byte[] bytes = ByteBufUtil.getBytes(buf);
                buf.release();
                return bytes;
            }

            @Override
            public @NotNull Component getDisplayName() {
                return provider.getDisplayName();
            }

            @Nullable
            @Override
            public AbstractContainerMenu createMenu(int i, Inventory inventory, Player player) {
                return provider.createMenu(i, inventory, player);
            }
        });
    }
}
