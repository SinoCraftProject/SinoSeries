package games.moegirl.sinocraft.sinocore.network;

import dev.architectury.injectables.annotations.ExpectPlatform;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.server.level.ServerPlayer;

/**
 * 实现了网络包的注册和发送
 * 替代 INetworkChannel
 */
public class NetworkManager {

    public static <T extends CustomPacketPayload> SinoPacketBuilder<T> packet(CustomPacketPayload.Type<T> type) {
        return new SinoPacketBuilder<>(type);
    }

    /**
     * （服务端->客户端）将数据包发送到某玩家
     *
     * @param packet 待发送数据包
     * @param player 接收玩家
     * @param <T>    数据包类型
     */
    @ExpectPlatform
    public static <T extends CustomPacketPayload> void send(T packet, ServerPlayer player) {
        throw new AssertionError();
    }

    /**
     * 将数据包按某些规则发送
     * <p></p>
     * {@link PacketTarget} 通过 {@link PacketDistributor#with(Object)} 或 {@link PacketDistributor#noArg()} )} 获取
     *
     * @param packet 待发送数据包
     * @param target 接收目标
     * @param <T>    数据包类型
     * @see PacketDistributor
     */
    @ExpectPlatform
    public static <T extends CustomPacketPayload> void send(T packet, PacketTarget target) {
        throw new AssertionError();
    }

    /**
     * （客户端->服务端）将数据包发送到服务器
     *
     * @param packet 数据包
     * @param <T>    数据包类型
     */
    @ExpectPlatform
    public static <T extends CustomPacketPayload> void sendToServer(T packet) {
        throw new AssertionError();
    }

    @ExpectPlatform
    static <T extends CustomPacketPayload> void register(SinoPacket<T> packet) {
        throw new AssertionError();
    }
}
