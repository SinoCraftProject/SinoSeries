package games.moegirl.sinocraft.sinocore.network.fabric;

import games.moegirl.sinocraft.sinocore.mixin.IServerCommonPacketListenerImplAccessor;
import games.moegirl.sinocraft.sinocore.network.INetworkChannel;
import games.moegirl.sinocraft.sinocore.network.NetworkContext;
import games.moegirl.sinocraft.sinocore.network.PacketTarget;
import games.moegirl.sinocraft.sinocore.utility.Reference;
import io.netty.buffer.Unpooled;
import net.fabricmc.api.EnvType;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayConnectionEvents;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.FabricPacket;
import net.fabricmc.fabric.api.networking.v1.PacketType;
import net.fabricmc.fabric.api.networking.v1.ServerPlayConnectionEvents;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.network.Connection;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.PacketFlow;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.function.Function;

public class FabricNetworkChannelImpl implements INetworkChannel {

    private final ResourceLocation id;
    private final Map<Class<?>, Reference<PacketType<PacketWrapper<?>>>> serverToClientTypeMapByClass = new HashMap<>();
    private final Map<Class<?>, Reference<PacketType<PacketWrapper<?>>>> clientToServerTypeMapByClass = new HashMap<>();

    public FabricNetworkChannelImpl(ResourceLocation id) {
        this.id = id;
    }

    @Override
    public ResourceLocation getId() {
        return id;
    }

    @Override
    public <T extends Packet<NetworkContext>> void registerPacket(PacketFlow direction, Class<T> type, Function<FriendlyByteBuf, T> decoder) {
        // 查重
        Map<Class<?>, Reference<PacketType<PacketWrapper<?>>>> map = selectMap(direction);
        if (map.containsKey(type)) {
            String from = direction == PacketFlow.CLIENTBOUND ? "server" : "client";
            String to = direction == PacketFlow.SERVERBOUND ? "server" : "client";
            throw new IllegalStateException("Packet " + type.getCanonicalName()
                    + " is existed in channel " + id
                    + " (" + from + "->" + to + ")");
        }

        // 注册事件
        ResourceLocation pId = new ResourceLocation(id.getNamespace(), type.getSimpleName().toLowerCase(Locale.ROOT));
        if (direction == PacketFlow.CLIENTBOUND) {
            // 客户端处理器仅能在客户端注册
            if (FabricLoader.getInstance().getEnvironmentType() == EnvType.CLIENT) {
                ClientPlayConnectionEvents.INIT.register((h, c) ->
                        ClientPlayNetworking.registerGlobalReceiver(pId, (client, handler, buf, responseSender) -> {
                            T packet = decoder.apply(buf);
                            client.execute(() -> packet.handle(new NetworkContext(handler.getConnection(), null)));
                        }));
            }
        } else {
            ServerPlayConnectionEvents.INIT.register((h, s) ->
                    ServerPlayNetworking.registerGlobalReceiver(pId, (server, player, handler, buf, responseSender) -> {
                        T packet = decoder.apply(buf);
                        server.execute(() -> {
                            Connection connection = ((IServerCommonPacketListenerImplAccessor) handler).getConnection();
                            packet.handle(new NetworkContext(connection, player));
                        });
                    }));
        }

        // 创建 PacketType 并记录
        Reference<PacketType<PacketWrapper<?>>> ref = new Reference<>();
        PacketType<PacketWrapper<?>> packetType = PacketType.create(pId, buf -> new PacketWrapper<>(decoder.apply(buf), ref));
        ref.set(packetType);
        map.put(type, ref);
    }

    @Override
    public <T extends Packet<NetworkContext>> void send(T packet, ServerPlayer player) {
        Reference<PacketType<PacketWrapper<?>>> type = findType(packet.getClass(), PacketFlow.CLIENTBOUND);
        ServerPlayNetworking.send(player, new PacketWrapper<>(packet, type));
    }

    @Override
    public <T extends Packet<NetworkContext>> void send(T packet, PacketTarget target) {
        Reference<PacketType<PacketWrapper<?>>> type = findType(packet.getClass(), PacketFlow.CLIENTBOUND);
        var buf = new FriendlyByteBuf(Unpooled.buffer());
        new PacketWrapper<>(packet, type).write(buf);
        target.send(ServerPlayNetworking.createS2CPacket(id, buf));
    }

    @Override
    public <T extends Packet<NetworkContext>> void sendToServer(T packet) {
        Reference<PacketType<PacketWrapper<?>>> type = findType(packet.getClass(), PacketFlow.SERVERBOUND);
        ClientPlayNetworking.send(new PacketWrapper<>(packet, type));
    }

    /**
     * 根据数据包类型查找 {@link PacketType}
     *
     * @param type      类型
     * @param direction 传输方向
     * @throws NullPointerException 找不到数据包类型时抛出
     */
    private Reference<PacketType<PacketWrapper<?>>> findType(Class<?> type, PacketFlow direction) {
        var map = selectMap(direction);
        if (map.containsKey(type)) {
            return map.get(type);
        }

        String from = direction == PacketFlow.CLIENTBOUND ? "server" : "client";
        String to = direction == PacketFlow.SERVERBOUND ? "server" : "client";
        throw new NullPointerException("Not register type " + type.getName()
                + " in channel " + id + "(" + from + "->" + to + ")");
    }

    /**
     * 根据方向选择 Map
     *
     * @param direction 发送方向
     * @return 注册 Map
     */
    private Map<Class<?>, Reference<PacketType<PacketWrapper<?>>>> selectMap(PacketFlow direction) {
        return direction == PacketFlow.CLIENTBOUND
                ? serverToClientTypeMapByClass
                : clientToServerTypeMapByClass;
    }

    /**
     * 将 {@link Packet<NetworkContext>} 包装为 {@link FabricPacket}
     *
     * @param packet {@link  Packet<NetworkContext>}
     * @param type   用于获取 {@link PacketType}
     * @param <T>    包类型
     */
    public record PacketWrapper<T extends Packet<NetworkContext>>(T packet,
                                                                  Reference<PacketType<PacketWrapper<?>>> type)
            implements FabricPacket {

        @Override
        public void write(FriendlyByteBuf buf) {
            packet.write(buf);
        }

        @Override
        public PacketType<?> getType() {
            return type.get();
        }
    }
}
