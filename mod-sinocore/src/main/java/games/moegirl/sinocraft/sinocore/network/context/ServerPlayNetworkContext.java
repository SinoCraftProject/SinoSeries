package games.moegirl.sinocraft.sinocore.network.context;

import net.minecraft.network.Connection;
import net.minecraft.network.ConnectionProtocol;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;
import org.jetbrains.annotations.NotNull;

public final class ServerPlayNetworkContext extends ServerNetworkContext {
    @NotNull
    private final ServerPlayer player;

    public ServerPlayNetworkContext(Connection connection, @NotNull MinecraftServer server, @NotNull ServerPlayer player) {
        super(ConnectionProtocol.PLAY, connection, server);
        this.player = player;
    }

    public @NotNull ServerPlayer getPlayer() {
        return player;
    }
}
