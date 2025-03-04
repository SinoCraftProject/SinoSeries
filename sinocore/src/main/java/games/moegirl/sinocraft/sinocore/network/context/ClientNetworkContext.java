package games.moegirl.sinocraft.sinocore.network.context;

import net.minecraft.client.Minecraft;
import net.minecraft.network.Connection;
import net.minecraft.network.ConnectionProtocol;
import org.jetbrains.annotations.NotNull;

public abstract sealed class ClientNetworkContext extends NetworkContext permits ClientConfigurationNetworkContext, ClientPlayNetworkContext {
    @NotNull
    protected final Minecraft minecraft;

    public ClientNetworkContext(ConnectionProtocol stage, Connection connection, @NotNull Minecraft minecraft) {
        super(stage, connection);
        this.minecraft = minecraft;
    }

    public @NotNull Minecraft getMinecraft() {
        return minecraft;
    }
}
