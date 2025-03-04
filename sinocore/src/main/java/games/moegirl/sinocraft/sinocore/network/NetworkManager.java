package games.moegirl.sinocraft.sinocore.network;

import dev.architectury.injectables.annotations.ExpectPlatform;
import games.moegirl.sinocraft.sinocore.network.context.*;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;

/**
 * 实现了网络包的注册和发送
 * 替代 INetworkChannel
 */
public class NetworkManager {
    /**
     * （Play 阶段）服务端将数据包发送到某玩家客户端
     *
     * @param payload 数据包
     * @param player  玩家
     * @param <T>     数据包类型
     */
    @ExpectPlatform
    public static <T extends CustomPacketPayload> void send(T payload, ServerPlayer player) {
        throw new AssertionError();
    }

    /**
     * （Play 阶段）将数据包按某些规则发送
     * <p></p>
     * {@link PacketTarget} 通过 {@link PacketDistributor#with(Object)} 或 {@link PacketDistributor#noArg()} )} 获取
     *
     * @param payload 数据包
     * @param target  接收目标
     * @param <T>     数据包类型
     * @see PacketDistributor
     */
    @ExpectPlatform
    public static <T extends CustomPacketPayload> void send(T payload, PacketTarget target) {
        throw new AssertionError();
    }

    /**
     * （Play 阶段）客户端将数据包发送到服务端
     *
     * @param payload 数据包
     * @param <T>     数据包类型
     */
    @ExpectPlatform
    public static <T extends CustomPacketPayload> void sendToServer(T payload) {
        throw new AssertionError();
    }

    public static <T extends CustomPacketPayload> PacketBuilder<T, RegistryFriendlyByteBuf, ClientPlayNetworkContext, ServerPlayNetworkContext> playPacket(Class<T> clazz, String id) {
        return playPacket(CustomPacketPayload.createType(id));
    }

    public static <T extends CustomPacketPayload> PacketBuilder<T, RegistryFriendlyByteBuf, ClientPlayNetworkContext, ServerPlayNetworkContext> playPacket(Class<T> clazz, ResourceLocation id) {
        return playPacket(new CustomPacketPayload.Type<>(id));
    }

    public static <T extends CustomPacketPayload> PacketBuilder<T, RegistryFriendlyByteBuf, ClientPlayNetworkContext, ServerPlayNetworkContext> playPacket(CustomPacketPayload.Type<T> type) {
        return new PacketBuilder<>(NetworkManager::registerPlay, type);
    }

    @ExpectPlatform
    static <T extends CustomPacketPayload> void registerPlay(PacketBuilder<T, RegistryFriendlyByteBuf, ClientPlayNetworkContext, ServerPlayNetworkContext> packet) {
        throw new AssertionError();
    }

    // Todo: Configuration tasks API
//    /**
//     * （Configuration 阶段）服务端将数据包发送到某玩家客户端
//     *
//     * @param payload 数据包
//     * @param handler 服务端 PacketListener
//     * @param <T>     数据包类型
//     */
//    @ExpectPlatform
//    public static <T extends CustomPacketPayload> void sendConfiguration(T payload, ServerConfigurationPacketListenerImpl handler) {
//        throw new AssertionError();
//    }
//
//    /**
//     * （Configuration 阶段）客户端将数据包发送到服务端
//     *
//     * @param payload 数据包
//     * @param <T>     数据包类型
//     */
//    @ExpectPlatform
//    public static <T extends CustomPacketPayload> void sendConfigurationToServer(T payload) {
//        throw new AssertionError();
//    }
//
//    public static <T extends CustomPacketPayload> PacketBuilder<T, FriendlyByteBuf, ClientConfigurationNetworkContext, ServerConfigurationNetworkContext> configurationPacket(Class<T> clazz, String id) {
//        return configurationPacket(CustomPacketPayload.createType(id));
//    }
//
//    public static <T extends CustomPacketPayload> PacketBuilder<T, FriendlyByteBuf, ClientConfigurationNetworkContext, ServerConfigurationNetworkContext> configurationPacket(Class<T> clazz, ResourceLocation id) {
//        return configurationPacket(new CustomPacketPayload.Type<>(id));
//    }
//
//    public static <T extends CustomPacketPayload> PacketBuilder<T, FriendlyByteBuf, ClientConfigurationNetworkContext, ServerConfigurationNetworkContext> configurationPacket(CustomPacketPayload.Type<T> type) {
//        return new PacketBuilder<>(NetworkManager::registerConfiguration, type);
//    }
//
//    @ExpectPlatform
//    static <T extends CustomPacketPayload> void registerConfiguration(PacketBuilder<T, net.minecraft.network.FriendlyByteBuf, ClientConfigurationNetworkContext, ServerConfigurationNetworkContext> packet) {
//        throw new AssertionError();
//    }
}
