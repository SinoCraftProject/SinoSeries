package games.moegirl.sinocraft.sinocore.network.context;

import net.minecraft.client.Minecraft;
import net.minecraft.network.Connection;
import net.minecraft.network.ConnectionProtocol;
import org.jetbrains.annotations.NotNull;

public final class ClientConfigurationNetworkContext extends ClientNetworkContext {
    public ClientConfigurationNetworkContext(Connection connection, @NotNull Minecraft minecraft) {
        super(ConnectionProtocol.CONFIGURATION, connection, minecraft);
    }
}
