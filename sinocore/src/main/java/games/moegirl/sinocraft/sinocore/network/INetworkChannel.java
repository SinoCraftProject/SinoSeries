package games.moegirl.sinocraft.sinocore.network;

import games.moegirl.sinocraft.sinocore.util.Functions;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.PacketFlow;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.MenuProvider;

import java.util.function.Function;

/**
 * Mod 网络通信
 */
public interface INetworkChannel {

    ResourceLocation getId();

    /**
     * 注册一个数据包
     * <p></p>
     * 注意：Fabric 端会将类名作为 id 写入数据包，因此请尽量简洁
     *
     * @param <T>       数据包类型
     * @param direction 数据包发送目标。{@link PacketFlow#CLIENTBOUND} 表示从服务端发向客户端的数据包
     * @param type      数据包类型
     * @param decoder   将 {@link FriendlyByteBuf} 转换为数据包
     * @throws IllegalStateException 当同一个包重复注册时抛出
     */
    <T extends Packet<NetworkContext>> void registerPacket(PacketFlow direction, Class<T> type, Function<FriendlyByteBuf, T> decoder);

    /**
     * 注册一个数据包，要求对应类型需要有一个形参为 {@link FriendlyByteBuf} 的构造函数用于转化
     * <p></p>
     * 注意：Fabric 端会将类名作为 id 写入数据包，因此请尽量简洁
     *
     * @param <T>       数据包类型
     * @param direction 数据包发送目标。{@link PacketFlow#CLIENTBOUND} 表示从服务端发向客户端的数据包
     * @param type      数据包类型
     * @throws IllegalStateException 当同一个包重复注册时抛出
     */
    default <T extends Packet<NetworkContext>> void registerPacket(PacketFlow direction, Class<T> type) {
        registerPacket(direction, type, Functions.constructor(type, FriendlyByteBuf.class));
    }

    /**
     * （服务端->客户端）将数据包发送到某玩家
     *
     * @param packet 待发送数据包
     * @param player 接收玩家
     * @param <T>    数据包类型
     */
    <T extends Packet<NetworkContext>> void send(T packet, ServerPlayer player);

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
    <T extends Packet<NetworkContext>> void send(T packet, PacketTarget target);

    /**
     * （客户端->服务端）将数据包发送到服务器
     *
     * @param packet 数据包
     * @param <T>    数据包类型
     */
    <T extends Packet<NetworkContext>> void sendToServer(T packet);

    /**
     * 打开 GUI
     *
     * @param player 玩家
     * @param menu   gui
     */
    default void openGui(ServerPlayer player, MenuProvider menu) {
        player.openMenu(menu);
    }
}
