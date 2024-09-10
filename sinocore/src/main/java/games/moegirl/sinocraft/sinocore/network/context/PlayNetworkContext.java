package games.moegirl.sinocraft.sinocore.network.context;

import net.minecraft.client.Minecraft;
import net.minecraft.network.Connection;
import net.minecraft.network.ConnectionProtocol;
import net.minecraft.network.protocol.PacketFlow;
import net.minecraft.server.level.ServerPlayer;
import org.jetbrains.annotations.Nullable;

/**
 * 网络通信上下文
 *
 * @param stage      连接阶段
 * @param connection 网络连接
 * @param sender     发送信息的玩家（目标是客户端时为 null）
 * @param client     客户端（目标是服务端时为 null）
 */
public record PlayNetworkContext(ConnectionProtocol stage, Connection connection,
                                 @Nullable ServerPlayer sender, @Nullable Minecraft client) {

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
}
