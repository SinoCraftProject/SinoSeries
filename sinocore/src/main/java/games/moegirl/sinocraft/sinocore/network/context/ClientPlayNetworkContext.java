package games.moegirl.sinocraft.sinocore.network.context;

import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.network.Connection;
import net.minecraft.network.ConnectionProtocol;
import org.jetbrains.annotations.NotNull;

public final class ClientPlayNetworkContext extends ClientNetworkContext {
    @NotNull
    private final LocalPlayer player;

    public ClientPlayNetworkContext(Connection connection, @NotNull Minecraft minecraft, @NotNull LocalPlayer player) {
        super(ConnectionProtocol.PLAY, connection, minecraft);
        this.player = player;
    }

    public @NotNull LocalPlayer getPlayer() {
        return player;
    }
}
