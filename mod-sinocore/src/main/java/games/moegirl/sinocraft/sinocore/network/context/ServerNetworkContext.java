package games.moegirl.sinocraft.sinocore.network.context;

import net.minecraft.network.Connection;
import net.minecraft.network.ConnectionProtocol;
import net.minecraft.server.MinecraftServer;
import org.jetbrains.annotations.NotNull;

public sealed class ServerNetworkContext extends NetworkContext permits ServerConfigurationNetworkContext, ServerPlayNetworkContext {
    @NotNull
    protected MinecraftServer server;

    public ServerNetworkContext(ConnectionProtocol stage, Connection connection, @NotNull MinecraftServer server) {
        super(stage, connection);
        this.server = server;
    }

    public @NotNull MinecraftServer getServer() {
        return server;
    }
}
