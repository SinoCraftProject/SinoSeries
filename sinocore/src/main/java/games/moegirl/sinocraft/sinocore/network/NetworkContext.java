package games.moegirl.sinocraft.sinocore.network;

import games.moegirl.sinocraft.sinocore.SinoCore;
import net.minecraft.network.Connection;
import net.minecraft.network.ConnectionProtocol;
import net.minecraft.network.PacketListener;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.PacketFlow;
import net.minecraft.server.level.ServerPlayer;

/**
 * 网络通信上下文
 *
 * @param connection 网络连接
 * @param sender     信息发送者，若信息方向为从服务端发送到客户端，该方法返回 null
 */
public record NetworkContext(Connection connection, ServerPlayer sender) implements PacketListener {

    /**
     * 当前为逻辑客户端
     *
     * @return 是否为逻辑客户端
     */
    public boolean isClientSide() {
        return connection.getReceiving() == PacketFlow.CLIENTBOUND;
    }

    /**
     * 当前为逻辑服务端
     *
     * @return 是否为逻辑服务端
     */
    public boolean isServerSide() {
        return !isClientSide();
    }

    /**
     * 获取接收端类型
     *
     * @return 接收端类型
     */
    public PacketFlow getReceiving() {
        return connection.getReceiving();
    }

    /**
     * 获取发送端类型
     *
     * @return 发送端类型
     */
    public PacketFlow getSending() {
        return connection.getSending();
    }

    // region From PacketListener

    @Override
    public PacketFlow flow() {
        return connection.getReceiving();
    }

    @Override
    public ConnectionProtocol protocol() {
        return ConnectionProtocol.PLAY;
    }

    @Override
    public void onDisconnect(Component reason) {
        SinoCore.LOGGER.warn("Connection interrupted: " + reason.getString());
    }

    @Override
    public boolean isAcceptingMessages() {
        return false;
    }

    // endregion
}
