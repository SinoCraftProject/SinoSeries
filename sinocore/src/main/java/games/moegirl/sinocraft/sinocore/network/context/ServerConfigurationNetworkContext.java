package games.moegirl.sinocraft.sinocore.network.context;

import net.minecraft.network.Connection;
import net.minecraft.network.ConnectionProtocol;
import net.minecraft.server.MinecraftServer;
import org.jetbrains.annotations.NotNull;

public final class ServerConfigurationNetworkContext extends ServerNetworkContext {
    public ServerConfigurationNetworkContext(Connection connection, @NotNull MinecraftServer server) {
        super(ConnectionProtocol.CONFIGURATION, connection, server);
    }
}
