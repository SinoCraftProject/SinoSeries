package games.moegirl.sinocraft.sinocore.network.context;

import net.minecraft.network.Connection;
import net.minecraft.network.ConnectionProtocol;
import net.minecraft.network.protocol.PacketFlow;

public sealed class NetworkContext permits ClientNetworkContext, ServerNetworkContext {
    protected final ConnectionProtocol stage;
    protected final Connection connection;

    public NetworkContext(ConnectionProtocol stage, Connection connection) {
        this.stage = stage;
        this.connection = connection;
    }

    public ConnectionProtocol getStage() {
        return stage;
    }

    public Connection getConnection() {
        return connection;
    }

    /**
     * 获取接收端类型
     *
     * @return 接收端类型
     */
    public PacketFlow getReceiver() {
        return connection.getReceiving();
    }

    /**
     * 获取发送端类型
     *
     * @return 发送端类型
     */
    public PacketFlow getSender() {
        return connection.getSending();
    }

    /**
     * 当前为逻辑客户端
     *
     * @return 是否为逻辑客户端
     */
    public boolean isServerSide() {
        return connection.getReceiving() == PacketFlow.SERVERBOUND;
    }

    /**
     * 当前为逻辑服务端
     *
     * @return 是否为逻辑服务端
     */
    public boolean isClientSide() {
        return !isServerSide();
    }
}
